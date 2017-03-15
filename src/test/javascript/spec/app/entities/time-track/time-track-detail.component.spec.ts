import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TimeTrackDetailComponent } from '../../../../../../main/webapp/app/entities/time-track/time-track-detail.component';
import { TimeTrackService } from '../../../../../../main/webapp/app/entities/time-track/time-track.service';
import { TimeTrack } from '../../../../../../main/webapp/app/entities/time-track/time-track.model';

describe('Component Tests', () => {

    describe('TimeTrack Management Detail Component', () => {
        let comp: TimeTrackDetailComponent;
        let fixture: ComponentFixture<TimeTrackDetailComponent>;
        let service: TimeTrackService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TimeTrackDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    TimeTrackService
                ]
            }).overrideComponent(TimeTrackDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimeTrackDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeTrackService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TimeTrack(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.timeTrack).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
