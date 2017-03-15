import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetrackSharedModule } from '../../shared';

import {
    TimeTrackService,
    TimeTrackPopupService,
    TimeTrackComponent,
    TimeTrackDetailComponent,
    TimeTrackDialogComponent,
    TimeTrackPopupComponent,
    TimeTrackDeletePopupComponent,
    TimeTrackDeleteDialogComponent,
    timeTrackRoute,
    timeTrackPopupRoute,
} from './';

let ENTITY_STATES = [
    ...timeTrackRoute,
    ...timeTrackPopupRoute,
];

@NgModule({
    imports: [
        TimetrackSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TimeTrackComponent,
        TimeTrackDetailComponent,
        TimeTrackDialogComponent,
        TimeTrackDeleteDialogComponent,
        TimeTrackPopupComponent,
        TimeTrackDeletePopupComponent,
    ],
    entryComponents: [
        TimeTrackComponent,
        TimeTrackDialogComponent,
        TimeTrackPopupComponent,
        TimeTrackDeleteDialogComponent,
        TimeTrackDeletePopupComponent,
    ],
    providers: [
        TimeTrackService,
        TimeTrackPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetrackTimeTrackModule {}
