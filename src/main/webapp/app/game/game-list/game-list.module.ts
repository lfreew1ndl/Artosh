import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ArtoshSharedModule } from 'app/shared/shared.module';
import { LobbyComponent } from 'app/game/lobby/lobby.component';
import { lobbyRoute } from 'app/game/lobby/lobby.route';
import { GameListComponent } from 'app/game/game-list/game-list.component';
import { gameListRoute } from 'app/game/game-list/game-list.route';
import { MatTabsModule } from '@angular/material/tabs';

@NgModule({
  imports: [ArtoshSharedModule, RouterModule.forChild(gameListRoute), MatTabsModule],
  declarations: [GameListComponent],
})
export class ArtoshGameListModule {}
