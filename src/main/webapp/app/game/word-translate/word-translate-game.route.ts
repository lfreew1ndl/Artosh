import { Routes } from '@angular/router';

import { Authority } from 'app/shared/constants/authority.constants';
import {WordTranslateGameComponent} from "./word-translate-game.component";

export const wordTranslateGameRoute: Routes = [
  {
    path: '',
    component: WordTranslateGameComponent,
    data: {
      authorities: [Authority.USER],
      // eslint-disable-next-line
      pageTitle: 'artoshApp.category.home.title', //todo rename it
    },
  }
];
