import { Routes } from '@angular/router';

import { Authority } from 'app/shared/constants/authority.constants';
import {LobbyComponent} from "./lobby.component";

export const lobbyRoute: Routes = [
  {
    path: '',
    component: LobbyComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'artoshApp.category.home.title', //todo rename it
    },
    // canActivate: [UserRouteAccessService], //todo remove it
  }
];
