import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPollChoice, PollChoice } from 'app/shared/model/poll-choice.model';
import { PollChoiceService } from './poll-choice.service';
import { PollChoiceComponent } from './poll-choice.component';
import { PollChoiceDetailComponent } from './poll-choice-detail.component';
import { PollChoiceUpdateComponent } from './poll-choice-update.component';

@Injectable({ providedIn: 'root' })
export class PollChoiceResolve implements Resolve<IPollChoice> {
  constructor(private service: PollChoiceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPollChoice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pollChoice: HttpResponse<PollChoice>) => {
          if (pollChoice.body) {
            return of(pollChoice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PollChoice());
  }
}

export const pollChoiceRoute: Routes = [
  {
    path: '',
    component: PollChoiceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'smartcommunityApp.pollChoice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PollChoiceDetailComponent,
    resolve: {
      pollChoice: PollChoiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.pollChoice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PollChoiceUpdateComponent,
    resolve: {
      pollChoice: PollChoiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.pollChoice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PollChoiceUpdateComponent,
    resolve: {
      pollChoice: PollChoiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'smartcommunityApp.pollChoice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
