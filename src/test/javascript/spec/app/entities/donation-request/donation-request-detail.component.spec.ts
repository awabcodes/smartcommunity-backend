import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { DonationRequestDetailComponent } from 'app/entities/donation-request/donation-request-detail.component';
import { DonationRequest } from 'app/shared/model/donation-request.model';

describe('Component Tests', () => {
  describe('DonationRequest Management Detail Component', () => {
    let comp: DonationRequestDetailComponent;
    let fixture: ComponentFixture<DonationRequestDetailComponent>;
    const route = ({ data: of({ donationRequest: new DonationRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [DonationRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DonationRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DonationRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load donationRequest on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.donationRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
