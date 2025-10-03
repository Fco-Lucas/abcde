export interface LotImageQuestionInterface {
  id: number;
  number: number;
  alternative: string;
}

export enum LotImageStatusEnum {
  ACTIVE = "ACTIVE",
  INACTIVE = "INACTIVE",
}

export interface lotImageInterface {
  id: number;
  lotId: number;
  url: string;
  originalName: string;
  matricula: number;
  nomeAluno: string;
  etapa: string;
  prova: number;
  gabarito: string;
  presenca: number;
  qtdQuestoes: number;
  haveModification: boolean;
  status: LotImageStatusEnum;
  createdAt: string;
  questions: LotImageQuestionInterface[];
}

export interface LotImagePageableInterface {
  id: number;
  lotId: number;
  matricula: number;
  nomeAluno: string;
  presenca: number;
  haveModification: boolean;
  status: LotImageStatusEnum;
  url: string;
  expirationImageDate: string;
  createdAt: string;
}

export interface PageableLotImagesList {
  content: LotImagePageableInterface[],
  first: boolean,
  end: boolean,
  page: number,
  size: number,
  pageElements: number,
  totalElements: number,
  totalPages: number
}

export interface UpdateLotImageQuetionInterface {
  lotImageQuestionId: number;
  alternative: string;
  previousAlternative: string;
}

export interface LotImageHashInterface {
  hash: string;
  matricula: number;
  nomeAluno: string;
}