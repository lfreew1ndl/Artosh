import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {WordTranslateGameService} from 'app/game/word-translate/word-translate-game.service';
import {WordTranslateGameAction} from 'app/core/game/word-translate-game-action';
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {GameResultModalComponent} from "app/game/gameResultModal/game-result-modal.component";

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
  resultModal: any;

  constructor(private gameService: WordTranslateGameService, private route: ActivatedRoute, private modalService: NgbModal) {
  }

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
    this.gameService.disconnect();
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  showGameResults(data: any): void {
    const modalRef = this.modalService.open(GameResultModalComponent);
    modalRef.componentInstance.name = 'World';
  }

  private processGameAction(gameAction: WordTranslateGameAction): any {
    switch (gameAction.action) {
      case 'GAME_CONFIRMED': {
        this.testString = gameAction.action;
        break;
      }
      case 'NEXT_CARD': {
        this.gameData = gameAction.data;
        this.isGameStarted = true;
        break;
      }
      case 'END_GAME': {
        this.showGameResults(gameAction.data);
        break;
      }
      default: {
        this.testString = gameAction.action;
        break;
      }
    }
  }

  chooseAnswer(option: String | undefined, orderNumberOfWord: number | undefined): void {
    const data = [
      option,
      orderNumberOfWord
    ]
    const gameAction = new WordTranslateGameAction('answer', data);
    this.gameService.produceGameAction(gameAction);
  }
}
