import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentShare } from '../document-share.model';
import { DocumentShareService } from '../service/document-share.service';

const documentShareResolve = (route: ActivatedRouteSnapshot): Observable<null | IDocumentShare> => {
  const id = route.params.id;
  if (id) {
    return inject(DocumentShareService)
      .find(id)
      .pipe(
        mergeMap((documentShare: HttpResponse<IDocumentShare>) => {
          if (documentShare.body) {
            return of(documentShare.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default documentShareResolve;
