import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { TimeTrack } from './time-track.model';
import { TimeTrackService } from './time-track.service';

@Component({
    selector: 'jhi-time-track-detail',
    templateUrl: './time-track-detail.component.html'
})
export class TimeTrackDetailComponent implements OnInit, OnDestroy {

    timeTrack: TimeTrack;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private timeTrackService: TimeTrackService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['timeTrack']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.timeTrackService.find(id).subscribe(timeTrack => {
            this.timeTrack = timeTrack;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
