export interface AppointmentResponse {
  id: number;
  patientName: string;
  doctorName: string;
  appointmentDate: string;
  notes?: string;
  status?: 'SCHEDULED' | 'COMPLETED' | 'CANCELLED' | 'CONFIRMED';
}

export interface AppointmentRequest {
  doctorId: number;
  appointmentDate: string;
  type: string;
  status: string;
  notes: string;
}
