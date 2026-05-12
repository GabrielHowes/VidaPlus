import { inject } from '@angular/core';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { AppointmentService } from '../service/appointment.service';
import { AppointmentRequest, AppointmentResponse } from '../service/appointment.model';
import { tapResponse } from '@ngrx/operators';
import { pipe, switchMap, tap } from 'rxjs';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { MatSnackBar } from '@angular/material/snack-bar';

type AppointmentState = {
  appointments: AppointmentResponse[];
  selectedAppointment: AppointmentResponse | undefined;
  isLoading: boolean;
};

const initialState: AppointmentState = {
  appointments: [],
  selectedAppointment: undefined,
  isLoading: false,
};

export const AppointmentStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withMethods((store, service = inject(AppointmentService), snackbar = inject(MatSnackBar)) => ({

    loadAppointments: rxMethod<void>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap(() => {
          return service.getDetailedList().pipe(
            tapResponse({
              next: (appointments) => {
                console.log('Agendamentos carregados:', appointments);
                patchState(store, { appointments });
              },
              error: (err: any) => {
                snackbar.open('Erro ao carregar consultas: ' + (err.error?.message || 'Erro desconhecido'), 'Fechar', { duration: 3000 });
              },
              finalize: () => patchState(store, { isLoading: false }),
            })
          );
        })
      )
    ),

    createAppointment: rxMethod<AppointmentRequest>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap((request) => {
          return service.create(request).pipe(
            tapResponse({
              next: (newAppointment) =>
                patchState(store, (state) => ({
                  appointments: [...state.appointments, newAppointment],
                })),
              error: (err: any) => {
                snackbar.open('Erro ao agendar consulta: ' + (err.error?.message || 'Erro desconhecido'), 'Fechar', { duration: 3000 });
              },
              finalize: () => patchState(store, { isLoading: false }),
            })
          );
        })
      )
    ),

    updateAppointment: rxMethod<{ id: number; request: AppointmentRequest }>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap(({ id, request }) => {
          return service.update(id, request).pipe(
            tapResponse({
              next: (updatedAppointment) =>
                patchState(store, (state) => ({
                  appointments: state.appointments.map((a) =>
                    a.id === id ? { ...a, ...updatedAppointment } : a
                  ),
                })),
              error: (err: any) => {
                snackbar.open('Erro ao atualizar consulta: ' + (err.error?.message || 'Erro desconhecido'), 'Fechar', { duration: 3000 });
              },
              finalize: () => patchState(store, { isLoading: false }),
            })
          );
        })
      )
    ),

    deleteAppointment: rxMethod<number>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap((id) => {
          if (!id) {
            console.error('Tentativa de deletar agendamento sem ID válido.');
            snackbar.open('Erro: ID do agendamento inválido.', 'Fechar', { duration: 3000 });
            patchState(store, { isLoading: false });
            return [];
          }
          return service.delete(id).pipe(
            tapResponse({
              next: () =>
                patchState(store, (state) => ({
                  appointments: state.appointments.filter((a) => a.id !== id),
                })),
              error: (err: any) => {
                snackbar.open('Erro ao cancelar consulta: ' + (err.error?.message || 'Erro desconhecido'), 'Fechar', { duration: 3000 });
              },
              finalize: () => patchState(store, { isLoading: false }),
            })
          );
        })
      )
    ),
  }))
);
