export interface CreateClient {
  name: string;
  cnpj: string;
  password: string;
}

export enum ClientStatus {
  ACTIVE,
  INACTIVE
}

export interface Client {
  id: string;
  cnpj: string;
  password: string;
  status: ClientStatus;
  users: [];
}