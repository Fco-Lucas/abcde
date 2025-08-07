export interface AuditLogQuestionInterface {
  id: number;
  clientName: string;
  imageId: number;
  clientId: string;
  userId: string | null;
  userName: string;
  details: string;
  createdAt: string;
}

export interface PageableAuditLogQuestionList {
  content: AuditLogQuestionInterface[],
  first: boolean,
  end: boolean,
  page: number,
  size: number,
  pageElements: number,
  totalElements: number,
  totalPages: number
}