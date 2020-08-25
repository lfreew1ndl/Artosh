import {Component, Input} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";


@Component({
  selector: 'jhi-game-result-modal',
  templateUrl: './game-result-modal.component.html',
  styleUrls: ['./game-result-modal.component.scss'],
})
export class GameResultModalComponent {
  @Input() name: any;

  constructor(public activeModal: NgbActiveModal) {
  }
}
