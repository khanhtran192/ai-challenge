import dayjs from 'dayjs/esm';

import { IDocumentShare, NewDocumentShare } from './document-share.model';

export const sampleWithRequiredData: IDocumentShare = {
  id: 22894,
  permission: 'VIEW',
};

export const sampleWithPartialData: IDocumentShare = {
  id: 24497,
  permission: 'VIEW',
};

export const sampleWithFullData: IDocumentShare = {
  id: 16655,
  permission: 'VIEW',
  grantedAt: dayjs('2025-09-05T17:10'),
};

export const sampleWithNewData: NewDocumentShare = {
  permission: 'VIEW',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
