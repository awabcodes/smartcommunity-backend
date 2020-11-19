import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartcommunitySharedModule } from 'app/shared/shared.module';
import { FeedbackComponent } from './feedback.component';
import { FeedbackDetailComponent } from './feedback-detail.component';
import { FeedbackUpdateComponent } from './feedback-update.component';
import { FeedbackDeleteDialogComponent } from './feedback-delete-dialog.component';
import { feedbackRoute } from './feedback.route';

@NgModule({
  imports: [SmartcommunitySharedModule, RouterModule.forChild(feedbackRoute)],
  declarations: [FeedbackComponent, FeedbackDetailComponent, FeedbackUpdateComponent, FeedbackDeleteDialogComponent],
  entryComponents: [FeedbackDeleteDialogComponent],
})
export class SmartcommunityFeedbackModule {}
