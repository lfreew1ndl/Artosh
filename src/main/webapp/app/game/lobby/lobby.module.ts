import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ArtoshSharedModule } from 'app/shared/shared.module';
import {LobbyComponent} from "app/game/lobby/lobby.component";
import {lobbyRoute} from "app/game/lobby/lobby.route";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@NgModule({
    imports: [ArtoshSharedModule, RouterModule.forChild(lobbyRoute), MatProgressSpinnerModule],
  declarations: [LobbyComponent],
})
export class ArtoshLobbyModule {}
