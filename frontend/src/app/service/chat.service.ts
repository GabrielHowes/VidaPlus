import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {Message, Room, RoomRequest, TypingEvent} from './chat.model';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  http = inject(HttpClient);
  private pendingMessages: (() => void)[] = [];

  private stompClient: Client | undefined;
  private messagesSubject = new Subject<Message>();
  private typingSubject = new Subject<TypingEvent>();

  fetchRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(`/api/chat/room`);
  }

  createRoom(room: RoomRequest): Observable<Room> {
    return this.http.post<Room>(`/api/chat/room`, room);
  }

  fetchMessages(roomId: string): Observable<Message[]> {
    return this.http.get<Message[]>(`/api/chat/message/${roomId}`);
  }

  connect(roomId: string): Observable<Message> {
    this.disconnect();
    this.messagesSubject = new Subject<Message>();

    const token = localStorage.getItem('access_token');

    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:9000/ws/chat'),
      connectHeaders: { Authorization: `Bearer ${token}` },
      reconnectDelay: 5000,

      onConnect: () => {

        this.pendingMessages.forEach(fn => fn());
        this.pendingMessages = [];

        this.stompClient?.subscribe(`/topic/room/${roomId}`, (message) => {
          console.log('[WS] Mensagem raw:', message.body);
          try {
            const parsed: Message = JSON.parse(message.body);
            this.messagesSubject.next(parsed);
          } catch (e) {
            console.error('[WS] Erro ao parsear mensagem:', e);
          }
        });

        this.stompClient?.subscribe(`/topic/typing/${roomId}`, (event) => {
          try {
            const parsed: TypingEvent = JSON.parse(event.body);
            this.typingSubject.next(parsed);
          } catch (e) {
            console.error('[WS] Erro ao parsear typing:', e);
          }
        });

      },

      onWebSocketClose: () => console.log('[WS] Closed'),
      onDisconnect: () => console.log('[WS] Disconnected'),
      onStompError: (frame) => { frame
      },
    });

    this.stompClient.activate();
    return this.messagesSubject.asObservable();
  }

  sendTyping(roomId: string, username: string, typing: boolean): void {
    if (!this.stompClient?.connected) return;

    const token = localStorage.getItem('access_token');
    this.stompClient.publish({
      destination: `/app/chat/${roomId}/typing`,
      headers: { Authorization: `Bearer ${token ?? ''}` },
      body: JSON.stringify({ username, roomId, typing }),
    });
  }

  getTypingEvents(): Observable<TypingEvent> {
    return this.typingSubject.asObservable();
  }

  sendMessage(roomId: string, request: any): void {
    const token = localStorage.getItem('access_token');

    const publish = () => {
      this.stompClient?.publish({
        destination: `/app/chat/${roomId}/sendMessage`,
        headers: { Authorization: `Bearer ${token ?? ''}` },
        body: JSON.stringify({
          content: request.message,
          username: request.username,
          role: request.role
        }),
      });
    };

    if (this.stompClient?.connected) {
      publish();
    } else {
      console.warn('[WS] Ainda conectando, mensagem enfileirada...');
      this.pendingMessages.push(publish);
    }
  }

  disconnect(): void {
    this.stompClient?.deactivate();
    this.stompClient = undefined;
  }
}
