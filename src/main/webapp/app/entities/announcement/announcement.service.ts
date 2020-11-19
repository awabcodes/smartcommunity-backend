import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnnouncement } from 'app/shared/model/announcement.model';

type EntityResponseType = HttpResponse<IAnnouncement>;
type EntityArrayResponseType = HttpResponse<IAnnouncement[]>;

@Injectable({ providedIn: 'root' })
export class AnnouncementService {
  public resourceUrl = SERVER_API_URL + 'api/announcements';

  constructor(protected http: HttpClient) {}

  create(announcement: IAnnouncement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(announcement);
    return this.http
      .post<IAnnouncement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(announcement: IAnnouncement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(announcement);
    return this.http
      .put<IAnnouncement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnnouncement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnnouncement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(announcement: IAnnouncement): IAnnouncement {
    const copy: IAnnouncement = Object.assign({}, announcement, {
      creationDate: announcement.creationDate && announcement.creationDate.isValid() ? announcement.creationDate.toJSON() : undefined,
      announcementDate:
        announcement.announcementDate && announcement.announcementDate.isValid() ? announcement.announcementDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
      res.body.announcementDate = res.body.announcementDate ? moment(res.body.announcementDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((announcement: IAnnouncement) => {
        announcement.creationDate = announcement.creationDate ? moment(announcement.creationDate) : undefined;
        announcement.announcementDate = announcement.announcementDate ? moment(announcement.announcementDate) : undefined;
      });
    }
    return res;
  }
}
