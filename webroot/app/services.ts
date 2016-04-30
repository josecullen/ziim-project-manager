import {Injectable, Inject} from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Headers, RequestOptions} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import {WorkspaceRoot} from './models';

@Injectable()
export class AppServices {
    items: Array<any>;

    constructor(private http: Http) { }

    getProjectFromPath(path: string, target: string): WorkspaceRoot {
        return this.http.get(Path[Path.project_from_string] + "?path=" + path + "&target=" + target)
            .map(res => {
                return <WorkspaceRoot>JSON.parse(res._body);
            });
    }

    runFile(path: string) {
        console.log("run file");
        return this.http.get(Path[Path.run] + "?path=" + path).map(res => res);
    }

    getProjectDirectory():WorkspaceRoot {
        return this.http.get(Path[Path.project_directory])
            .map(res => {
                console.log(JSON.parse(res._body));
                return <WorkspaceRoot>JSON.parse(res._body);
            });
    }

    createProject(projectName:string, levels:number, subLevels:number){
        return this.http.get(Path[Path.new_project] + "?projectName=" + projectName + "&levels=" + levels+"&subLevels="+subLevels)
            .map(res => {
                return "ok";
            });
    }


}


enum Path {
    run, project_from_string, project_directory,new_project
}