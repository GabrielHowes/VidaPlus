import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';
import { AppointmentStore } from '../../store/appointment.store';
import { AddAppointmentDialogComponent } from '../add-appointment-dialog/add-appointment-dialog.component';
import { AppointmentResponse } from '../../service/appointment.model';

@Component({
  selector: 'app-appointment-list',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule, RouterModule],
  templateUrl: './appointment-list.component.html',
})
export class AppointmentListComponent implements OnInit {
  store = inject(AppointmentStore);
  dialog = inject(MatDialog);

  private expandedIds = new Set<number>();

  ngOnInit(): void {
    this.store.loadAppointments();
  }

  isExpanded(id: number): boolean {
    return this.expandedIds.has(id);
  }

  toggleDetails(id: number): void {
    if (this.expandedIds.has(id)) {
      this.expandedIds.delete(id);
    } else {
      this.expandedIds.clear();
      this.expandedIds.add(id);
    }
  }

  openAddDialog(): void {
    this.dialog.open(AddAppointmentDialogComponent, {
      width: '500px',
      disableClose: true,
    });
  }

  openEditDialog(appointment: AppointmentResponse): void {
    this.dialog.open(AddAppointmentDialogComponent, {
      width: '500px',
      disableClose: true,
      data: appointment,
    });
  }

  deleteAppointment(id: number): void {
    if (confirm('Tem certeza que deseja cancelar esta consulta?')) {
      this.store.deleteAppointment(id);
    }
  }
}
