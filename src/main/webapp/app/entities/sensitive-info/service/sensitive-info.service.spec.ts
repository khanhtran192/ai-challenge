import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISensitiveInfo } from '../sensitive-info.model';
import { sampleWithFullData, sampleWithPartialData, sampleWithRequiredData } from '../sensitive-info.test-samples';

import { RestSensitiveInfo, SensitiveInfoService } from './sensitive-info.service';

const requireRestSample: RestSensitiveInfo = {
  ...sampleWithRequiredData,
  detectedAt: sampleWithRequiredData.detectedAt?.toJSON(),
};

describe('SensitiveInfo Service', () => {
  let service: SensitiveInfoService;
  let httpMock: HttpTestingController;
  let expectedResult: ISensitiveInfo | ISensitiveInfo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SensitiveInfoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SensitiveInfo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    describe('addSensitiveInfoToCollectionIfMissing', () => {
      it('should add a SensitiveInfo to an empty array', () => {
        const sensitiveInfo: ISensitiveInfo = sampleWithRequiredData;
        expectedResult = service.addSensitiveInfoToCollectionIfMissing([], sensitiveInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sensitiveInfo);
      });

      it('should not add a SensitiveInfo to an array that contains it', () => {
        const sensitiveInfo: ISensitiveInfo = sampleWithRequiredData;
        const sensitiveInfoCollection: ISensitiveInfo[] = [
          {
            ...sensitiveInfo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSensitiveInfoToCollectionIfMissing(sensitiveInfoCollection, sensitiveInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SensitiveInfo to an array that doesn't contain it", () => {
        const sensitiveInfo: ISensitiveInfo = sampleWithRequiredData;
        const sensitiveInfoCollection: ISensitiveInfo[] = [sampleWithPartialData];
        expectedResult = service.addSensitiveInfoToCollectionIfMissing(sensitiveInfoCollection, sensitiveInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sensitiveInfo);
      });

      it('should add only unique SensitiveInfo to an array', () => {
        const sensitiveInfoArray: ISensitiveInfo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sensitiveInfoCollection: ISensitiveInfo[] = [sampleWithRequiredData];
        expectedResult = service.addSensitiveInfoToCollectionIfMissing(sensitiveInfoCollection, ...sensitiveInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sensitiveInfo: ISensitiveInfo = sampleWithRequiredData;
        const sensitiveInfo2: ISensitiveInfo = sampleWithPartialData;
        expectedResult = service.addSensitiveInfoToCollectionIfMissing([], sensitiveInfo, sensitiveInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sensitiveInfo);
        expect(expectedResult).toContain(sensitiveInfo2);
      });

      it('should accept null and undefined values', () => {
        const sensitiveInfo: ISensitiveInfo = sampleWithRequiredData;
        expectedResult = service.addSensitiveInfoToCollectionIfMissing([], null, sensitiveInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sensitiveInfo);
      });

      it('should return initial array if no SensitiveInfo is added', () => {
        const sensitiveInfoCollection: ISensitiveInfo[] = [sampleWithRequiredData];
        expectedResult = service.addSensitiveInfoToCollectionIfMissing(sensitiveInfoCollection, undefined, null);
        expect(expectedResult).toEqual(sensitiveInfoCollection);
      });
    });

    describe('compareSensitiveInfo', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSensitiveInfo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 9267 };
        const entity2 = null;

        const compareResult1 = service.compareSensitiveInfo(entity1, entity2);
        const compareResult2 = service.compareSensitiveInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 9267 };
        const entity2 = { id: 22493 };

        const compareResult1 = service.compareSensitiveInfo(entity1, entity2);
        const compareResult2 = service.compareSensitiveInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 9267 };
        const entity2 = { id: 9267 };

        const compareResult1 = service.compareSensitiveInfo(entity1, entity2);
        const compareResult2 = service.compareSensitiveInfo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
