import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmartcommunitySharedModule } from 'app/shared/shared.module';
import { NeedOrderComponent } from './need-order.component';
import { NeedOrderDetailComponent } from './need-order-detail.component';
import { NeedOrderUpdateComponent } from './need-order-update.component';
import { NeedOrderDeleteDialogComponent } from './need-order-delete-dialog.component';
import { needOrderRoute } from './need-order.route';

@NgModule({
  imports: [SmartcommunitySharedModule, RouterModule.forChild(needOrderRoute)],
  declarations: [NeedOrderComponent, NeedOrderDetailComponent, NeedOrderUpdateComponent, NeedOrderDeleteDialogComponent],
  entryComponents: [NeedOrderDeleteDialogComponent],
})
export class SmartcommunityNeedOrderModule {}
