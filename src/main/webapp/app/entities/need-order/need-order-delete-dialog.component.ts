import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INeedOrder } from 'app/shared/model/need-order.model';
import { NeedOrderService } from './need-order.service';

@Component({
  templateUrl: './need-order-delete-dialog.component.html',
})
export class NeedOrderDeleteDialogComponent {
  needOrder?: INeedOrder;

  constructor(protected needOrderService: NeedOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.needOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('needOrderListModification');
      this.activeModal.close();
    });
  }
}
