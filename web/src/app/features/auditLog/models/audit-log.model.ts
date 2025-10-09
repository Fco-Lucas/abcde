export enum AuditLogAction {
  CREATE = "CREATE",
  UPDATE = "UPDATE",
  DELETE = "DELETE",
  RESTORE = "RESTORE",
  LOGIN = "LOGIN",
  PROCESSED = "PROCESSED",
  DOWNLOADTXT = "DOWNLOADTXT"
}

export enum AuditLogProgram {
  CLIENT = "CLIENT",
  CLIENT_USER = "CLIENT_USER",
  LOT = "LOT",
  LOT_IMAGE = "LOT_IMAGE",
  AUTH = "AUTH"
}

export interface AuditLogInterface {
  id: number;
  action: AuditLogAction;
  clientId: string;
  clientName: string;
  userId: string | null;
  userName: string;
  program: AuditLogProgram;
  details: string;
  createdAt: string;
};

export interface PageableAuditLotListInterface {
  content: AuditLogInterface[],
  first: boolean,
  end: boolean,
  page: number,
  size: number,
  pageElements: number,
  totalElements: number,
  totalPages: number
}
