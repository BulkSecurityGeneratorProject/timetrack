import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TimeTrackComponent } from './time-track.component';
import { TimeTrackDetailComponent } from './time-track-detail.component';
import { TimeTrackPopupComponent } from './time-track-dialog.component';
import { TimeTrackDeletePopupComponent } from './time-track-delete-dialog.component';

import { Principal } from '../../shared';


export const timeTrackRoute: Routes = [
  {
    path: 'time-track',
    component: TimeTrackComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'timetrackApp.timeTrack.home.title'
    }
  }, {
    path: 'time-track/:id',
    component: TimeTrackDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'timetrackApp.timeTrack.home.title'
    }
  }
];

export const timeTrackPopupRoute: Routes = [
  {
    path: 'time-track-new',
    component: TimeTrackPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'timetrackApp.timeTrack.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'time-track/:id/edit',
    component: TimeTrackPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'timetrackApp.timeTrack.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'time-track/:id/delete',
    component: TimeTrackDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'timetrackApp.timeTrack.home.title'
    },
    outlet: 'popup'
  }
];
