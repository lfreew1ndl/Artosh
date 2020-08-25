import {Component, OnDestroy, OnInit} from '@angular/core';
import {RoomModel} from 'app/core/game/room-model';
import {Subscription} from 'rxjs';
import {LobbyService} from 'app/game/lobby/lobby.service';
import {Router} from '@angular/router';

@Component({
  selector: 'jhi-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss'],
})
export class LobbyComponent implements OnInit, OnDestroy {
  subscription?: Subscription;
  roomModel: RoomModel | undefined;
  test = 'test';
  constructor(private lobbyService: LobbyService, private router: Router) {
  }

  showActivity(roomModel: RoomModel): void {
    this.roomModel = roomModel;
    this.router.navigate(['wordTranslateGame/' + roomModel.roomId]
    );
  }


  ngOnInit(): void {
    // this.router.onSameUrlNavigation = 'reload';
    this.test = Math.random().toString();
    this.lobbyService.connect();
    this.lobbyService.subscribe();
    this.subscription = this.lobbyService.receive().subscribe((roomModel: RoomModel) => {
      this.lobbyService.unsubscribe();
      this.showActivity(roomModel);
    });

  }


  ngOnDestroy(): void {
    // this.router.onSameUrlNavigation = 'ignore';
    this.lobbyService.unsubscribe();
    this.lobbyService.disconnect();
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  cancelGame(): void {
    this.router.navigate(['games']);
  }
}
