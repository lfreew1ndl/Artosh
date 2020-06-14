import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { Subscription, ReplaySubject, Subject } from 'rxjs';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';
import { RoomModel } from 'app/core/game/room-model';
import { CSRFService } from 'app/core/auth/csrf.service';

@Injectable({ providedIn: 'root' })
export class LobbyService {
  private stompClient: Stomp.Client | null = null;
  private connectionSubject: ReplaySubject<void> = new ReplaySubject(1);
  private connectionSubscription: Subscription | null = null;
  private stompSubscription: Stomp.Subscription | null = null;
  private listenerSubject: Subject<RoomModel> = new Subject();

  constructor(private router: Router, private csrfService: CSRFService, private location: Location) {}

  connect(): void {
    if (this.stompClient && this.stompClient.connected) {
      return;
    }

    // building absolute path so that websocket doesn't fail when deploying with a context path
    let url = '/websocket/lobby';
    url = this.location.prepareExternalUrl(url);
    const socket: WebSocket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
    const headers: Stomp.ConnectionHeaders = {};
    headers['X-XSRF-TOKEN'] = this.csrfService.getCSRF('XSRF-TOKEN');
    this.stompClient.connect(headers, () => {
      this.connectionSubject.next();
      this.findGame();
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

  receive(): Subject<RoomModel> {
    return this.listenerSubject;
  }

  subscribe(): void {
    if (this.connectionSubscription) {
      return;
    }

    this.connectionSubscription = this.connectionSubject.subscribe(() => {
      if (this.stompClient) {
        this.stompSubscription = this.stompClient.subscribe('/user/topic/lobby', (data: Stomp.Message) => {
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

  private findGame(): void {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(
        '/topic/lobby', // destination
        JSON.stringify({ type: 'word-translate' }), // body
        {} // header
      );
    }
  }
}
