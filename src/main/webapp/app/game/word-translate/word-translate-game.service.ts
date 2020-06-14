import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { Subscription, ReplaySubject, Subject } from 'rxjs';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';
import { RoomModel } from 'app/core/game/room-model';
import { CSRFService } from 'app/core/auth/csrf.service';
import { WordTranslateGameAction } from 'app/core/game/word-translate-game-action';

@Injectable({ providedIn: 'root' })
export class WordTranslateGameService {
  private stompClient: Stomp.Client | null = null;
  private connectionSubject: ReplaySubject<void> = new ReplaySubject(1);
  private connectionSubscription: Subscription | null = null;
  private stompSubscription: Stomp.Subscription | null = null;
  private listenerSubject: Subject<WordTranslateGameAction> = new Subject();
  private roomId: number | undefined;

  constructor(private router: Router, private csrfService: CSRFService, private location: Location) {}

  connect(roomId: number): void {
    this.roomId = roomId;
    if (this.stompClient && this.stompClient.connected) {
      return;
    }

    // building absolute path so that websocket doesn't fail when deploying with a context path
    let url = '/websocket/game/';
    url = this.location.prepareExternalUrl(url);
    const socket: WebSocket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
    const headers: Stomp.ConnectionHeaders = {};
    headers['X-XSRF-TOKEN'] = this.csrfService.getCSRF('XSRF-TOKEN');
    this.stompClient.connect(headers, () => {
      this.connectionSubject.next();
      const gameAction = new WordTranslateGameAction('confirm_game', undefined);
      this.produceGameAction(gameAction);
    });
  }

  disconnect(): void {
    this.unsubscribe();

    this.connectionSubject = new ReplaySubject(1);

    if (this.stompClient) {
      if (this.stompClient.connected) {
        this.stompClient.disconnect();
      }
      this.stompClient = null;
    }
  }

  receive(): Subject<WordTranslateGameAction> {
    return this.listenerSubject;
  }

  subscribe(): void {
    if (this.connectionSubscription) {
      return;
    }

    this.connectionSubscription = this.connectionSubject.subscribe(() => {
      if (this.stompClient) {
        this.stompSubscription = this.stompClient.subscribe('/topic/game/' + this.roomId, (data: Stomp.Message) => {
          this.listenerSubject.next(JSON.parse(data.body));
        });
      }
    });
  }

  unsubscribe(): void {
    if (this.stompSubscription) {
      this.stompSubscription.unsubscribe();
      this.stompSubscription = null;
    }

    if (this.connectionSubscription) {
      this.connectionSubscription.unsubscribe();
      this.connectionSubscription = null;
    }
  }

  produceGameAction(gameAction: WordTranslateGameAction): void {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(
        '/topic/game/' + this.roomId, // destination
        JSON.stringify(gameAction), // body
        {} // header
      );
    }
  }
}
