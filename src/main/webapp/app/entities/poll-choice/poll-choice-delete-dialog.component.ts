import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPollChoice } from 'app/shared/model/poll-choice.model';
import { PollChoiceService } from './poll-choice.service';

@Component({
  templateUrl: './poll-choice-delete-dialog.component.html',
})
export class PollChoiceDeleteDialogComponent {
  pollChoice?: IPollChoice;

  constructor(
    protected pollChoiceService: PollChoiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pollChoiceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pollChoiceListModification');
      this.activeModal.close();
    });
  }
}
