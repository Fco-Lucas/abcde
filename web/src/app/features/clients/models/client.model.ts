import { ClientUserInterface } from "./clientUsers.model";

export interface CreateClientInterface {
  name: string;
  email: string;
  cnpj: string;
  password?: string|null;
  customerComputex: boolean;
  numberContract?: number|null;
  urlToPost: string;
  imageActiveDays: number;
}

export enum ClientStatus {
  ACTIVE = "ACTIVE",
  INACTIVE = "INACTIVE"
}

export interface Client {
  id: string;
  name: string;
  email: string;
  cnpj: string;
  customerComputex: boolean;
  numberContract: number|null;
  urlToPost: string;
  imageActiveDays: number;
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
  email?: string;
  cnpj?: string,
  urlToPost?: string;
  customerComputex?: boolean;
  numberContract?: number|null;
  imageActiveDays?: number;
}

export interface UpdateClientPasswordInterface {
  newPassword: string;
  confirmNewPassword: string;
}