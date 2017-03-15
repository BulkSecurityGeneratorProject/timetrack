import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TimeTrack } from './time-track.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class TimeTrackService {

    private resourceUrl = 'api/time-tracks';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(timeTrack: TimeTrack): Observable<TimeTrack> {
        let copy: TimeTrack = Object.assign({}, timeTrack);
        copy.timeFrom = this.dateUtils.toDate(timeTrack.timeFrom);
        copy.timeTo = this.dateUtils.toDate(timeTrack.timeTo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(timeTrack: TimeTrack): Observable<TimeTrack> {
        let copy: TimeTrack = Object.assign({}, timeTrack);

        copy.timeFrom = this.dateUtils.toDate(timeTrack.timeFrom);

        copy.timeTo = this.dateUtils.toDate(timeTrack.timeTo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TimeTrack> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.timeFrom = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.timeFrom);
            jsonResponse.timeTo = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.timeTo);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].timeFrom = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].timeFrom);
            jsonResponse[i].timeTo = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].timeTo);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
