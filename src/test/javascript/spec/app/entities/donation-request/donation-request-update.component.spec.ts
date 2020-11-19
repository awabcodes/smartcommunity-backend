import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { DonationRequestUpdateComponent } from 'app/entities/donation-request/donation-request-update.component';
import { DonationRequestService } from 'app/entities/donation-request/donation-request.service';
import { DonationRequest } from 'app/shared/model/donation-request.model';

describe('Component Tests', () => {
  describe('DonationRequest Management Update Component', () => {
    let comp: DonationRequestUpdateComponent;
    let fixture: ComponentFixture<DonationRequestUpdateComponent>;
    let service: DonationRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [DonationRequestUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DonationRequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DonationRequestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonationRequestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DonationRequest(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DonationRequest();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
