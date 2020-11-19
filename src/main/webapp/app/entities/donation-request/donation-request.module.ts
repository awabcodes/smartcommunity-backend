import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartcommunitySharedModule } from 'app/shared/shared.module';
import { DonationRequestComponent } from './donation-request.component';
import { DonationRequestDetailComponent } from './donation-request-detail.component';
import { DonationRequestUpdateComponent } from './donation-request-update.component';
import { DonationRequestDeleteDialogComponent } from './donation-request-delete-dialog.component';
import { donationRequestRoute } from './donation-request.route';

@NgModule({
  imports: [SmartcommunitySharedModule, RouterModule.forChild(donationRequestRoute)],
  declarations: [
    DonationRequestComponent,
    DonationRequestDetailComponent,
    DonationRequestUpdateComponent,
    DonationRequestDeleteDialogComponent,
  ],
  entryComponents: [DonationRequestDeleteDialogComponent],
})
export class SmartcommunityDonationRequestModule {}
