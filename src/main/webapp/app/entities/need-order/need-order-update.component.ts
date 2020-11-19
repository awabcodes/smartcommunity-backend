import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INeedOrder, NeedOrder } from 'app/shared/model/need-order.model';
import { NeedOrderService } from './need-order.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { INeed } from 'app/shared/model/need.model';
import { NeedService } from 'app/entities/need/need.service';

type SelectableEntity = IUser | INeed;

@Component({
  selector: 'jhi-need-order-update',
  templateUrl: './need-order-update.component.html',
})
export class NeedOrderUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  needs: INeed[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    note: [],
    userId: [null, Validators.required],
    needId: [null, Validators.required],
  });

  constructor(
    protected needOrderService: NeedOrderService,
    protected userService: UserService,
    protected needService: NeedService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ needOrder }) => {
      this.updateForm(needOrder);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.needService.query().subscribe((res: HttpResponse<INeed[]>) => (this.needs = res.body || []));
    });
  }

  updateForm(needOrder: INeedOrder): void {
    this.editForm.patchValue({
      id: needOrder.id,
      quantity: needOrder.quantity,
      note: needOrder.note,
      userId: needOrder.userId,
      needId: needOrder.needId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const needOrder = this.createFromForm();
    if (needOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.needOrderService.update(needOrder));
    } else {
      this.subscribeToSaveResponse(this.needOrderService.create(needOrder));
    }
  }

  private createFromForm(): INeedOrder {
    return {
      ...new NeedOrder(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      note: this.editForm.get(['note'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      needId: this.editForm.get(['needId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INeedOrder>>): void {
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
