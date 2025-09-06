import dayjs from 'dayjs/esm';

import { IAppUser, NewAppUser } from './app-user.model';

export const sampleWithRequiredData: IAppUser = {
  id: 19407,
  fullName: 'nephew gah cutover',
  email: 'QuangHung_Truong@hotmail.com',
  passwordHash: 'only tastyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
  role: 'USER',
};

export const sampleWithPartialData: IAppUser = {
  id: 12064,
  fullName: 'complicated',
  email: 'ThienGiang.Bui91@hotmail.com',
  passwordHash: 'provided kindheartedlyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
  role: 'USER',
  createdAt: dayjs('2025-09-05T08:49'),
};

export const sampleWithFullData: IAppUser = {
  id: 1902,
  fullName: 'judgementally',
  email: 'ThuongNga.Phung@yahoo.com',
  passwordHash: 'insist rudely liberalizeXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
  role: 'ADMIN',
  createdAt: dayjs('2025-09-05T06:48'),
  updatedAt: dayjs('2025-09-05T18:39'),
};

export const sampleWithNewData: NewAppUser = {
  fullName: 'economise hepatitis what',
  email: 'KhanhVan87@yahoo.com',
  passwordHash: 'whose assuredXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX',
  role: 'ADMIN',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
