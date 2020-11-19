import { Moment } from 'moment';
import { FeedbackType } from 'app/shared/model/enumerations/feedback-type.model';
import { FeedbackStatus } from 'app/shared/model/enumerations/feedback-status.model';

export interface IFeedback {
  id?: number;
  title?: string;
  content?: string;
  type?: FeedbackType;
  status?: FeedbackStatus;
  creationDate?: Moment;
  imageContentType?: string;
  image?: any;
  userLogin?: string;
  userId?: number;
}

export class Feedback implements IFeedback {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public type?: FeedbackType,
    public status?: FeedbackStatus,
    public creationDate?: Moment,
    public imageContentType?: string,
    public image?: any,
    public userLogin?: string,
    public userId?: number
  ) {}
}
