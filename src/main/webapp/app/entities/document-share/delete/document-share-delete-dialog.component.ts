import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDocumentShare } from '../document-share.model';
import { DocumentShareService } from '../service/document-share.service';

@Component({
  templateUrl: './document-share-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DocumentShareDeleteDialogComponent {
  documentShare?: IDocumentShare;

  protected documentShareService = inject(DocumentShareService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentShareService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
