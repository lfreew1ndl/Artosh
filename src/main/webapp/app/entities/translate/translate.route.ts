import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITranslate, Translate } from 'app/shared/model/translate.model';
import { TranslateService } from './translate.service';
import { TranslateComponent } from './translate.component';
import { TranslateDetailComponent } from './translate-detail.component';
import { TranslateUpdateComponent } from './translate-update.component';

@Injectable({ providedIn: 'root' })
export class TranslateResolve implements Resolve<ITranslate> {
  constructor(private service: TranslateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITranslate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((translate: HttpResponse<Translate>) => {
          if (translate.body) {
            return of(translate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Translate());
  }
}

export const translateRoute: Routes = [
  {
    path: '',
    component: TranslateComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'artoshApp.translate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TranslateDetailComponent,
    resolve: {
      translate: TranslateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'artoshApp.translate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TranslateUpdateComponent,
    resolve: {
      translate: TranslateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'artoshApp.translate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TranslateUpdateComponent,
    resolve: {
      translate: TranslateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'artoshApp.translate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
