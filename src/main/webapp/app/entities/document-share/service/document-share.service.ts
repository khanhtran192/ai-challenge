import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentShare, NewDocumentShare } from '../document-share.model';

export type PartialUpdateDocumentShare = Partial<IDocumentShare> & Pick<IDocumentShare, 'id'>;

type RestOf<T extends IDocumentShare | NewDocumentShare> = Omit<T, 'grantedAt'> & {
  grantedAt?: string | null;
};

export type RestDocumentShare = RestOf<IDocumentShare>;

export type NewRestDocumentShare = RestOf<NewDocumentShare>;

export type PartialUpdateRestDocumentShare = RestOf<PartialUpdateDocumentShare>;

export type EntityResponseType = HttpResponse<IDocumentShare>;
export type EntityArrayResponseType = HttpResponse<IDocumentShare[]>;

@Injectable({ providedIn: 'root' })
export class DocumentShareService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/document-shares');

  create(documentShare: NewDocumentShare): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentShare);
    return this.http
      .post<RestDocumentShare>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(documentShare: IDocumentShare): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentShare);
    return this.http
      .put<RestDocumentShare>(`${this.resourceUrl}/${this.getDocumentShareIdentifier(documentShare)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(documentShare: PartialUpdateDocumentShare): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentShare);
    return this.http
      .patch<RestDocumentShare>(`${this.resourceUrl}/${this.getDocumentShareIdentifier(documentShare)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDocumentShare>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDocumentShare[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocumentShareIdentifier(documentShare: Pick<IDocumentShare, 'id'>): number {
    return documentShare.id;
  }

  compareDocumentShare(o1: Pick<IDocumentShare, 'id'> | null, o2: Pick<IDocumentShare, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocumentShareIdentifier(o1) === this.getDocumentShareIdentifier(o2) : o1 === o2;
  }

  addDocumentShareToCollectionIfMissing<Type extends Pick<IDocumentShare, 'id'>>(
    documentShareCollection: Type[],
    ...documentSharesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const documentShares: Type[] = documentSharesToCheck.filter(isPresent);
    if (documentShares.length > 0) {
      const documentShareCollectionIdentifiers = documentShareCollection.map(documentShareItem =>
        this.getDocumentShareIdentifier(documentShareItem),
      );
      const documentSharesToAdd = documentShares.filter(documentShareItem => {
        const documentShareIdentifier = this.getDocumentShareIdentifier(documentShareItem);
        if (documentShareCollectionIdentifiers.includes(documentShareIdentifier)) {
          return false;
        }
        documentShareCollectionIdentifiers.push(documentShareIdentifier);
        return true;
      });
      return [...documentSharesToAdd, ...documentShareCollection];
    }
    return documentShareCollection;
  }

  protected convertDateFromClient<T extends IDocumentShare | NewDocumentShare | PartialUpdateDocumentShare>(documentShare: T): RestOf<T> {
    return {
      ...documentShare,
      grantedAt: documentShare.grantedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDocumentShare: RestDocumentShare): IDocumentShare {
    return {
      ...restDocumentShare,
      grantedAt: restDocumentShare.grantedAt ? dayjs(restDocumentShare.grantedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDocumentShare>): HttpResponse<IDocumentShare> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDocumentShare[]>): HttpResponse<IDocumentShare[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
