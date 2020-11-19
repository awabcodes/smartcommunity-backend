import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPoll } from 'app/shared/model/poll.model';

type EntityResponseType = HttpResponse<IPoll>;
type EntityArrayResponseType = HttpResponse<IPoll[]>;

@Injectable({ providedIn: 'root' })
export class PollService {
  public resourceUrl = SERVER_API_URL + 'api/polls';

  constructor(protected http: HttpClient) {}

  create(poll: IPoll): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(poll);
    return this.http
      .post<IPoll>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(poll: IPoll): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(poll);
    return this.http
      .put<IPoll>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPoll>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPoll[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(poll: IPoll): IPoll {
    const copy: IPoll = Object.assign({}, poll, {
      creationDate: poll.creationDate && poll.creationDate.isValid() ? poll.creationDate.toJSON() : undefined,
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
      res.body.forEach((poll: IPoll) => {
        poll.creationDate = poll.creationDate ? moment(poll.creationDate) : undefined;
      });
    }
    return res;
  }
}
