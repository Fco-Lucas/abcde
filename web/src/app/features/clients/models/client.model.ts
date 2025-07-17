import { ClientUserInterface } from "./clientUsers.model";

export interface CreateClient {
  name: string;
  cnpj: string;
  password: string;
}

export enum ClientStatus {
  ACTIVE = "ACTIVE",
  INACTIVE = "INACTIVE"
}

export interface Client {
  id: string;
  name: string;
  cnpj: string;
  password: string;
  status: ClientStatus;
  users: ClientUserInterface[];
}

export interface PageableClientList {
  content: Client[],
  first: boolean,
  end: boolean,
  page: number,
  size: number,
  pageElements: number,
  totalElements: number,
  totalPages: number
}

export interface UpdateClientInterface {
  name?: string,
  cnpj?: string,
}