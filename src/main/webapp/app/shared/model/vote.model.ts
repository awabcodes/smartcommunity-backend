import { Moment } from 'moment';

export interface IVote {
  id?: number;
  creationDate?: Moment;
  userLogin?: string;
  userId?: number;
  choiceChoice?: string;
  choiceId?: number;
}

export class Vote implements IVote {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public userLogin?: string,
    public userId?: number,
    public choiceChoice?: string,
    public choiceId?: number
  ) {}
}
