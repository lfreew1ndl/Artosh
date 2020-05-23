import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITranslate } from 'app/shared/model/translate.model';
import { TranslateService } from './translate.service';

@Component({
  templateUrl: './translate-delete-dialog.component.html',
})
export class TranslateDeleteDialogComponent {
  translate?: ITranslate;

  constructor(protected translateService: TranslateService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.translateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('translateListModification');
      this.activeModal.close();
    });
  }
}
