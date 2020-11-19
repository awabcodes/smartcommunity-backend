import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDonationRequest, DonationRequest } from 'app/shared/model/donation-request.model';
import { DonationRequestService } from './donation-request.service';

@Component({
  selector: 'jhi-donation-request-update',
  templateUrl: './donation-request-update.component.html',
})
export class DonationRequestUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cause: [null, [Validators.required]],
    paymentInfo: [null, [Validators.required]],
    info: [null, [Validators.required]],
    totalAmount: [null, [Validators.required]],
    contact: [null, [Validators.required]],
    amountRaised: [],
  });

  constructor(
    protected donationRequestService: DonationRequestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationRequest }) => {
      this.updateForm(donationRequest);
    });
  }

  updateForm(donationRequest: IDonationRequest): void {
    this.editForm.patchValue({
      id: donationRequest.id,
      cause: donationRequest.cause,
      paymentInfo: donationRequest.paymentInfo,
      info: donationRequest.info,
      totalAmount: donationRequest.totalAmount,
      contact: donationRequest.contact,
      amountRaised: donationRequest.amountRaised,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donationRequest = this.createFromForm();
    if (donationRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.donationRequestService.update(donationRequest));
    } else {
      this.subscribeToSaveResponse(this.donationRequestService.create(donationRequest));
    }
  }

  private createFromForm(): IDonationRequest {
    return {
      ...new DonationRequest(),
      id: this.editForm.get(['id'])!.value,
      cause: this.editForm.get(['cause'])!.value,
      paymentInfo: this.editForm.get(['paymentInfo'])!.value,
      info: this.editForm.get(['info'])!.value,
      totalAmount: this.editForm.get(['totalAmount'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      amountRaised: this.editForm.get(['amountRaised'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationRequest>>): void {
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
}
