import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPollChoice } from 'app/shared/model/poll-choice.model';

@Component({
  selector: 'jhi-poll-choice-detail',
  templateUrl: './poll-choice-detail.component.html',
})
export class PollChoiceDetailComponent implements OnInit {
  pollChoice: IPollChoice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pollChoice }) => (this.pollChoice = pollChoice));
  }

  previousState(): void {
    window.history.back();
  }
}
