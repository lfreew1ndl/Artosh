import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ArtoshSharedModule } from 'app/shared/shared.module';
import {LobbyComponent} from "app/game/lobby/lobby.component";
import {lobbyRoute} from "app/game/lobby/lobby.route";

@NgModule({
  imports: [ArtoshSharedModule, RouterModule.forChild(lobbyRoute)],
  declarations: [LobbyComponent],
})
export class ArtoshLobbyModule {}
