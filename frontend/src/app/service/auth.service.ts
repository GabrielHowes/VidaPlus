import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import {ChatStore} from '../store/chat.store';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private chatStore = inject(ChatStore);

  login(credentials: any): Observable<any> {
    return this.http.post<any>('/api/auth/login', credentials).pipe(
      tap(response => {
        const token = response.Token;
        if (token) {
          const decoded = JSON.parse(atob(token.split('.')[1]));

          const username = decoded.preferred_username || decoded.sub;
          let role = 'USER';

          if (decoded.realm_access && decoded.realm_access.roles) {
            const roles = decoded.realm_access.roles.filter((r: string) =>
              !['offline_access', 'uma_authorization', 'default-roles-vidaplus'].includes(r)
            );
            if (roles.length > 0) {
              role = roles[0];
            }
          }

          this.chatStore.updateUser(username, role);
          localStorage.setItem('access_token', token);
        }
      })
    );
  }

  register(data: any): Observable<any> {
    return this.http.post<any>('/api/auth/register', data);
  }

  logout() {
    localStorage.removeItem('access_token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('access_token');
  }
}
