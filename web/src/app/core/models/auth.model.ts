export interface AuthPayload {
  login: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}