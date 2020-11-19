import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDonation } from 'app/shared/model/donation.model';

type EntityResponseType = HttpResponse<IDonation>;
type EntityArrayResponseType = HttpResponse<IDonation[]>;

@Injectable({ providedIn: 'root' })
export class DonationService {
  public resourceUrl = SERVER_API_URL + 'api/donations';

  constructor(protected http: HttpClient) {}

  create(donation: IDonation): Observable<EntityResponseType> {
    return this.http.post<IDonation>(this.resourceUrl, donation, { observe: 'response' });
  }

  update(donation: IDonation): Observable<EntityResponseType> {
    return this.http.put<IDonation>(this.resourceUrl, donation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDonation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDonation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
