import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserExtra, UserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from './user-extra.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language/language.service';

type SelectableEntity = IUser | ILanguage;

@Component({
  selector: 'jhi-user-extra-update',
  templateUrl: './user-extra-update.component.html',
})
export class UserExtraUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  languages: ILanguage[] = [];

  editForm = this.fb.group({
    id: [],
    gramarScore: [null, [Validators.required, Validators.min(0)]],
    vocabularyScore: [null, [Validators.required, Validators.min(0)]],
    lisningScore: [null, [Validators.required, Validators.min(0)]],
    userId: [null, Validators.required],
    languageId: [null, Validators.required],
  });

  constructor(
    protected userExtraService: UserExtraService,
    protected userService: UserService,
    protected languageService: LanguageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userExtra }) => {
      this.updateForm(userExtra);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.languageService.query().subscribe((res: HttpResponse<ILanguage[]>) => (this.languages = res.body || []));
    });
  }

  updateForm(userExtra: IUserExtra): void {
    this.editForm.patchValue({
      id: userExtra.id,
      gramarScore: userExtra.gramarScore,
      vocabularyScore: userExtra.vocabularyScore,
      lisningScore: userExtra.lisningScore,
      userId: userExtra.userId,
      languageId: userExtra.languageId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userExtra = this.createFromForm();
    if (userExtra.id !== undefined) {
      this.subscribeToSaveResponse(this.userExtraService.update(userExtra));
    } else {
      this.subscribeToSaveResponse(this.userExtraService.create(userExtra));
    }
  }

  private createFromForm(): IUserExtra {
    return {
      ...new UserExtra(),
      id: this.editForm.get(['id'])!.value,
      gramarScore: this.editForm.get(['gramarScore'])!.value,
      vocabularyScore: this.editForm.get(['vocabularyScore'])!.value,
      lisningScore: this.editForm.get(['lisningScore'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      languageId: this.editForm.get(['languageId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtra>>): void {
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
