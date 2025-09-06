import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../document-share.test-samples';

import { DocumentShareFormService } from './document-share-form.service';

describe('DocumentShare Form Service', () => {
  let service: DocumentShareFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentShareFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentShareFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentShareFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            permission: expect.any(Object),
            grantedAt: expect.any(Object),
            document: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });

      it('passing IDocumentShare should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentShareFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            permission: expect.any(Object),
            grantedAt: expect.any(Object),
            document: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });
    });

    describe('getDocumentShare', () => {
      it('should return NewDocumentShare for default DocumentShare initial value', () => {
        const formGroup = service.createDocumentShareFormGroup(sampleWithNewData);

        const documentShare = service.getDocumentShare(formGroup) as any;

        expect(documentShare).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocumentShare for empty DocumentShare initial value', () => {
        const formGroup = service.createDocumentShareFormGroup();

        const documentShare = service.getDocumentShare(formGroup) as any;

        expect(documentShare).toMatchObject({});
      });

      it('should return IDocumentShare', () => {
        const formGroup = service.createDocumentShareFormGroup(sampleWithRequiredData);

        const documentShare = service.getDocumentShare(formGroup) as any;

        expect(documentShare).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocumentShare should not enable id FormControl', () => {
        const formGroup = service.createDocumentShareFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocumentShare should disable id FormControl', () => {
        const formGroup = service.createDocumentShareFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
