export interface CreateUserInterface {
  name: String,
  cpf: String,
  password: String
}

export enum UserStatus {
  ACTIVE = "ACTIVE",
  INACTIVE = "INACTIVE",
}

export interface User {
  id: String,
  name: String,
  cpf: String,
  status: UserStatus
}