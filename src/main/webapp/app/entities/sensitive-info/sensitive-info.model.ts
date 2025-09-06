import dayjs from 'dayjs/esm';
import { IDocument } from 'app/entities/document/document.model';

export interface ISensitiveInfo {
  id: number;
  infoType?: string | null;
  content?: string | null;
  pageNumber?: number | null;
  position?: string | null;
  detectedAt?: dayjs.Dayjs | null;
  document?: Pick<IDocument, 'id'> | null;
}
