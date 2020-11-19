import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'announcement',
        loadChildren: () => import('./announcement/announcement.module').then(m => m.SmartcommunityAnnouncementModule),
      },
      {
        path: 'need',
        loadChildren: () => import('./need/need.module').then(m => m.SmartcommunityNeedModule),
      },
      {
        path: 'need-order',
        loadChildren: () => import('./need-order/need-order.module').then(m => m.SmartcommunityNeedOrderModule),
      },
      {
        path: 'feedback',
        loadChildren: () => import('./feedback/feedback.module').then(m => m.SmartcommunityFeedbackModule),
      },
      {
        path: 'donation-request',
        loadChildren: () => import('./donation-request/donation-request.module').then(m => m.SmartcommunityDonationRequestModule),
      },
      {
        path: 'donation',
        loadChildren: () => import('./donation/donation.module').then(m => m.SmartcommunityDonationModule),
      },
      {
        path: 'poll',
        loadChildren: () => import('./poll/poll.module').then(m => m.SmartcommunityPollModule),
      },
      {
        path: 'poll-choice',
        loadChildren: () => import('./poll-choice/poll-choice.module').then(m => m.SmartcommunityPollChoiceModule),
      },
      {
        path: 'vote',
        loadChildren: () => import('./vote/vote.module').then(m => m.SmartcommunityVoteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SmartcommunityEntityModule {}
