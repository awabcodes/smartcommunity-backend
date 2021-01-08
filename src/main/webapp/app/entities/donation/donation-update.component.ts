import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDonation, Donation } from 'app/shared/model/donation.model';
import { DonationService } from './donation.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IDonationRequest } from 'app/shared/model/donation-request.model';
import { DonationRequestService } from 'app/entities/donation-request/donation-request.service';

type SelectableEntity = IUser | IDonationRequest;

@Component({
  selector: 'jhi-donation-update',
  templateUrl: './donation-update.component.html',
})
export class DonationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  donationrequests: IDonationRequest[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    receiptNumber: [],
    collected: [],
    userId: [null, Validators.required],
    requestId: [null, Validators.required],
  });

  constructor(
    protected donationService: DonationService,
    protected userService: UserService,
    protected donationRequestService: DonationRequestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donation }) => {
      this.updateForm(donation);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.donationRequestService.query().subscribe((res: HttpResponse<IDonationRequest[]>) => (this.donationrequests = res.body || []));
    });
  }

  updateForm(donation: IDonation): void {
    this.editForm.patchValue({
      id: donation.id,
      amount: donation.amount,
      receiptNumber: donation.receiptNumber,
      collected: donation.collected,
      userId: donation.userId,
      requestId: donation.requestId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donation = this.createFromForm();
    if (donation.id !== undefined) {
      this.subscribeToSaveResponse(this.donationService.update(donation));
    } else {
      this.subscribeToSaveResponse(this.donationService.create(donation));
    }
  }

  private createFromForm(): IDonation {
    return {
      ...new Donation(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      receiptNumber: this.editForm.get(['receiptNumber'])!.value,
      collected: this.editForm.get(['collected'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      requestId: this.editForm.get(['requestId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonation>>): void {
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
