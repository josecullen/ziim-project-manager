import {Component, Output, EventEmitter} from 'angular2/core';
import {AppServices} from './services';
import {HTTP_PROVIDERS} from 'angular2/http';
import {WorkspaceRoot} from './models';

@Component({
    selector: 'url-input',
    template: `
    	<div class="col-xs-12">
	    	<input #inputPath class="col-xs-8">
	    	<input #inputTarget class="col-xs-2">
	    	<button class="btn btn-default" (click)="open(inputPath.value, inputTarget.value)">Cargar</button>
    	</div>
    	`    ,
    providers: [HTTP_PROVIDERS, AppServices]

})
export class UrlInputComponent {
    @Output() openEvent: EventEmitter = new EventEmitter();

    constructor(private services: AppServices) { }

    ngOnInit() {
        this.services.getProjectDirectory().subscribe(
            res => {
                this.openEvent.emit(<WorkspaceRoot>res);
            },
            error => console.log("ERROR")
            )

    }

    open(path: string, target: string) {
        this.services.getProjectFromPath(path, target).subscribe(
            res => {
                this.openEvent.emit(<WorkspaceRoot>res);
            },
            error => console.log("ERROR")
            )
    }


}