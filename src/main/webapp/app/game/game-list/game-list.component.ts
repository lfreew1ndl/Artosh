import {Component, OnInit} from '@angular/core';
import {LobbyService} from "app/game/lobby/lobby.service";
import {ActivatedRoute, NavigationExtras, Router} from "@angular/router";
import {RoomModel} from "app/core/game/room-model";
import {GameResultModalComponent} from "app/game/gameResultModal/game-result-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'jhi-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss'],
})
export class GameListComponent implements OnInit {
  constructor(private router: Router, private modalService: NgbModal) { //todo remove modal
  }

  ngOnInit(): void {
    const modalRef = this.modalService.open(GameResultModalComponent);//todo remove modal
    modalRef.componentInstance.name = 'World';
  }

  openGame(gameType: string): void {
    const navigationExtras: NavigationExtras = {
      queryParams: {
        'game_type': gameType
      },
    };
    this.router.navigate(['lobby'], navigationExtras);
  }
}
