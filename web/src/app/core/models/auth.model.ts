export interface AuthPayload {
  cpf: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}