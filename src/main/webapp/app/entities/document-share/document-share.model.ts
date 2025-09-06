import dayjs from 'dayjs/esm';
import { IDocument } from 'app/entities/document/document.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { SharePermission } from 'app/entities/enumerations/share-permission.model';

export interface IDocumentShare {
  id: number;
  permission?: keyof typeof SharePermission | null;
  grantedAt?: dayjs.Dayjs | null;
  document?: Pick<IDocument, 'id'> | null;
  user?: Pick<IAppUser, 'id'> | null;
}

export type NewDocumentShare = Omit<IDocumentShare, 'id'> & { id: null };
