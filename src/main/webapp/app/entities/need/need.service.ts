import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INeed } from 'app/shared/model/need.model';

type EntityResponseType = HttpResponse<INeed>;
type EntityArrayResponseType = HttpResponse<INeed[]>;

@Injectable({ providedIn: 'root' })
export class NeedService {
  public resourceUrl = SERVER_API_URL + 'api/needs';

  constructor(protected http: HttpClient) {}

  create(need: INeed): Observable<EntityResponseType> {
    return this.http.post<INeed>(this.resourceUrl, need, { observe: 'response' });
  }

  update(need: INeed): Observable<EntityResponseType> {
    return this.http.put<INeed>(this.resourceUrl, need, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INeed>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INeed[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
