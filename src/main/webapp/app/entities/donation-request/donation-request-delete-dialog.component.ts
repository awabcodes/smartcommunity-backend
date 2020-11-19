import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDonationRequest } from 'app/shared/model/donation-request.model';
import { DonationRequestService } from './donation-request.service';

@Component({
  templateUrl: './donation-request-delete-dialog.component.html',
})
export class DonationRequestDeleteDialogComponent {
  donationRequest?: IDonationRequest;

  constructor(
    protected donationRequestService: DonationRequestService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donationRequestService.delete(id).subscribe(() => {
      this.eventManager.broadcast('donationRequestListModification');
      this.activeModal.close();
    });
  }
}
