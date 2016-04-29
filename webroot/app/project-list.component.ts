import {Component, Input} from 'angular2/core';
import {ProjectRoot, WorkspaceRoot, TargetDirectory, SystemDirectory} from './models';
import {AppServices} from './services';
import {HTTP_PROVIDERS} from 'angular2/http';

@Component({
    selector: 'project-list',
    templateUrl: "app/project-list.component.html",
    providers: [HTTP_PROVIDERS, AppServices]

})
export class ProjectListComponent {
	@Input() workspaceRoot:WorkspaceRoot = new WorkspaceRoot();

	constructor(private services:AppServices){}

	createFloors(targetDirectory:TargetDirectory):Array<SystemDirectory>{
		let floors:Array<SystemDirectory> = new Array();

		targetDirectory.systemDirectories.forEach(function(systemDirectory){
			systemDirectory.files.forEach(function(file){
				let floorNumber:number = parseInt(file.name.split("-")[0]);
				
				if(floors.length -1 < floorNumber){
					floors.push(new SystemDirectory());
				}
				if(floorNumber == 99){
					floors[floors.length-1].files.push(file);	
				}else{
					floors[floorNumber].files.push(file);	
				}
				
			});
		});

		return floors;
	}	

	runFile(path:string){
		console.log("runFile path ", path)
		this.services.runFile(path).subscribe(
			res => console.log("OK"),
			error => console.log("ERROR")
		);
	}

}