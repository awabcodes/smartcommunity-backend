import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoll } from 'app/shared/model/poll.model';

@Component({
  selector: 'jhi-poll-detail',
  templateUrl: './poll-detail.component.html',
})
export class PollDetailComponent implements OnInit {
  poll: IPoll | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poll }) => (this.poll = poll));
  }

  previousState(): void {
    window.history.back();
  }
}
