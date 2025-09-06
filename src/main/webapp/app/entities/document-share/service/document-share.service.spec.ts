import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDocumentShare } from '../document-share.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../document-share.test-samples';

import { DocumentShareService, RestDocumentShare } from './document-share.service';

const requireRestSample: RestDocumentShare = {
  ...sampleWithRequiredData,
  grantedAt: sampleWithRequiredData.grantedAt?.toJSON(),
};

describe('DocumentShare Service', () => {
  let service: DocumentShareService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocumentShare | IDocumentShare[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentShareService);
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

    it('should create a DocumentShare', () => {
      const documentShare = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(documentShare).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocumentShare', () => {
      const documentShare = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(documentShare).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DocumentShare', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DocumentShare', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DocumentShare', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocumentShareToCollectionIfMissing', () => {
      it('should add a DocumentShare to an empty array', () => {
        const documentShare: IDocumentShare = sampleWithRequiredData;
        expectedResult = service.addDocumentShareToCollectionIfMissing([], documentShare);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentShare);
      });

      it('should not add a DocumentShare to an array that contains it', () => {
        const documentShare: IDocumentShare = sampleWithRequiredData;
        const documentShareCollection: IDocumentShare[] = [
          {
            ...documentShare,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocumentShareToCollectionIfMissing(documentShareCollection, documentShare);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocumentShare to an array that doesn't contain it", () => {
        const documentShare: IDocumentShare = sampleWithRequiredData;
        const documentShareCollection: IDocumentShare[] = [sampleWithPartialData];
        expectedResult = service.addDocumentShareToCollectionIfMissing(documentShareCollection, documentShare);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentShare);
      });

      it('should add only unique DocumentShare to an array', () => {
        const documentShareArray: IDocumentShare[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const documentShareCollection: IDocumentShare[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentShareToCollectionIfMissing(documentShareCollection, ...documentShareArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentShare: IDocumentShare = sampleWithRequiredData;
        const documentShare2: IDocumentShare = sampleWithPartialData;
        expectedResult = service.addDocumentShareToCollectionIfMissing([], documentShare, documentShare2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentShare);
        expect(expectedResult).toContain(documentShare2);
      });

      it('should accept null and undefined values', () => {
        const documentShare: IDocumentShare = sampleWithRequiredData;
        expectedResult = service.addDocumentShareToCollectionIfMissing([], null, documentShare, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentShare);
      });

      it('should return initial array if no DocumentShare is added', () => {
        const documentShareCollection: IDocumentShare[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentShareToCollectionIfMissing(documentShareCollection, undefined, null);
        expect(expectedResult).toEqual(documentShareCollection);
      });
    });

    describe('compareDocumentShare', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocumentShare(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 20035 };
        const entity2 = null;

        const compareResult1 = service.compareDocumentShare(entity1, entity2);
        const compareResult2 = service.compareDocumentShare(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 20035 };
        const entity2 = { id: 18494 };

        const compareResult1 = service.compareDocumentShare(entity1, entity2);
        const compareResult2 = service.compareDocumentShare(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 20035 };
        const entity2 = { id: 20035 };

        const compareResult1 = service.compareDocumentShare(entity1, entity2);
        const compareResult2 = service.compareDocumentShare(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
