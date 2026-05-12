import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Medic {
  id: number;
  name: string;
  email: string;
  jobTitle: string;
  specialty: string;
  crm: string;
  cpf: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class MedicService {
  private http = inject(HttpClient);
  private readonly API_URL = '/api/medic';

  list(): Observable<Medic[]> {
    return this.http.get<Medic[]>(this.API_URL);
  }
}
