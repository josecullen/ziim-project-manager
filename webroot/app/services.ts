import {Injectable, Inject} from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Headers, RequestOptions} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import {ProjectRoot} from './models';

@Injectable()
export class AppServices {
    items: Array<any>;

    constructor(private http: Http) { }

    getProjectFromPath(path: string, target: string): ProjectRoot {
        return this.http.get(Path[Path.project_from_string] + "?path=" + path + "&target=" + target)
            .map(res => {
                return <ProjectRoot>JSON.parse(res._body);
            });
    }

    runFile(path: string) {
        console.log("run file");
        return this.http.get(Path[Path.run] + "?path=" + path).map(res => res);
    }

    getProjectDirectory() {
        return this.http.get(Path[Path.project_directory])
            .map(res => {
                return <ProjectRoot>JSON.parse(res._body);
            });
    }

}


enum Path {
    run, project_from_string, project_directory
}