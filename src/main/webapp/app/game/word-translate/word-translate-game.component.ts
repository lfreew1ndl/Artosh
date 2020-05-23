import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {WordTranslateGameService} from "app/game/word-translate/word-translate-game.service";
import {WordTranslateGameAction} from "app/core/game/word-translate-game-action";

@Component({
  selector: 'jhi-word-translate-game',
  templateUrl: './word-translate-game.component.html',
  styleUrls: ['./word-translate-game.component.scss']
})
export class WordTranslateGameComponent implements OnInit, OnDestroy {

  subscription?: Subscription;
  roomId: number | undefined;
  testString: string | undefined;

  constructor(private gameService: WordTranslateGameService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let roomId = params["roomId"];
      this.roomId = roomId
      this.gameService.connect(roomId);
      this.gameService.subscribe(roomId);
      this.subscription = this.gameService.receive().subscribe((gameAction: WordTranslateGameAction) => {
        this.processGameAction(gameAction);
      });
    })
  }

  ngOnDestroy(): void {
    this.gameService.unsubscribe();
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private processGameAction(gameAction: WordTranslateGameAction) {
    switch (gameAction.action) {
      case "start_game": {
        this.testString = gameAction.action
        break
      }
      default: {
        this.testString = gameAction.action
        break
      }
    }
  }
}
