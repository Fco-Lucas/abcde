export interface CreateClientUserInterface {
  clientId: string;
  name: string;
  email: string;
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
  permission: number;
  status: ClientUserStatus
}