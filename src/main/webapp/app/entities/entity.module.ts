import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.ArtoshCategoryModule),
      },
      {
        path: 'word',
        loadChildren: () => import('./word/word.module').then(m => m.ArtoshWordModule),
      },
      {
        path: 'language',
        loadChildren: () => import('./language/language.module').then(m => m.ArtoshLanguageModule),
      },
      {
        path: 'translate',
        loadChildren: () => import('./translate/translate.module').then(m => m.ArtoshTranslateModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ArtoshEntityModule {}
