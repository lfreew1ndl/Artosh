import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITranslate } from 'app/shared/model/translate.model';
import { TranslateService } from './translate.service';
import { TranslateDeleteDialogComponent } from './translate-delete-dialog.component';

@Component({
  selector: 'jhi-translate',
  templateUrl: './translate.component.html',
})
export class TranslateComponent implements OnInit, OnDestroy {
  translates?: ITranslate[];
  eventSubscriber?: Subscription;

  constructor(protected translateService: TranslateService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.translateService.query().subscribe((res: HttpResponse<ITranslate[]>) => (this.translates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTranslates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITranslate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTranslates(): void {
    this.eventSubscriber = this.eventManager.subscribe('translateListModification', () => this.loadAll());
  }

  delete(translate: ITranslate): void {
    const modalRef = this.modalService.open(TranslateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.translate = translate;
  }
}
