import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonationRequest } from 'app/shared/model/donation-request.model';

@Component({
  selector: 'jhi-donation-request-detail',
  templateUrl: './donation-request-detail.component.html',
})
export class DonationRequestDetailComponent implements OnInit {
  donationRequest: IDonationRequest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationRequest }) => (this.donationRequest = donationRequest));
  }

  previousState(): void {
    window.history.back();
  }
}
