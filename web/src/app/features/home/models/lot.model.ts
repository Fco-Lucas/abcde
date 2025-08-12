export interface LotCreateInterface {
  userId: string;
  name: string;
}

export enum LotStatusEnum {
  COMPLETED = "COMPLETED",
  INCOMPLETED = "INCOMPLETED",
  DELETED = "DELETED"
}

export interface LotInterface {
  id: number;
  userId: string;
  userName: string;
  userCnpj: string;
  name: string;
  numberImages: number;
  status: LotStatusEnum;
  createdByComputex: boolean;
}

export interface PageableLotList {
  content: LotInterface[],
  first: boolean,
  end: boolean,
  page: number,
  size: number,
  pageElements: number,
  totalElements: number,
  totalPages: number
}

export interface LotUpdateInterface {
  name?: string;
  status?: LotStatusEnum
}