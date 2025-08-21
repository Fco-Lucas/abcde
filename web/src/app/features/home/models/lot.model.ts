export enum LotStatusEnum {
  COMPLETED = "COMPLETED",
  INCOMPLETED = "INCOMPLETED",
  DELETED = "DELETED"
}

export enum LotTypeEnum {
  ABCDE = "ABCDE",
  VTB = "VTB",
}

export interface LotCreateInterface {
  userId: string;
  name: string;
  type: LotTypeEnum;
}

export interface LotInterface {
  id: number;
  userId: string;
  userName: string;
  userCnpj: string;
  name: string;
  type: LotTypeEnum;
  createdAt: string;
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