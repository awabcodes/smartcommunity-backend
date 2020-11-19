import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INeedOrder } from 'app/shared/model/need-order.model';

type EntityResponseType = HttpResponse<INeedOrder>;
type EntityArrayResponseType = HttpResponse<INeedOrder[]>;

@Injectable({ providedIn: 'root' })
export class NeedOrderService {
  public resourceUrl = SERVER_API_URL + 'api/need-orders';

  constructor(protected http: HttpClient) {}

  create(needOrder: INeedOrder): Observable<EntityResponseType> {
    return this.http.post<INeedOrder>(this.resourceUrl, needOrder, { observe: 'response' });
  }

  update(needOrder: INeedOrder): Observable<EntityResponseType> {
    return this.http.put<INeedOrder>(this.resourceUrl, needOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INeedOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INeedOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
