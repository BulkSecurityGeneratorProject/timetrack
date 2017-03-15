import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { TimeTrack } from './time-track.model';
import { TimeTrackPopupService } from './time-track-popup.service';
import { TimeTrackService } from './time-track.service';

@Component({
    selector: 'jhi-time-track-delete-dialog',
    templateUrl: './time-track-delete-dialog.component.html'
})
export class TimeTrackDeleteDialogComponent {

    timeTrack: TimeTrack;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private timeTrackService: TimeTrackService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['timeTrack']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.timeTrackService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timeTrackListModification',
                content: 'Deleted an timeTrack'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-track-delete-popup',
    template: ''
})
export class TimeTrackDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private timeTrackPopupService: TimeTrackPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.timeTrackPopupService
                .open(TimeTrackDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
