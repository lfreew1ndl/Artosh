import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ArtoshTestModule } from '../../../test.module';
import { TranslateUpdateComponent } from 'app/entities/translate/translate-update.component';
import { TranslateService } from 'app/entities/translate/translate.service';
import { Translate } from 'app/shared/model/translate.model';

describe('Component Tests', () => {
  describe('Translate Management Update Component', () => {
    let comp: TranslateUpdateComponent;
    let fixture: ComponentFixture<TranslateUpdateComponent>;
    let service: TranslateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArtoshTestModule],
        declarations: [TranslateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TranslateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TranslateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TranslateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Translate(123);
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
        const entity = new Translate();
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
