import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentRequest, AppointmentResponse } from './appointment.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private http = inject(HttpClient);
  private readonly API_URL = '/api/appointment';

  list(): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(this.API_URL);
  }

  getDetailedList(): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.API_URL}/detailed-list`);
  }

  listByUser(): Observable<AppointmentResponse[]>{
    return this.http.get<AppointmentResponse[]>(`${this.API_URL}/list-by-user`);
  }

  create(request: AppointmentRequest): Observable<AppointmentResponse> {
    return this.http.post<AppointmentResponse>(this.API_URL, request);
  }

  update(id: number, request: AppointmentRequest): Observable<AppointmentResponse> {
    return this.http.put<AppointmentResponse>(`${this.API_URL}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }

  findById(id: number): Observable<AppointmentResponse> {
    return this.http.get<AppointmentResponse>(`${this.API_URL}/${id}`);
  }

  listByMonth(year: number, month: number): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.API_URL}/dashboard/month`, {
      params: { year, month }
    });
  }
}
