import dayjs from 'dayjs/esm';

import { IAuditLog, NewAuditLog } from './audit-log.model';

export const sampleWithRequiredData: IAuditLog = {
  id: 24557,
  action: 'soulful ah',
};

export const sampleWithPartialData: IAuditLog = {
  id: 1162,
  action: 'on',
  detail: '../fake-data/blob/hipster.txt',
  createdAt: dayjs('2025-09-05T15:08'),
};

export const sampleWithFullData: IAuditLog = {
  id: 16792,
  action: 'hasty carelessly neighboring',
  detail: '../fake-data/blob/hipster.txt',
  createdAt: dayjs('2025-09-05T03:05'),
};

export const sampleWithNewData: NewAuditLog = {
  action: 'far apricot',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
