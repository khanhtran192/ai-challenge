import dayjs from 'dayjs/esm';

import { ISensitiveInfo } from './sensitive-info.model';

export const sampleWithRequiredData: ISensitiveInfo = {
  id: 27872,
  infoType: 'whoever retrospectivity governance',
  content: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ISensitiveInfo = {
  id: 870,
  infoType: 'whose collaboration',
  content: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ISensitiveInfo = {
  id: 18683,
  infoType: 'curiously enthusiastically blah',
  content: '../fake-data/blob/hipster.txt',
  pageNumber: 14374,
  position: 'oof meh underpants',
  detectedAt: dayjs('2025-09-05T21:24'),
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
