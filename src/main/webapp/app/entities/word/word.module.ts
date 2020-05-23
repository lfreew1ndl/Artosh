import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtoshSharedModule } from 'app/shared/shared.module';
import { WordComponent } from './word.component';
import { WordDetailComponent } from './word-detail.component';
import { WordUpdateComponent } from './word-update.component';
import { WordDeleteDialogComponent } from './word-delete-dialog.component';
import { wordRoute } from './word.route';

@NgModule({
  imports: [ArtoshSharedModule, RouterModule.forChild(wordRoute)],
  declarations: [WordComponent, WordDetailComponent, WordUpdateComponent, WordDeleteDialogComponent],
  entryComponents: [WordDeleteDialogComponent],
})
export class ArtoshWordModule {}
