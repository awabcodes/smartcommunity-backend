import { Moment } from 'moment';
import { AnnouncementType } from 'app/shared/model/enumerations/announcement-type.model';

export interface IAnnouncement {
  id?: number;
  title?: string;
  content?: string;
  creationDate?: Moment;
  type?: AnnouncementType;
  location?: string;
  imageContentType?: string;
  image?: any;
  contact?: string;
  announcementDate?: Moment;
}

export class Announcement implements IAnnouncement {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public creationDate?: Moment,
    public type?: AnnouncementType,
    public location?: string,
    public imageContentType?: string,
    public image?: any,
    public contact?: string,
    public announcementDate?: Moment
  ) {}
}
