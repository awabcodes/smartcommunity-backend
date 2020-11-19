import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { PollDetailComponent } from 'app/entities/poll/poll-detail.component';
import { Poll } from 'app/shared/model/poll.model';

describe('Component Tests', () => {
  describe('Poll Management Detail Component', () => {
    let comp: PollDetailComponent;
    let fixture: ComponentFixture<PollDetailComponent>;
    const route = ({ data: of({ poll: new Poll(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [PollDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PollDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PollDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load poll on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poll).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
