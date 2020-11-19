import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFeedback } from 'app/shared/model/feedback.model';

type EntityResponseType = HttpResponse<IFeedback>;
type EntityArrayResponseType = HttpResponse<IFeedback[]>;

@Injectable({ providedIn: 'root' })
export class FeedbackService {
  public resourceUrl = SERVER_API_URL + 'api/feedbacks';

  constructor(protected http: HttpClient) {}

  create(feedback: IFeedback): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feedback);
    return this.http
      .post<IFeedback>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(feedback: IFeedback): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feedback);
    return this.http
      .put<IFeedback>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFeedback>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFeedback[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(feedback: IFeedback): IFeedback {
    const copy: IFeedback = Object.assign({}, feedback, {
      creationDate: feedback.creationDate && feedback.creationDate.isValid() ? feedback.creationDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((feedback: IFeedback) => {
        feedback.creationDate = feedback.creationDate ? moment(feedback.creationDate) : undefined;
      });
    }
    return res;
  }
}
