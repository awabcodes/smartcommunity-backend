import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartcommunitySharedModule } from 'app/shared/shared.module';
import { PollComponent } from './poll.component';
import { PollDetailComponent } from './poll-detail.component';
import { PollUpdateComponent } from './poll-update.component';
import { PollDeleteDialogComponent } from './poll-delete-dialog.component';
import { pollRoute } from './poll.route';

@NgModule({
  imports: [SmartcommunitySharedModule, RouterModule.forChild(pollRoute)],
  declarations: [PollComponent, PollDetailComponent, PollUpdateComponent, PollDeleteDialogComponent],
  entryComponents: [PollDeleteDialogComponent],
})
export class SmartcommunityPollModule {}
