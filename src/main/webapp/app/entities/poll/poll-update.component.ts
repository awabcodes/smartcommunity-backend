import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPoll, Poll } from 'app/shared/model/poll.model';
import { PollService } from './poll.service';
import { IPollChoice, PollChoice } from 'app/shared/model/poll-choice.model';
import { PollChoiceService } from '../poll-choice/poll-choice.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PollChoiceDeleteDialogComponent } from '../poll-choice/poll-choice-delete-dialog.component';

@Component({
  selector: 'jhi-poll-update',
  templateUrl: './poll-update.component.html',
})
export class PollUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  pollChoices: IPollChoice[] = [];

  editForm = this.fb.group({
    id: [],
    question: [null, [Validators.required]],
    active: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
    users: [],
  });

  choiceForm = this.fb.group({
    id: [],
    choice: [null, [Validators.required]],
    pollId: [null],
  });

  constructor(
    protected pollService: PollService,
    protected pollChoiceService: PollChoiceService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poll }) => {
      if (!poll.id) {
        const today = moment().startOf('day');
        poll.creationDate = today;
      }

      this.updateForm(poll);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(poll: IPoll): void {
    this.editForm.patchValue({
      id: poll.id,
      question: poll.question,
      active: poll.active,
      createdBy: poll.createdBy,
      creationDate: poll.creationDate ? poll.creationDate.format(DATE_TIME_FORMAT) : null,
      users: poll.users,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poll = this.createFromForm();
    if (poll.id !== undefined) {
      this.subscribeToSaveResponse(this.pollService.update(poll));
    } else {
      this.subscribeToSaveResponse(this.pollService.create(poll));
    }
  }

  private createFromForm(): IPoll {
    return {
      ...new Poll(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      active: this.editForm.get(['active'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      users: this.editForm.get(['users'])!.value,
    };
  }

  deleteChoice(pollChoice: IPollChoice): void {
    const modalRef = this.modalService.open(PollChoiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pollChoice = pollChoice;
    this.pollChoices = this.pollChoices.filter(val => val.id !== pollChoice.id);
  }

  saveChoice(): void {
    if (this.editForm.get(['id'])!.value) {
      this.isSaving = true;
      const pollChoice = this.createChoiceFromForm();
      this.subscribeToSaveChoiceResponse(this.pollChoiceService.create(pollChoice));
    } else {
      alert('Please create the Poll first');
    }
  }

  private createChoiceFromForm(): IPollChoice {
    return {
      ...new PollChoice(),
      choice: this.choiceForm.get(['choice'])!.value,
      pollId: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoll>>): void {
    result.subscribe(
      poll => {
        this.onSaveSuccess();
        this.updateForm(poll.body!);
      },
      () => this.onSaveError()
    );
  }

  protected subscribeToSaveChoiceResponse(result: Observable<HttpResponse<IPollChoice>>): void {
    result.subscribe(
      choice => {
        this.onSaveSuccess();
        this.pollChoices.push(choice.body!);
      },
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }

  getSelected(selectedVals: IUser[], option: IUser): IUser {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
