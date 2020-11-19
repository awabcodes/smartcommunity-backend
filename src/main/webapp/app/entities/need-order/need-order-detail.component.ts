import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INeedOrder } from 'app/shared/model/need-order.model';

@Component({
  selector: 'jhi-need-order-detail',
  templateUrl: './need-order-detail.component.html',
})
export class NeedOrderDetailComponent implements OnInit {
  needOrder: INeedOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ needOrder }) => (this.needOrder = needOrder));
  }

  previousState(): void {
    window.history.back();
  }
}
