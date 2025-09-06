import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDocument } from 'app/entities/document/document.model';
import { DocumentService } from 'app/entities/document/service/document.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { IDocumentShare } from '../document-share.model';
import { DocumentShareService } from '../service/document-share.service';
import { DocumentShareFormService } from './document-share-form.service';

import { DocumentShareUpdateComponent } from './document-share-update.component';

describe('DocumentShare Management Update Component', () => {
  let comp: DocumentShareUpdateComponent;
  let fixture: ComponentFixture<DocumentShareUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentShareFormService: DocumentShareFormService;
  let documentShareService: DocumentShareService;
  let documentService: DocumentService;
  let appUserService: AppUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DocumentShareUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DocumentShareUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentShareUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentShareFormService = TestBed.inject(DocumentShareFormService);
    documentShareService = TestBed.inject(DocumentShareService);
    documentService = TestBed.inject(DocumentService);
    appUserService = TestBed.inject(AppUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Document query and add missing value', () => {
      const documentShare: IDocumentShare = { id: 18494 };
      const document: IDocument = { id: 24703 };
      documentShare.document = document;

      const documentCollection: IDocument[] = [{ id: 24703 }];
      jest.spyOn(documentService, 'query').mockReturnValue(of(new HttpResponse({ body: documentCollection })));
      const additionalDocuments = [document];
      const expectedCollection: IDocument[] = [...additionalDocuments, ...documentCollection];
      jest.spyOn(documentService, 'addDocumentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentShare });
      comp.ngOnInit();

      expect(documentService.query).toHaveBeenCalled();
      expect(documentService.addDocumentToCollectionIfMissing).toHaveBeenCalledWith(
        documentCollection,
        ...additionalDocuments.map(expect.objectContaining),
      );
      expect(comp.documentsSharedCollection).toEqual(expectedCollection);
    });

    it('should call AppUser query and add missing value', () => {
      const documentShare: IDocumentShare = { id: 18494 };
      const user: IAppUser = { id: 14418 };
      documentShare.user = user;

      const appUserCollection: IAppUser[] = [{ id: 14418 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [user];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentShare });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining),
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const documentShare: IDocumentShare = { id: 18494 };
      const document: IDocument = { id: 24703 };
      documentShare.document = document;
      const user: IAppUser = { id: 14418 };
      documentShare.user = user;

      activatedRoute.data = of({ documentShare });
      comp.ngOnInit();

      expect(comp.documentsSharedCollection).toContainEqual(document);
      expect(comp.appUsersSharedCollection).toContainEqual(user);
      expect(comp.documentShare).toEqual(documentShare);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentShare>>();
      const documentShare = { id: 20035 };
      jest.spyOn(documentShareFormService, 'getDocumentShare').mockReturnValue(documentShare);
      jest.spyOn(documentShareService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentShare });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentShare }));
      saveSubject.complete();

      // THEN
      expect(documentShareFormService.getDocumentShare).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentShareService.update).toHaveBeenCalledWith(expect.objectContaining(documentShare));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentShare>>();
      const documentShare = { id: 20035 };
      jest.spyOn(documentShareFormService, 'getDocumentShare').mockReturnValue({ id: null });
      jest.spyOn(documentShareService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentShare: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentShare }));
      saveSubject.complete();

      // THEN
      expect(documentShareFormService.getDocumentShare).toHaveBeenCalled();
      expect(documentShareService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentShare>>();
      const documentShare = { id: 20035 };
      jest.spyOn(documentShareService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentShare });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentShareService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDocument', () => {
      it('should forward to documentService', () => {
        const entity = { id: 24703 };
        const entity2 = { id: 4007 };
        jest.spyOn(documentService, 'compareDocument');
        comp.compareDocument(entity, entity2);
        expect(documentService.compareDocument).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAppUser', () => {
      it('should forward to appUserService', () => {
        const entity = { id: 14418 };
        const entity2 = { id: 16679 };
        jest.spyOn(appUserService, 'compareAppUser');
        comp.compareAppUser(entity, entity2);
        expect(appUserService.compareAppUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
