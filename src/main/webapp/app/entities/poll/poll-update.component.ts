import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPoll, Poll } from 'app/shared/model/poll.model';
import { PollService } from './poll.service';
import { IPollChoice, PollChoice } from 'app/shared/model/poll-choice.model';
import { PollChoiceService } from '../poll-choice/poll-choice.service';

@Component({
  selector: 'jhi-poll-update',
  templateUrl: './poll-update.component.html',
})
export class PollUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    question: [null, [Validators.required]],
    active: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
  });

  choiceForm = this.fb.group({
    id: [],
    choice: [null, [Validators.required]],
    pollId: [null],
  });

  constructor(protected pollService: PollService, protected pollChoiceService: PollChoiceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poll }) => {
      if (!poll.id) {
        const today = moment().startOf('day');
        poll.creationDate = today;
      }

      this.updateForm(poll);
    });
  }

  updateForm(poll: IPoll): void {
    this.editForm.patchValue({
      id: poll.id,
      question: poll.question,
      active: poll.active,
      createdBy: poll.createdBy,
      creationDate: poll.creationDate ? poll.creationDate.format(DATE_TIME_FORMAT) : null,
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
    };
  }

  saveChoice(): void {
    this.isSaving = true;
    const pollChoice = this.createChoiceFromForm();
    this.subscribeToSaveResponse(this.pollChoiceService.create(pollChoice));
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
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
