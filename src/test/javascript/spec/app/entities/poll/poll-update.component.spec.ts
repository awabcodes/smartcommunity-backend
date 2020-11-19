import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { PollUpdateComponent } from 'app/entities/poll/poll-update.component';
import { PollService } from 'app/entities/poll/poll.service';
import { Poll } from 'app/shared/model/poll.model';

describe('Component Tests', () => {
  describe('Poll Management Update Component', () => {
    let comp: PollUpdateComponent;
    let fixture: ComponentFixture<PollUpdateComponent>;
    let service: PollService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [PollUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PollUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PollUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PollService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Poll(123);
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
        const entity = new Poll();
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
