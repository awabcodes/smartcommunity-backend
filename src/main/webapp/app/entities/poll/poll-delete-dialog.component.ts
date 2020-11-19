import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoll } from 'app/shared/model/poll.model';
import { PollService } from './poll.service';

@Component({
  templateUrl: './poll-delete-dialog.component.html',
})
export class PollDeleteDialogComponent {
  poll?: IPoll;

  constructor(protected pollService: PollService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pollService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pollListModification');
      this.activeModal.close();
    });
  }
}
