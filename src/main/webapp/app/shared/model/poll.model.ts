import { Moment } from 'moment';
import { IPollChoice } from 'app/shared/model/poll-choice.model';

export interface IPoll {
  id?: number;
  question?: string;
  active?: boolean;
  createdBy?: string;
  creationDate?: Moment;
  choices?: IPollChoice[];
}

export class Poll implements IPoll {
  constructor(
    public id?: number,
    public question?: string,
    public active?: boolean,
    public createdBy?: string,
    public creationDate?: Moment,
    public choices?: IPollChoice[]
  ) {
    this.active = this.active || false;
  }
}
