import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDonationRequest } from 'app/shared/model/donation-request.model';

type EntityResponseType = HttpResponse<IDonationRequest>;
type EntityArrayResponseType = HttpResponse<IDonationRequest[]>;

@Injectable({ providedIn: 'root' })
export class DonationRequestService {
  public resourceUrl = SERVER_API_URL + 'api/donation-requests';

  constructor(protected http: HttpClient) {}

  create(donationRequest: IDonationRequest): Observable<EntityResponseType> {
    return this.http.post<IDonationRequest>(this.resourceUrl, donationRequest, { observe: 'response' });
  }

  update(donationRequest: IDonationRequest): Observable<EntityResponseType> {
    return this.http.put<IDonationRequest>(this.resourceUrl, donationRequest, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDonationRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDonationRequest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
