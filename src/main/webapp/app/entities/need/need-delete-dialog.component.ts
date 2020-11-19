import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INeed } from 'app/shared/model/need.model';
import { NeedService } from './need.service';

@Component({
  templateUrl: './need-delete-dialog.component.html',
})
export class NeedDeleteDialogComponent {
  need?: INeed;

  constructor(protected needService: NeedService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.needService.delete(id).subscribe(() => {
      this.eventManager.broadcast('needListModification');
      this.activeModal.close();
    });
  }
}
