import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartcommunitySharedModule } from 'app/shared/shared.module';
import { NeedComponent } from './need.component';
import { NeedDetailComponent } from './need-detail.component';
import { NeedUpdateComponent } from './need-update.component';
import { NeedDeleteDialogComponent } from './need-delete-dialog.component';
import { needRoute } from './need.route';

@NgModule({
  imports: [SmartcommunitySharedModule, RouterModule.forChild(needRoute)],
  declarations: [NeedComponent, NeedDetailComponent, NeedUpdateComponent, NeedDeleteDialogComponent],
  entryComponents: [NeedDeleteDialogComponent],
})
export class SmartcommunityNeedModule {}
