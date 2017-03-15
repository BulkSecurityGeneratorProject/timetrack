import { Project } from '../project';
import { Employee } from '../employee';
export class TimeTrack {
    constructor(
        public id?: number,
        public name?: string,
        public timeFrom?: any,
        public timeTo?: any,
        public project?: Project,
        public employee?: Employee,
    ) {
    }
}
