import {Component} from 'angular2/core';
import { UrlInputComponent } from './url-input.component';
import {ProjectListComponent} from './project-list.component';
import {WorkspaceRoot} from './models';

@Component({
    selector: 'admin',
    templateUrl: 'app/app.component.html',
    directives: [UrlInputComponent, ProjectListComponent]
})

export class AppComponent {
	workspaceRoot:WorkspaceRoot = new WorkspaceRoot();

	print(){
		console.log("workspaceRoot ", this.workspaceRoot);
	}
}