import { Role } from "../core/modules/graphql/generated"

export type UserAccount = {
    id: string,
    role: Role,
    firstName: string,
    lastName: string
}