export interface Room {
  id: string;
  name: string;
}

export interface RoomRequest {
  name: string
}

export interface TypingEvent {
  username: string;
  roomId: string;
  typing: boolean;
}

export interface Message {
  id: string;
  roomId: string;
  createdAt: string;
  content: string;
  username: string;
  role: string;
}

export interface MessageRequest {
  roomId: string;
  username: string;
  message: string;
}
