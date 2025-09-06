import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IDocumentShare } from '../document-share.model';

@Component({
  selector: 'jhi-document-share-detail',
  templateUrl: './document-share-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class DocumentShareDetailComponent {
  documentShare = input<IDocumentShare | null>(null);

  previousState(): void {
    window.history.back();
  }
}
