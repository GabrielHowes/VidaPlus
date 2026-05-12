import { inject } from '@angular/core';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { ChatService } from '../service/chat.service';
import {Message, Room, TypingEvent} from '../service/chat.model';
import { tapResponse } from '@ngrx/operators';
import {catchError, EMPTY, merge, pipe, switchMap, tap} from 'rxjs';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { MatSnackBar } from '@angular/material/snack-bar';

type ChatState = {
  username: string | undefined;
  role: string | undefined;
  rooms: Room[] | undefined;
  selectedRoom: Room | undefined;
  messages: Message[];
  isLoading: boolean;
  isSending: boolean;
  typingUsers: string[];
};

const initialState: ChatState = {
  username: undefined,
  role: undefined,
  rooms: undefined,
  selectedRoom: undefined,
  messages: [],
  isLoading: false,
  isSending: false,
  typingUsers: [],
};

export const ChatStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withMethods((store, service = inject(ChatService), snackbar = inject(MatSnackBar)) => ({


    updateUser(username: string, role: string): void {
      patchState(store, { username, role });
    },

    handleTyping(event: TypingEvent): void {
      const current = store.typingUsers() ?? [];
      const username = store.username();

      if (event.username === username) return;

      if (event.typing) {
        if (!current.includes(event.username)) {
          patchState(store, { typingUsers: [...current, event.username] });
        }
      } else {
        patchState(store, { typingUsers: current.filter(u => u !== event.username) });
      }
    },


    fetchRooms: rxMethod<void>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap(() =>
          service.fetchRooms().pipe(
            tapResponse({
              next: (rooms) => patchState(store, { rooms }),
              error: (err: any) =>
                snackbar.open(err.error?.message ?? 'Erro ao buscar salas.', 'Fechar', { duration: 3000 }),
              finalize: () => patchState(store, { isLoading: false }),
            })
          )
        )
      )
    ),

    createRoom: rxMethod<string>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap((name) =>
          service.createRoom({ name }).pipe(
            tapResponse({
              next: (room) =>
                patchState(store, (state) => ({
                  rooms: [...(state.rooms ?? []), room],
                })),
              error: (err: any) =>
                snackbar.open(err.error?.message ?? 'Erro ao criar sala.', 'Fechar', { duration: 3000 }),
              finalize: () => patchState(store, { isLoading: false }),
            })
          )
        )
      )
    ),


    selectRoom(roomId: string): void {
      const room = store.rooms()?.find((r) => r.id === roomId);
      if (!room) return;

      patchState(store, { selectedRoom: room, messages: [] });
      this.connectRoom(roomId);
      this.fetchMessages(roomId);
    },


    fetchMessages: rxMethod<string>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap((roomId) =>
          service.fetchMessages(roomId).pipe(
            tapResponse({
              next: (messages) => patchState(store, { messages }),
              error: (err: any) =>
                snackbar.open(err.error?.message ?? 'Erro ao buscar mensagens.', 'Fechar', { duration: 3000 }),
              finalize: () => patchState(store, { isLoading: false }),
            })
          )
        )
      )
    ),


    sendMessage(content: string): void {
      const room = store.selectedRoom();
      const username = store.username();
      const role = store.role();

      if (!room) {
        snackbar.open('Erro: Nenhuma sala selecionada.', 'Fechar', { duration: 3000 });
        return;
      }
      if (!username) {
        snackbar.open('Erro: Usuário não identificado.', 'Fechar', { duration: 3000 });
        return;
      }
      if (!content.trim()) return;

      patchState(store, { isSending: true });

      service.sendMessage(room.id, {
        message: content,
        username: username,
        role: role ?? 'USER'
      });

      patchState(store, { isSending: false });
    },


    connectRoom: rxMethod<string>(
      pipe(
        switchMap((roomId) => {
          patchState(store, { isLoading: true, typingUsers: [] });

          return merge(
            service.connect(roomId).pipe(
              tap((message: Message) => {
                const current = store.messages() ?? [];
                if (!current.some((m) => m.id === message.id)) {
                  patchState(store, { messages: [...current, message] });
                }
              })
            ),
            service.getTypingEvents().pipe(
              tap((event: TypingEvent) => {
                const current = store.typingUsers() ?? [];
                const username = store.username();
                if (event.username === username) return;

                if (event.typing) {
                  if (!current.includes(event.username)) {
                    patchState(store, { typingUsers: [...current, event.username] });
                  }
                } else {
                  patchState(store, { typingUsers: current.filter(u => u !== event.username) });
                }
              })
            )
          ).pipe(
            catchError((err) => {
              snackbar.open('Erro na conexão WebSocket.', 'Fechar', { duration: 3000 });
              return EMPTY;
            }),
            tap({ finalize: () => patchState(store, { isLoading: false }) })
          );
        })
      )
    ),


    disconnect(): void {
      service.disconnect();
      patchState(store, { selectedRoom: undefined, messages: [] });
    },
  }))
);
