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
	project:ProjectRoot = new ProjectRoot();
	target:TargetDirectory = new TargetDirectory();
	constructor(private services:AppServices){}

	ngOnInit() {
        this.services.getProjectDirectory().subscribe(
            res => {
                this.workspaceRoot = <WorkspaceRoot>res;
                this.project = this.workspaceRoot.projectRoots[0];
                this.target = this.project.targetDirectories[0];
            },
            error => console.log("ERROR")
            )

    }

    display(){
    	return this.project != undefined ? 'inline-block' : 'none';
    }

    print(project:ProjectRoot){
    	console.log("project ",project);
    }

    setProject(p){
    	this.project = this.workspaceRoot.projectRoots[p];
    }

    selectTarget(t){
    	this.target = this.project.targetDirectories[t];
    }

	createFloors(targetDirectory:TargetDirectory):Array<SystemDirectory>{
		let floors:Array<SystemDirectory> = new Array();
		
		targetDirectory.systemDirectories[0].files.forEach(sysDir => floors.push(new SystemDirectory()))

		targetDirectory.systemDirectories
			.map(sysDir => sysDir.files)
			.forEach(files => {
				let count = 0;
				let subLevels = 0;
				
				files
					.filter(file => file.name.substr(0,1) == "S")
					.forEach(file => console.log("subLevels ",subLevels++))

				count = subLevels-1;

				files
					.filter(file => file.name.substr(0,1) == "S")
					.forEach(file => {
						floors[count].floor = file.name.substr(0,3)
						floors[count--].files.push(file)
					})

				count = subLevels;

				files
					.filter(file => file.name.substr(0,1) != "S")
					.forEach(file => {
						floors[count].floor = file.name.substr(0,2)
						floors[count++].files.push(file)
					})
			});

		return floors;
	}	

	filterSystemName(name:string):string{
		var result = name.split("-");
		result.shift();
		if(result.length > 0)
			return result.reduce((value,newVal) => value += "-"+newVal);
		else 
			return name;
	}

	filterFileName(name:string):string{
		var result = name.split(".")[0].split("-");
		result.shift();
		if(result.length > 0)
			return result.reduce((value,newVal) => value += "-"+newVal);
		else name;
	}


	runFile(path:string){
		this.services.runFile(path).subscribe(
			res => console.log("OK"),
			error => console.log("ERROR")
		);
	}

}