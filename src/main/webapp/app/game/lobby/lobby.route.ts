import { Routes } from '@angular/router';

import { Authority } from 'app/shared/constants/authority.constants';
import {LobbyComponent} from "./lobby.component";

export const lobbyRoute: Routes = [
  {
    path: '',
    component: LobbyComponent,
    data: {
      authorities: [Authority.USER],
      // eslint-disable-next-line
      pageTitle: 'artoshApp.category.home.title', //todo rename it
    },
    // eslint-disable-next-line
    // canActivate: [UserRouteAccessService], //todo remove it
  }
];
