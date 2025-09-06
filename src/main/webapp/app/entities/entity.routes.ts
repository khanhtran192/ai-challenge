import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'aiChallengeApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'app-user',
    data: { pageTitle: 'aiChallengeApp.appUser.home.title' },
    loadChildren: () => import('./app-user/app-user.routes'),
  },
  {
    path: 'document',
    data: { pageTitle: 'aiChallengeApp.document.home.title' },
    loadChildren: () => import('./document/document.routes'),
  },
  {
    path: 'sensitive-info',
    data: { pageTitle: 'aiChallengeApp.sensitiveInfo.home.title' },
    loadChildren: () => import('./sensitive-info/sensitive-info.routes'),
  },
  {
    path: 'document-share',
    data: { pageTitle: 'aiChallengeApp.documentShare.home.title' },
    loadChildren: () => import('./document-share/document-share.routes'),
  },
  {
    path: 'audit-log',
    data: { pageTitle: 'aiChallengeApp.auditLog.home.title' },
    loadChildren: () => import('./audit-log/audit-log.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
