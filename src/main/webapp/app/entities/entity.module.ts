import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TimetrackEmployeeModule } from './employee/employee.module';
import { TimetrackProjectModule } from './project/project.module';
import { TimetrackTimeTrackModule } from './time-track/time-track.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TimetrackEmployeeModule,
        TimetrackProjectModule,
        TimetrackTimeTrackModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetrackEntityModule {}
