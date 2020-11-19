import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { PollChoiceDetailComponent } from 'app/entities/poll-choice/poll-choice-detail.component';
import { PollChoice } from 'app/shared/model/poll-choice.model';

describe('Component Tests', () => {
  describe('PollChoice Management Detail Component', () => {
    let comp: PollChoiceDetailComponent;
    let fixture: ComponentFixture<PollChoiceDetailComponent>;
    const route = ({ data: of({ pollChoice: new PollChoice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [PollChoiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PollChoiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PollChoiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pollChoice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pollChoice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
