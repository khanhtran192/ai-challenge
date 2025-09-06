import dayjs from 'dayjs/esm';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { FileType } from 'app/entities/enumerations/file-type.model';
import { DocumentStatus } from 'app/entities/enumerations/document-status.model';

export interface IDocument {
  id: number;
  fileName?: string | null;
  fileType?: keyof typeof FileType | null;
  fileSize?: number | null;
  status?: keyof typeof DocumentStatus | null;
  storagePath?: string | null;
  uploadedAt?: dayjs.Dayjs | null;
  owner?: Pick<IAppUser, 'id'> | null;
}

export type NewDocument = Omit<IDocument, 'id'> & { id: null };
