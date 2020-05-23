import {Component, OnDestroy, OnInit} from '@angular/core';
import {RoomModel} from "app/core/game/room-model";
import {Subscription} from "rxjs";
import {LobbyService} from "app/game/lobby/lobby.service";
import {Router} from "@angular/router";

@Component({
  selector: 'jhi-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss']
})
export class LobbyComponent implements OnInit, OnDestroy {

  subscription?: Subscription;
  roomModel: RoomModel | undefined;

  constructor(private gameService: LobbyService, private router: Router) {
  }

  showActivity(roomModel: RoomModel): void {
    this.roomModel = roomModel;
    this.router.navigate(["wordTranslateGame/" + roomModel.room])
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
