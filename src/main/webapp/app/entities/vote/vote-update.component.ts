import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVote, Vote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPollChoice } from 'app/shared/model/poll-choice.model';
import { PollChoiceService } from 'app/entities/poll-choice/poll-choice.service';

type SelectableEntity = IUser | IPollChoice;

@Component({
  selector: 'jhi-vote-update',
  templateUrl: './vote-update.component.html',
})
export class VoteUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  pollchoices: IPollChoice[] = [];

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    userId: [null, Validators.required],
    choiceId: [null, Validators.required],
  });

  constructor(
    protected voteService: VoteService,
    protected userService: UserService,
    protected pollChoiceService: PollChoiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vote }) => {
      if (!vote.id) {
        const today = moment().startOf('day');
        vote.creationDate = today;
      }

      this.updateForm(vote);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.pollChoiceService.query().subscribe((res: HttpResponse<IPollChoice[]>) => (this.pollchoices = res.body || []));
    });
  }

  updateForm(vote: IVote): void {
    this.editForm.patchValue({
      id: vote.id,
      creationDate: vote.creationDate ? vote.creationDate.format(DATE_TIME_FORMAT) : null,
      userId: vote.userId,
      choiceId: vote.choiceId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vote = this.createFromForm();
    if (vote.id !== undefined) {
      this.subscribeToSaveResponse(this.voteService.update(vote));
    } else {
      this.subscribeToSaveResponse(this.voteService.create(vote));
    }
  }

  private createFromForm(): IVote {
    return {
      ...new Vote(),
      id: this.editForm.get(['id'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      choiceId: this.editForm.get(['choiceId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVote>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
