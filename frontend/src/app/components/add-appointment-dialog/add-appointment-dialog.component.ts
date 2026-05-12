import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AppointmentStore } from '../../store/appointment.store';
import { AppointmentRequest, AppointmentResponse } from '../../service/appointment.model';
import { Medic, MedicService } from '../../service/medic.service';

@Component({
  selector: 'app-add-appointment-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
  ],
  templateUrl: './add-appointment-dialog.component.html',
})
export class AddAppointmentDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  private store = inject(AppointmentStore);
  private medicService = inject(MedicService);
  private dialogRef = inject(MatDialogRef<AddAppointmentDialogComponent>);
  public data = inject<AppointmentResponse | null>(MAT_DIALOG_DATA);

  medics = signal<Medic[]>([]);
  isEditMode = false;

  form = this.fb.group({
    doctorId: [null as number | null, Validators.required],
    appointmentDate: ['', Validators.required],
    type: ['CONSULTATION', Validators.required],
    status: ['SCHEDULED', Validators.required],
    notes: [''],
  });

  ngOnInit(): void {
    this.medicService.list().subscribe({
      next: (medics) => {
        this.medics.set(medics);

        if (this.data) {
          this.isEditMode = true;

          const medic = medics.find(m => m.name === this.data?.doctorName);

          this.form.patchValue({
            doctorId: medic ? medic.id : null,
            appointmentDate: this.data.appointmentDate,
            type: 'CONSULTATION',
            status: this.data.status || 'SCHEDULED',
            notes: this.data.notes || ''
          });
        }
      },
      error: (err) => console.error('Erro ao carregar médicos', err)
    });
  }

  save(): void {
    if (this.form.valid) {
      const formValue = this.form.getRawValue();
      const request: AppointmentRequest = {
        doctorId: formValue.doctorId!,
        appointmentDate: formValue.appointmentDate!,
        type: formValue.type!,
        status: formValue.status!,
        notes: formValue.notes || ''
      };

      if (this.isEditMode && this.data) {
        this.store.updateAppointment({ id: this.data.id, request });
      } else {
        this.store.createAppointment(request);
      }

      this.dialogRef.close();
    }
  }
}
