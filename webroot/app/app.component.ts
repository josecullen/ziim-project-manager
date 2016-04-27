import {Component} from 'angular2/core';
import { UrlInputComponent } from './url-input.component';
import {ProjectListComponent} from './project-list.component';
import {ProjectRoot} from './models';

@Component({
    selector: 'admin',
    templateUrl: 'app/app.component.html',
    directives: [UrlInputComponent, ProjectListComponent]
})

export class AppComponent {
	projectRoot:ProjectRoot = new ProjectRoot();

	print(){
		console.log("projectRoot ", this.projectRoot);
	}
}