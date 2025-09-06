import dayjs from 'dayjs/esm';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { IDocument } from 'app/entities/document/document.model';

export interface IAuditLog {
  id: number;
  action?: string | null;
  detail?: string | null;
  createdAt?: dayjs.Dayjs | null;
  user?: Pick<IAppUser, 'id'> | null;
  document?: Pick<IDocument, 'id'> | null;
}

export type NewAuditLog = Omit<IAuditLog, 'id'> & { id: null };
