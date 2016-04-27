import {Component, Input} from 'angular2/core';
import {ProjectRoot, ProjectStructure, TargetDirectory} from './models';
import {AppServices} from './services';
import {HTTP_PROVIDERS} from 'angular2/http';

@Component({
    selector: 'project-list',
    templateUrl: "app/project-list.component.html",
    providers: [HTTP_PROVIDERS, AppServices]

})
export class ProjectListComponent {
	@Input() projectRoot:ProjectRoot = new ProjectRoot();

	constructor(private services:AppServices){}

	createFloors(projectStructure:ProjectStructure):Array<TargetDirectory>{
		let floors:Array<TargetDirectory> = new Array();

		projectStructure.targetDirectories.forEach(function(targetDirectory){
			targetDirectory.files.forEach(function(file){
				let floorNumber:number = parseInt(file.name.split("-")[0]);
				
				if(floors.length -1 < floorNumber){
					floors.push(new TargetDirectory());
				}

				floors[floorNumber].files.push(file);
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