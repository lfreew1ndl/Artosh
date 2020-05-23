import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'lobby',
        loadChildren: () => import('./lobby/lobby.module').then(m => m.ArtoshLobbyModule),
      },
    ]),
  ],
})
export class ArtoshGameModule {}
