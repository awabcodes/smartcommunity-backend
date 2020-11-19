import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartcommunityTestModule } from '../../../test.module';
import { NeedOrderDetailComponent } from 'app/entities/need-order/need-order-detail.component';
import { NeedOrder } from 'app/shared/model/need-order.model';

describe('Component Tests', () => {
  describe('NeedOrder Management Detail Component', () => {
    let comp: NeedOrderDetailComponent;
    let fixture: ComponentFixture<NeedOrderDetailComponent>;
    const route = ({ data: of({ needOrder: new NeedOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SmartcommunityTestModule],
        declarations: [NeedOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NeedOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NeedOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load needOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.needOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
