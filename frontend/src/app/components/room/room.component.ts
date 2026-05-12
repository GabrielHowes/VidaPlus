import {
  AfterViewChecked,
  Component,
  computed,
  effect,
  ElementRef,
  inject,
  OnDestroy,
  OnInit,
  ViewChild
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatService } from '../../service/chat.service';
import { ChatStore } from '../../store/chat.store';
import { FormsModule, ReactiveFormsModule, FormControl, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-room',
  imports: [CommonModule, FormsModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatButtonModule, MatIcon],
  templateUrl: './room.component.html'
})
export class RoomComponent implements OnDestroy{

  @ViewChild('messagesContainer') private messagesContainer!: ElementRef<HTMLDivElement>;

  service = inject(ChatService);
  store = inject(ChatStore);

  private typingTimeout: any;

  formControl = new FormControl('', Validators.required);

  constructor() {
    effect(() => {
      const messages = this.store.messages();
      if (messages?.length) {
        this.scrollToBottom();
      }
    });
  }

  onTyping(): void {
    const room = this.store.selectedRoom();
    const username = this.store.username();
    if (!room || !username) return;

    this.service.sendTyping(room.id, username, true);

    clearTimeout(this.typingTimeout);
    this.typingTimeout = setTimeout(() => {
      this.service.sendTyping(room.id, username, false);
    }, 2000);
  }


  ngOnDestroy(): void {
    clearTimeout(this.typingTimeout);
  }

  private scrollToBottom() {
    try {
      requestAnimationFrame(() => {
        this.messagesContainer.nativeElement.scrollTop =
          this.messagesContainer.nativeElement.scrollHeight;
      });
    } catch {}
  }

  sendMessage() {
    const content = this.formControl.value?.trim();
    if (!content) return;

    this.store.sendMessage(content);
    this.formControl.reset();
  }

}
