import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { PollChoiceUpdateComponent } from 'app/entities/poll-choice/poll-choice-update.component';
import { PollChoiceService } from 'app/entities/poll-choice/poll-choice.service';
import { PollChoice } from 'app/shared/model/poll-choice.model';

describe('Component Tests', () => {
  describe('PollChoice Management Update Component', () => {
    let comp: PollChoiceUpdateComponent;
    let fixture: ComponentFixture<PollChoiceUpdateComponent>;
    let service: PollChoiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [PollChoiceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PollChoiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PollChoiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PollChoiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PollChoice(123);
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
        const entity = new PollChoice();
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
