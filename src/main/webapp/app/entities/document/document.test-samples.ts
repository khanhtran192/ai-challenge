import dayjs from 'dayjs/esm';

import { IDocument, NewDocument } from './document.model';

export const sampleWithRequiredData: IDocument = {
  id: 26644,
  fileName: 'incidentally before',
  fileType: 'DOCX',
  fileSize: 19269,
  status: 'UPLOADED',
  storagePath: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IDocument = {
  id: 9417,
  fileName: 'as afterwards cleaner',
  fileType: 'DOCX',
  fileSize: 5642,
  status: 'UPLOADED',
  storagePath: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDocument = {
  id: 31599,
  fileName: 'chasuble',
  fileType: 'DOCX',
  fileSize: 32197,
  status: 'SENSITIVE',
  storagePath: '../fake-data/blob/hipster.txt',
  uploadedAt: dayjs('2025-09-06T01:24'),
};

export const sampleWithNewData: NewDocument = {
  fileName: 'vain',
  fileType: 'DOCX',
  fileSize: 8475,
  status: 'ERROR',
  storagePath: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
