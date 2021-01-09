import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPollChoice } from 'app/shared/model/poll-choice.model';

import { IPoll } from 'app/shared/model/poll.model';
import { JhiEventManager } from 'ng-jhipster';
import { PollChoiceDeleteDialogComponent } from '../poll-choice/poll-choice-delete-dialog.component';
import { PollChoiceService } from '../poll-choice/poll-choice.service';

@Component({
  selector: 'jhi-poll-detail',
  templateUrl: './poll-detail.component.html',
})
export class PollDetailComponent implements OnInit {
  poll: IPoll | null = null;
  pollChoices: IPollChoice[] = [];

  constructor(
    protected pollChoiceService: PollChoiceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poll }) => {
      this.poll = poll;
      this.loadChoices();
    });
  }

  loadChoices(): void {
    this.pollChoiceService.queryByPoll(this.poll?.id!).subscribe((res: HttpResponse<IPollChoice[]>) => (this.pollChoices = res.body!));
  }

  previousState(): void {
    window.history.back();
  }

  deleteChoice(pollChoice: IPollChoice): void {
    const modalRef = this.modalService.open(PollChoiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pollChoice = pollChoice;
  }
}
