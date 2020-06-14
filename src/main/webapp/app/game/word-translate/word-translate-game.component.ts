import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { WordTranslateGameService } from 'app/game/word-translate/word-translate-game.service';
import { WordTranslateGameAction } from 'app/core/game/word-translate-game-action';

class WordTranslateData {
  orderNumberOfWord: number | undefined;
  currentWord: String | undefined;
  translateOptions: Array<String> | undefined;
}

@Component({
  selector: 'jhi-word-translate-game',
  templateUrl: './word-translate-game.component.html',
  styleUrls: ['./word-translate-game.component.scss'],
})
export class WordTranslateGameComponent implements OnInit, OnDestroy {
  subscription?: Subscription;
  roomId: number | undefined;
  testString: string | undefined;
  gameData: WordTranslateData | undefined;
  isGameStarted = false;

  constructor(private gameService: WordTranslateGameService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const roomId = params['roomId'];
      this.roomId = roomId;
      this.gameService.connect(roomId);
      this.gameService.subscribe();
      this.subscription = this.gameService.receive().subscribe((gameAction: WordTranslateGameAction) => {
        this.processGameAction(gameAction);
      });
    });
  }

  ngOnDestroy(): void {
    this.gameService.unsubscribe();
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private processGameAction(gameAction: WordTranslateGameAction): any {
    switch (gameAction.action) {
      case 'game_confirmed': {
        this.testString = gameAction.action;
        break;
      }
      case 'next_card': {
        this.gameData = gameAction.data;
        this.isGameStarted = true;
        break;
      }
      default: {
        this.testString = gameAction.action;
        break;
      }
    }
  }

  chooseAnswer(option: String | undefined): void {
    const gameAction = new WordTranslateGameAction('answer', option);
    this.gameService.produceGameAction(gameAction);
  }
}
