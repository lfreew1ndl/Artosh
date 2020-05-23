import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ArtoshSharedModule } from 'app/shared/shared.module';
import {WordTranslateGameComponent} from "app/game/word-translate/word-translate-game.component";
import {wordTranslateGameRoute} from "app/game/word-translate/word-translate-game.route";

@NgModule({
  imports: [ArtoshSharedModule, RouterModule.forChild(wordTranslateGameRoute)],
  declarations: [WordTranslateGameComponent],
})
export class ArtoshWordTranslateGameModule {}
