import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { TimeTrack } from './time-track.model';
import { TimeTrackService } from './time-track.service';
@Injectable()
export class TimeTrackPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private timeTrackService: TimeTrackService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.timeTrackService.find(id).subscribe(timeTrack => {
                timeTrack.timeFrom = this.datePipe
                    .transform(timeTrack.timeFrom, 'yyyy-MM-ddThh:mm');
                timeTrack.timeTo = this.datePipe
                    .transform(timeTrack.timeTo, 'yyyy-MM-ddThh:mm');
                this.timeTrackModalRef(component, timeTrack);
            });
        } else {
            return this.timeTrackModalRef(component, new TimeTrack());
        }
    }

    timeTrackModalRef(component: Component, timeTrack: TimeTrack): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.timeTrack = timeTrack;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
