import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArtoshTestModule } from '../../../test.module';
import { TranslateDetailComponent } from 'app/entities/translate/translate-detail.component';
import { Translate } from 'app/shared/model/translate.model';

describe('Component Tests', () => {
  describe('Translate Management Detail Component', () => {
    let comp: TranslateDetailComponent;
    let fixture: ComponentFixture<TranslateDetailComponent>;
    const route = ({ data: of({ translate: new Translate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArtoshTestModule],
        declarations: [TranslateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TranslateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TranslateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load translate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.translate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
