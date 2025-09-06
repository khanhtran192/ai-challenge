import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISensitiveInfo } from '../sensitive-info.model';

type RestOf<T extends ISensitiveInfo> = Omit<T, 'detectedAt'> & {
  detectedAt?: string | null;
};

export type RestSensitiveInfo = RestOf<ISensitiveInfo>;

export type EntityResponseType = HttpResponse<ISensitiveInfo>;
export type EntityArrayResponseType = HttpResponse<ISensitiveInfo[]>;

@Injectable({ providedIn: 'root' })
export class SensitiveInfoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sensitive-infos');

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSensitiveInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSensitiveInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  getSensitiveInfoIdentifier(sensitiveInfo: Pick<ISensitiveInfo, 'id'>): number {
    return sensitiveInfo.id;
  }

  compareSensitiveInfo(o1: Pick<ISensitiveInfo, 'id'> | null, o2: Pick<ISensitiveInfo, 'id'> | null): boolean {
    return o1 && o2 ? this.getSensitiveInfoIdentifier(o1) === this.getSensitiveInfoIdentifier(o2) : o1 === o2;
  }

  addSensitiveInfoToCollectionIfMissing<Type extends Pick<ISensitiveInfo, 'id'>>(
    sensitiveInfoCollection: Type[],
    ...sensitiveInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sensitiveInfos: Type[] = sensitiveInfosToCheck.filter(isPresent);
    if (sensitiveInfos.length > 0) {
      const sensitiveInfoCollectionIdentifiers = sensitiveInfoCollection.map(sensitiveInfoItem =>
        this.getSensitiveInfoIdentifier(sensitiveInfoItem),
      );
      const sensitiveInfosToAdd = sensitiveInfos.filter(sensitiveInfoItem => {
        const sensitiveInfoIdentifier = this.getSensitiveInfoIdentifier(sensitiveInfoItem);
        if (sensitiveInfoCollectionIdentifiers.includes(sensitiveInfoIdentifier)) {
          return false;
        }
        sensitiveInfoCollectionIdentifiers.push(sensitiveInfoIdentifier);
        return true;
      });
      return [...sensitiveInfosToAdd, ...sensitiveInfoCollection];
    }
    return sensitiveInfoCollection;
  }

  protected convertDateFromClient<T extends ISensitiveInfo>(sensitiveInfo: T): RestOf<T> {
    return {
      ...sensitiveInfo,
      detectedAt: sensitiveInfo.detectedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSensitiveInfo: RestSensitiveInfo): ISensitiveInfo {
    return {
      ...restSensitiveInfo,
      detectedAt: restSensitiveInfo.detectedAt ? dayjs(restSensitiveInfo.detectedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSensitiveInfo>): HttpResponse<ISensitiveInfo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSensitiveInfo[]>): HttpResponse<ISensitiveInfo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
