import { Routes } from '@angular/router';

import { Authority } from 'app/shared/constants/authority.constants';
import { GameListComponent } from 'app/game/game-list/game-list.component';

export const gameListRoute: Routes = [
  {
    path: '',
    component: GameListComponent,
    data: {
      authorities: [Authority.USER],
      // eslint-disable-next-line
      pageTitle: 'artoshApp.category.home.title', //todo rename it
    },
    // eslint-disable-next-line
    // canActivate: [UserRouteAccessService], //todo remove it
  },
];
