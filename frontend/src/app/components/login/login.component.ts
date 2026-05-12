import { Component, inject, NgZone } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import {MatIcon} from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatIcon, RouterModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private ngZone = inject(NgZone);

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  login() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: () => {
          setTimeout(() => {
            this.ngZone.run(() => {
              this.router.navigate(['/']);
            });
          }, 100);
        },
        error: (err) => {
          this.snackBar.open('Falha no login: ' + (err.error?.message || 'Erro desconhecido'), 'Fechar', {
            duration: 3000
          });
        }
      });
    }
  }
}
