import { Routes } from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {loginGuard} from './guard/login-guard';
import {LoginComponent} from './components/login/login.component';
import {AppointmentListComponent} from './components/appointment-list/appointment-list.component';
import {RegisterComponent} from './components/register/register.component';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => HomeComponent,
    canActivate: [loginGuard]
  },
  {
    path: 'login',
    loadComponent: () => LoginComponent
  },
  {
    path: 'register',
    loadComponent: () => RegisterComponent
  },
  {
    path: 'appointments',
    loadComponent: () => AppointmentListComponent,
    canActivate: [loginGuard]
  }
];
