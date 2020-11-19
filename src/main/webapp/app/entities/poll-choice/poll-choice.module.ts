import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartcommunitySharedModule } from 'app/shared/shared.module';
import { PollChoiceComponent } from './poll-choice.component';
import { PollChoiceDetailComponent } from './poll-choice-detail.component';
import { PollChoiceUpdateComponent } from './poll-choice-update.component';
import { PollChoiceDeleteDialogComponent } from './poll-choice-delete-dialog.component';
import { pollChoiceRoute } from './poll-choice.route';

@NgModule({
  imports: [SmartcommunitySharedModule, RouterModule.forChild(pollChoiceRoute)],
  declarations: [PollChoiceComponent, PollChoiceDetailComponent, PollChoiceUpdateComponent, PollChoiceDeleteDialogComponent],
  entryComponents: [PollChoiceDeleteDialogComponent],
})
export class SmartcommunityPollChoiceModule {}
