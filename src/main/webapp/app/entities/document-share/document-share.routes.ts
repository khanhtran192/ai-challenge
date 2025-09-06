import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DocumentShareResolve from './route/document-share-routing-resolve.service';

const documentShareRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/document-share.component').then(m => m.DocumentShareComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/document-share-detail.component').then(m => m.DocumentShareDetailComponent),
    resolve: {
      documentShare: DocumentShareResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/document-share-update.component').then(m => m.DocumentShareUpdateComponent),
    resolve: {
      documentShare: DocumentShareResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/document-share-update.component').then(m => m.DocumentShareUpdateComponent),
    resolve: {
      documentShare: DocumentShareResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default documentShareRoute;
