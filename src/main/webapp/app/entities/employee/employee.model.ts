import { User } from '../../shared';
import { TimeTrack } from '../time-track';
export class Employee {
    constructor(
        public id?: number,
        public firstname?: string,
        public lastname?: string,
        public email?: string,
        public phonenumber?: string,
        public salary?: number,
        public user?: User,
        public timetrack?: TimeTrack,
    ) {
    }
}
