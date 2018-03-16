export class WeatherInfo {
  id: string;
  userId: string;
  finished: string;
  city: string;
  date: string;
  temp: string;
  pressure: string;
  description: string;
  lon: string;
  lat: string;
  type: string;
}

export class QueueInfo {
  pos: string;
}

export class ErrorInfo {
  message: string;
}

export class HistoryInfo {
  history: WeatherInfo[];
}

export interface Info {
}

export class InfoResponse {
  type: string;
  info: Info;
}

export interface Handler {
  handle(done: boolean, message: string): void;
}
