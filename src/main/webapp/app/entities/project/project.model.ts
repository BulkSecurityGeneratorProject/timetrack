import { TimeTrack } from '../time-track';
export class Project {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public timetrack?: TimeTrack,
    ) {
    }
}
