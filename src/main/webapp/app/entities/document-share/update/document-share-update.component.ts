import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDocument } from 'app/entities/document/document.model';
import { DocumentService } from 'app/entities/document/service/document.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { SharePermission } from 'app/entities/enumerations/share-permission.model';
import { DocumentShareService } from '../service/document-share.service';
import { IDocumentShare } from '../document-share.model';
import { DocumentShareFormGroup, DocumentShareFormService } from './document-share-form.service';

@Component({
  selector: 'jhi-document-share-update',
  templateUrl: './document-share-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DocumentShareUpdateComponent implements OnInit {
  isSaving = false;
  documentShare: IDocumentShare | null = null;
  sharePermissionValues = Object.keys(SharePermission);

  documentsSharedCollection: IDocument[] = [];
  appUsersSharedCollection: IAppUser[] = [];

  protected documentShareService = inject(DocumentShareService);
  protected documentShareFormService = inject(DocumentShareFormService);
  protected documentService = inject(DocumentService);
  protected appUserService = inject(AppUserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DocumentShareFormGroup = this.documentShareFormService.createDocumentShareFormGroup();

  compareDocument = (o1: IDocument | null, o2: IDocument | null): boolean => this.documentService.compareDocument(o1, o2);

  compareAppUser = (o1: IAppUser | null, o2: IAppUser | null): boolean => this.appUserService.compareAppUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentShare }) => {
      this.documentShare = documentShare;
      if (documentShare) {
        this.updateForm(documentShare);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentShare = this.documentShareFormService.getDocumentShare(this.editForm);
    if (documentShare.id !== null) {
      this.subscribeToSaveResponse(this.documentShareService.update(documentShare));
    } else {
      this.subscribeToSaveResponse(this.documentShareService.create(documentShare));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentShare>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(documentShare: IDocumentShare): void {
    this.documentShare = documentShare;
    this.documentShareFormService.resetForm(this.editForm, documentShare);

    this.documentsSharedCollection = this.documentService.addDocumentToCollectionIfMissing<IDocument>(
      this.documentsSharedCollection,
      documentShare.document,
    );
    this.appUsersSharedCollection = this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(
      this.appUsersSharedCollection,
      documentShare.user,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.documentService
      .query()
      .pipe(map((res: HttpResponse<IDocument[]>) => res.body ?? []))
      .pipe(
        map((documents: IDocument[]) =>
          this.documentService.addDocumentToCollectionIfMissing<IDocument>(documents, this.documentShare?.document),
        ),
      )
      .subscribe((documents: IDocument[]) => (this.documentsSharedCollection = documents));

    this.appUserService
      .query()
      .pipe(map((res: HttpResponse<IAppUser[]>) => res.body ?? []))
      .pipe(
        map((appUsers: IAppUser[]) => this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(appUsers, this.documentShare?.user)),
      )
      .subscribe((appUsers: IAppUser[]) => (this.appUsersSharedCollection = appUsers));
  }
}
