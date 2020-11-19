import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPollChoice, PollChoice } from 'app/shared/model/poll-choice.model';
import { PollChoiceService } from './poll-choice.service';
import { IPoll } from 'app/shared/model/poll.model';
import { PollService } from 'app/entities/poll/poll.service';

@Component({
  selector: 'jhi-poll-choice-update',
  templateUrl: './poll-choice-update.component.html',
})
export class PollChoiceUpdateComponent implements OnInit {
  isSaving = false;
  polls: IPoll[] = [];

  editForm = this.fb.group({
    id: [],
    choice: [null, [Validators.required]],
    pollId: [null, Validators.required],
  });

  constructor(
    protected pollChoiceService: PollChoiceService,
    protected pollService: PollService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pollChoice }) => {
      this.updateForm(pollChoice);

      this.pollService.query().subscribe((res: HttpResponse<IPoll[]>) => (this.polls = res.body || []));
    });
  }

  updateForm(pollChoice: IPollChoice): void {
    this.editForm.patchValue({
      id: pollChoice.id,
      choice: pollChoice.choice,
      pollId: pollChoice.pollId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pollChoice = this.createFromForm();
    if (pollChoice.id !== undefined) {
      this.subscribeToSaveResponse(this.pollChoiceService.update(pollChoice));
    } else {
      this.subscribeToSaveResponse(this.pollChoiceService.create(pollChoice));
    }
  }

  private createFromForm(): IPollChoice {
    return {
      ...new PollChoice(),
      id: this.editForm.get(['id'])!.value,
      choice: this.editForm.get(['choice'])!.value,
      pollId: this.editForm.get(['pollId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPollChoice>>): void {
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

  trackById(index: number, item: IPoll): any {
    return item.id;
  }
}
