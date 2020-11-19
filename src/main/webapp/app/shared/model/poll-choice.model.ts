import { IVote } from 'app/shared/model/vote.model';

export interface IPollChoice {
  id?: number;
  choice?: string;
  votes?: IVote[];
  pollQuestion?: string;
  pollId?: number;
}

export class PollChoice implements IPollChoice {
  constructor(public id?: number, public choice?: string, public votes?: IVote[], public pollQuestion?: string, public pollId?: number) {}
}
