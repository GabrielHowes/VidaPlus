import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

@Component({
  imports: [MatDialogModule, MatButtonModule, FormsModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatIconModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './add-room-dialog.component.html'
})
export class AddRoomDialogComponent {

  formControl = new FormControl({disabled: false, value: null}, Validators.required)

}
