import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SensitiveInfoResolve from './route/sensitive-info-routing-resolve.service';

const sensitiveInfoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sensitive-info.component').then(m => m.SensitiveInfoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sensitive-info-detail.component').then(m => m.SensitiveInfoDetailComponent),
    resolve: {
      sensitiveInfo: SensitiveInfoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sensitiveInfoRoute;
