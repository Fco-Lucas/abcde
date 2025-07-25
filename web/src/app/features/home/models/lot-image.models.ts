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
  questions: LotImageQuestionInterface[];
}

export interface PageableLotImagesList {
  content: lotImageInterface[],
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
}
