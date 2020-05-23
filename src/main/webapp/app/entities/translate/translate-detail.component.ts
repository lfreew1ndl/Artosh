import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITranslate } from 'app/shared/model/translate.model';

@Component({
  selector: 'jhi-translate-detail',
  templateUrl: './translate-detail.component.html',
})
export class TranslateDetailComponent implements OnInit {
  translate: ITranslate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ translate }) => (this.translate = translate));
  }

  previousState(): void {
    window.history.back();
  }
}
