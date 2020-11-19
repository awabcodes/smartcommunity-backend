import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPollChoice } from 'app/shared/model/poll-choice.model';

type EntityResponseType = HttpResponse<IPollChoice>;
type EntityArrayResponseType = HttpResponse<IPollChoice[]>;

@Injectable({ providedIn: 'root' })
export class PollChoiceService {
  public resourceUrl = SERVER_API_URL + 'api/poll-choices';

  constructor(protected http: HttpClient) {}

  create(pollChoice: IPollChoice): Observable<EntityResponseType> {
    return this.http.post<IPollChoice>(this.resourceUrl, pollChoice, { observe: 'response' });
  }

  update(pollChoice: IPollChoice): Observable<EntityResponseType> {
    return this.http.put<IPollChoice>(this.resourceUrl, pollChoice, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPollChoice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPollChoice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
