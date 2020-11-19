import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPoll, Poll } from 'app/shared/model/poll.model';
import { PollService } from './poll.service';
import { PollComponent } from './poll.component';
import { PollDetailComponent } from './poll-detail.component';
import { PollUpdateComponent } from './poll-update.component';

@Injectable({ providedIn: 'root' })
export class PollResolve implements Resolve<IPoll> {
  constructor(private service: PollService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPoll> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((poll: HttpResponse<Poll>) => {
          if (poll.body) {
            return of(poll.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Poll());
  }
}

export const pollRoute: Routes = [
  {
    path: '',
    component: PollComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'smartcommunityApp.poll.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PollDetailComponent,
    resolve: {
      poll: PollResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.poll.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PollUpdateComponent,
    resolve: {
      poll: PollResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.poll.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PollUpdateComponent,
    resolve: {
      poll: PollResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.poll.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
