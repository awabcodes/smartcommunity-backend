import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INeedOrder, NeedOrder } from 'app/shared/model/need-order.model';
import { NeedOrderService } from './need-order.service';
import { NeedOrderComponent } from './need-order.component';
import { NeedOrderDetailComponent } from './need-order-detail.component';
import { NeedOrderUpdateComponent } from './need-order-update.component';

@Injectable({ providedIn: 'root' })
export class NeedOrderResolve implements Resolve<INeedOrder> {
  constructor(private service: NeedOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INeedOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((needOrder: HttpResponse<NeedOrder>) => {
          if (needOrder.body) {
            return of(needOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NeedOrder());
  }
}

export const needOrderRoute: Routes = [
  {
    path: '',
    component: NeedOrderComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'smartcommunityApp.needOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NeedOrderDetailComponent,
    resolve: {
      needOrder: NeedOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.needOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NeedOrderUpdateComponent,
    resolve: {
      needOrder: NeedOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.needOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NeedOrderUpdateComponent,
    resolve: {
      needOrder: NeedOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.needOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
