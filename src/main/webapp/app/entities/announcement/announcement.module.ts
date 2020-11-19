import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartcommunitySharedModule } from 'app/shared/shared.module';
import { AnnouncementComponent } from './announcement.component';
import { AnnouncementDetailComponent } from './announcement-detail.component';
import { AnnouncementUpdateComponent } from './announcement-update.component';
import { AnnouncementDeleteDialogComponent } from './announcement-delete-dialog.component';
import { announcementRoute } from './announcement.route';

@NgModule({
  imports: [SmartcommunitySharedModule, RouterModule.forChild(announcementRoute)],
  declarations: [AnnouncementComponent, AnnouncementDetailComponent, AnnouncementUpdateComponent, AnnouncementDeleteDialogComponent],
  entryComponents: [AnnouncementDeleteDialogComponent],
})
export class SmartcommunityAnnouncementModule {}
