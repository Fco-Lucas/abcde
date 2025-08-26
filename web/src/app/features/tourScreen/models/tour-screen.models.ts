export enum TourScreenEnum {
  LOT = "LOT",
  LOTDETAILS = "LOTDETAILS"
}

export interface TourScreenInterface {
  id: number;
  userId: string;
  screen: TourScreenEnum;
  completed: boolean;
}

export interface TourScreenUpdateInterface {
  completed: boolean;
}