import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITranslate, Translate } from 'app/shared/model/translate.model';
import { TranslateService } from './translate.service';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language/language.service';
import { IWord } from 'app/shared/model/word.model';
import { WordService } from 'app/entities/word/word.service';

type SelectableEntity = ILanguage | IWord;

@Component({
  selector: 'jhi-translate-update',
  templateUrl: './translate-update.component.html',
})
export class TranslateUpdateComponent implements OnInit {
  isSaving = false;
  languages: ILanguage[] = [];
  words: IWord[] = [];

  editForm = this.fb.group({
    id: [],
    translate: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
    language: [null, Validators.required],
    word: [null, Validators.required],
  });

  constructor(
    protected translateService: TranslateService,
    protected languageService: LanguageService,
    protected wordService: WordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ translate }) => {
      this.updateForm(translate);

      this.languageService.query().subscribe((res: HttpResponse<ILanguage[]>) => (this.languages = res.body || []));

      this.wordService.query().subscribe((res: HttpResponse<IWord[]>) => (this.words = res.body || []));
    });
  }

  updateForm(translate: ITranslate): void {
    this.editForm.patchValue({
      id: translate.id,
      translate: translate.translate,
      language: translate.language,
      word: translate.word,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const translate = this.createFromForm();
    if (translate.id !== undefined) {
      this.subscribeToSaveResponse(this.translateService.update(translate));
    } else {
      this.subscribeToSaveResponse(this.translateService.create(translate));
    }
  }

  private createFromForm(): ITranslate {
    return {
      ...new Translate(),
      id: this.editForm.get(['id'])!.value,
      translate: this.editForm.get(['translate'])!.value,
      language: this.editForm.get(['language'])!.value,
      word: this.editForm.get(['word'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITranslate>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
