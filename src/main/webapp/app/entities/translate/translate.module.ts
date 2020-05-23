import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtoshSharedModule } from 'app/shared/shared.module';
import { TranslateComponent } from './translate.component';
import { TranslateDetailComponent } from './translate-detail.component';
import { TranslateUpdateComponent } from './translate-update.component';
import { TranslateDeleteDialogComponent } from './translate-delete-dialog.component';
import { translateRoute } from './translate.route';

@NgModule({
  imports: [ArtoshSharedModule, RouterModule.forChild(translateRoute)],
  declarations: [TranslateComponent, TranslateDetailComponent, TranslateUpdateComponent, TranslateDeleteDialogComponent],
  entryComponents: [TranslateDeleteDialogComponent],
})
export class ArtoshTranslateModule {}
