import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISensitiveInfo } from '../sensitive-info.model';
import { SensitiveInfoService } from '../service/sensitive-info.service';

const sensitiveInfoResolve = (route: ActivatedRouteSnapshot): Observable<null | ISensitiveInfo> => {
  const id = route.params.id;
  if (id) {
    return inject(SensitiveInfoService)
      .find(id)
      .pipe(
        mergeMap((sensitiveInfo: HttpResponse<ISensitiveInfo>) => {
          if (sensitiveInfo.body) {
            return of(sensitiveInfo.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default sensitiveInfoResolve;
