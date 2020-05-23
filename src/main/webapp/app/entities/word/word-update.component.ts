import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWord, Word } from 'app/shared/model/word.model';
import { WordService } from './word.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

@Component({
  selector: 'jhi-word-update',
  templateUrl: './word-update.component.html',
})
export class WordUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    word: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(255)]],
    imageUrl: [],
    category: [null, Validators.required],
  });

  constructor(
    protected wordService: WordService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ word }) => {
      this.updateForm(word);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(word: IWord): void {
    this.editForm.patchValue({
      id: word.id,
      word: word.word,
      description: word.description,
      imageUrl: word.imageUrl,
      category: word.category,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const word = this.createFromForm();
    if (word.id !== undefined) {
      this.subscribeToSaveResponse(this.wordService.update(word));
    } else {
      this.subscribeToSaveResponse(this.wordService.create(word));
    }
  }

  private createFromForm(): IWord {
    return {
      ...new Word(),
      id: this.editForm.get(['id'])!.value,
      word: this.editForm.get(['word'])!.value,
      description: this.editForm.get(['description'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWord>>): void {
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

  trackById(index: number, item: ICategory): any {
    return item.id;
  }
}
