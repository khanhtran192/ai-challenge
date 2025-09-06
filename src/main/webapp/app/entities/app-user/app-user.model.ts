import dayjs from 'dayjs/esm';
import { Role } from 'app/entities/enumerations/role.model';

export interface IAppUser {
  id: number;
  fullName?: string | null;
  email?: string | null;
  passwordHash?: string | null;
  role?: keyof typeof Role | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewAppUser = Omit<IAppUser, 'id'> & { id: null };
