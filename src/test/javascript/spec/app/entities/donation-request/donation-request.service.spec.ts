import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DonationRequestService } from 'app/entities/donation-request/donation-request.service';
import { IDonationRequest, DonationRequest } from 'app/shared/model/donation-request.model';

describe('Service Tests', () => {
  describe('DonationRequest Service', () => {
    let injector: TestBed;
    let service: DonationRequestService;
    let httpMock: HttpTestingController;
    let elemDefault: IDonationRequest;
    let expectedResult: IDonationRequest | IDonationRequest[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DonationRequestService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DonationRequest(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DonationRequest', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DonationRequest()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DonationRequest', () => {
        const returnedFromService = Object.assign(
          {
            cause: 'BBBBBB',
            paymentInfo: 'BBBBBB',
            info: 'BBBBBB',
            totalAmount: 1,
            contact: 'BBBBBB',
            amountRaised: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DonationRequest', () => {
        const returnedFromService = Object.assign(
          {
            cause: 'BBBBBB',
            paymentInfo: 'BBBBBB',
            info: 'BBBBBB',
            totalAmount: 1,
            contact: 'BBBBBB',
            amountRaised: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DonationRequest', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
