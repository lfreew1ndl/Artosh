import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArtoshTestModule } from '../../../test.module';
import { TranslateComponent } from 'app/entities/translate/translate.component';
import { TranslateService } from 'app/entities/translate/translate.service';
import { Translate } from 'app/shared/model/translate.model';

describe('Component Tests', () => {
  describe('Translate Management Component', () => {
    let comp: TranslateComponent;
    let fixture: ComponentFixture<TranslateComponent>;
    let service: TranslateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArtoshTestModule],
        declarations: [TranslateComponent],
      })
        .overrideTemplate(TranslateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TranslateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TranslateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Translate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.translates && comp.translates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
