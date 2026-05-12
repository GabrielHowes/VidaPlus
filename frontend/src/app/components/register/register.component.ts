import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
  ],
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  registerForm = this.fb.group({
    name: ['', Validators.required],
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    role: ['user', Validators.required],
    cpf: ['', Validators.required],
    crm: [''],
    cardId: [''],
  });

  ngOnInit(): void {
    this.registerForm.get('role')?.valueChanges.subscribe(role => {
      this.updateValidators(role);
    });
  }

  updateValidators(role: string | null): void {
    const crmControl = this.registerForm.get('crm');
    const cardIdControl = this.registerForm.get('cardId');

    crmControl?.clearValidators();
    cardIdControl?.clearValidators();

    if (role !== 'doctor') crmControl?.setValue('');
    if (role !== 'admin') cardIdControl?.setValue('');

    if (role === 'doctor') {
      crmControl?.setValidators([Validators.required]);
    } else if (role === 'admin') {
      cardIdControl?.setValidators([Validators.required]);
    }

    crmControl?.updateValueAndValidity();
    cardIdControl?.updateValueAndValidity();
  }

  register(): void {
    if (this.registerForm.invalid) {
      return;
    }

    const formValue = this.registerForm.getRawValue();

    const credentialInfo: { [key: string]: any } = { cpf: formValue.cpf };

    if (formValue.role === 'doctor') {
      credentialInfo['crm'] = formValue.crm;
    } else if (formValue.role === 'admin') {
      credentialInfo['cardId'] = formValue.cardId;
    }

    const request = {
      name: formValue.name,
      username: formValue.username,
      email: formValue.email,
      password: formValue.password,
      role: formValue.role,
      credentialInfo: credentialInfo,
    };

    this.authService.register(request).subscribe({
      next: () => {
        this.snackBar.open('Usuário registrado com sucesso!', 'Fechar', { duration: 3000 });
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.snackBar.open('Erro ao registrar: ' + (err.error?.message || 'Erro desconhecido'), 'Fechar', { duration: 5000 });
      }
    });
  }
}
