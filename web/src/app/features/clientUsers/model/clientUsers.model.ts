import type { PermissionInterface } from "../../permissions/models/permission.model";

export interface CreateClientUserInterface {
  clientId: string;
  name: string;
  email: string;
  password: string;
  permission: number;
}

export enum ClientUserStatus {
  ACTIVE = "ACTIVE",
  INACTIVE = "INACTIVE"
}

export interface ClientUserInterface {
  id: string;
  clientId: string;
  name: string;
  email: string;
  permission: PermissionInterface;
  status: ClientUserStatus
}

export interface PageableClientUsersList {
  content: ClientUserInterface[],
  first: boolean,
  end: boolean,
  page: number,
  size: number,
  pageElements: number,
  totalElements: number,
  totalPages: number
}

export interface UpdateClientUserInterface {
  clientId: string,
  name: string,
  email: string,
  permission: number,
}