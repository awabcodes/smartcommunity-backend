import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { NeedUpdateComponent } from 'app/entities/need/need-update.component';
import { NeedService } from 'app/entities/need/need.service';
import { Need } from 'app/shared/model/need.model';

describe('Component Tests', () => {
  describe('Need Management Update Component', () => {
    let comp: NeedUpdateComponent;
    let fixture: ComponentFixture<NeedUpdateComponent>;
    let service: NeedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [NeedUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NeedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NeedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NeedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Need(123);
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
        const entity = new Need();
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
