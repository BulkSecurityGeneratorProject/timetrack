import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { TimeTrack } from './time-track.model';
import { TimeTrackPopupService } from './time-track-popup.service';
import { TimeTrackService } from './time-track.service';
import { Project, ProjectService } from '../project';
import { Employee, EmployeeService } from '../employee';
@Component({
    selector: 'jhi-time-track-dialog',
    templateUrl: './time-track-dialog.component.html'
})
export class TimeTrackDialogComponent implements OnInit {

    timeTrack: TimeTrack;
    authorities: any[];
    isSaving: boolean;

    projects: Project[];

    employees: Employee[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private timeTrackService: TimeTrackService,
        private projectService: ProjectService,
        private employeeService: EmployeeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['timeTrack']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.projectService.query().subscribe(
            (res: Response) => { this.projects = res.json(); }, (res: Response) => this.onError(res.json()));
        this.employeeService.query().subscribe(
            (res: Response) => { this.employees = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.timeTrack.id !== undefined) {
            this.timeTrackService.update(this.timeTrack)
                .subscribe((res: TimeTrack) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.timeTrackService.create(this.timeTrack)
                .subscribe((res: TimeTrack) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: TimeTrack) {
        this.eventManager.broadcast({ name: 'timeTrackListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }

    trackEmployeeById(index: number, item: Employee) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-time-track-popup',
    template: ''
})
export class TimeTrackPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private timeTrackPopupService: TimeTrackPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.timeTrackPopupService
                    .open(TimeTrackDialogComponent, params['id']);
            } else {
                this.modalRef = this.timeTrackPopupService
                    .open(TimeTrackDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
