import { Component, inject, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { ChatService } from '../../service/chat.service';
import { MatDialog } from '@angular/material/dialog';
import { AddRoomDialogComponent } from '../add-room-dialog/add-room-dialog.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { ChatStore } from '../../store/chat.store';
import { RoomComponent } from "../room/room.component";
import { AuthService } from '../../service/auth.service';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-home',
  imports: [CommonModule, MatButtonModule, RoomComponent, MatIcon],
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit{

  service = inject(ChatService);
  store = inject(ChatStore);
  router = inject(Router);
  readonly dialog = inject(MatDialog);
  private authService = inject(AuthService);

  ngOnInit(): void {
    this.store.fetchRooms();

    const token = localStorage.getItem('access_token');
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));

        const username = payload.preferred_username || payload.name || payload.sub;

        let role = 'USER';
        if (payload.realm_access && payload.realm_access.roles) {
          const roles = payload.realm_access.roles.filter((r: string) =>
            !['offline_access', 'uma_authorization', 'default-roles-vidaplus'].includes(r)
          );
          if (roles.length > 0) {
            role = roles[0];
          }
        }

        if (username) {
          this.store.updateUser(username, role);
        }
      } catch (e) {
        console.error('Erro ao decodificar token', e);
      }
    }
  }

  openDialog() {
    const dialogRef = this.dialog.open(AddRoomDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      if(result) {
        this.store.createRoom(result);
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
