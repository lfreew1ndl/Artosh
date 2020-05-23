import {Component, OnDestroy, OnInit} from '@angular/core';
import {RoomModel} from "app/core/game/room-model";
import {Subscription} from "rxjs";
import {GameService} from "app/game/game.service";

@Component({
  selector: 'jhi-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss']
})
export class LobbyComponent implements OnInit, OnDestroy {

  subscription?: Subscription;
  roomModel: RoomModel | undefined;

  constructor(private gameService: GameService) {
  }

  showActivity(roomModel: RoomModel): void {
    this.roomModel = roomModel;
  }

  ngOnInit(): void {
    this.gameService.connect();
    this.gameService.subscribe();
    this.subscription = this.gameService.receive().subscribe((roomModel: RoomModel) => {
      this.showActivity(roomModel);
    });
  }

  ngOnDestroy(): void {
    this.gameService.unsubscribe();
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
