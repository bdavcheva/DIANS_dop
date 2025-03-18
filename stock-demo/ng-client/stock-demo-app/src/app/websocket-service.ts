import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client = new Client();
  private stockUpdates = new Subject<any>();

  constructor() {
    this.connect();
  }

  private connect() {
    const socket = new SockJS('/ws');
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: (msg) => console.log(msg),
      onConnect: () => {
        this.stompClient.subscribe('/topic/stocks', (message) => {
          this.stockUpdates.next(JSON.parse(message.body));
        });
      }
    });

    this.stompClient.activate();
  }

  getStockUpdates(): Observable<any> {
    return this.stockUpdates.asObservable();
  }
}
