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
    RouterModule.forChild([
      {
        path: 'wordTranslateGame/:roomId',
        loadChildren: () => import('./word-translate/word-translate-game.module').then(m => m.ArtoshWordTranslateGameModule),
      },
    ]),
    RouterModule.forChild([
      {
        path: 'games',
        loadChildren: () => import('./game-list/game-list.module').then(m => m.ArtoshGameListModule),
      },
    ]),
  ],
})
export class ArtoshGameModule {}
