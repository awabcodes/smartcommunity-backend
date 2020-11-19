import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { NeedOrderUpdateComponent } from 'app/entities/need-order/need-order-update.component';
import { NeedOrderService } from 'app/entities/need-order/need-order.service';
import { NeedOrder } from 'app/shared/model/need-order.model';

describe('Component Tests', () => {
  describe('NeedOrder Management Update Component', () => {
    let comp: NeedOrderUpdateComponent;
    let fixture: ComponentFixture<NeedOrderUpdateComponent>;
    let service: NeedOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [NeedOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NeedOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NeedOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NeedOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NeedOrder(123);
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
        const entity = new NeedOrder();
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
