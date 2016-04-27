class FileBase{
	constructor(public name:string = "", public path:string = ""){}
}

export class ProjectRoot extends FileBase{
	constructor(public projectStructures:Array<ProjectStructure> = []){
		super();
	}
}

export class ProjectStructure extends FileBase{
	constructor(public target:string, public targetDirectories:Array<TargetDirectory> = []){
		super();
	}
}

export class TargetDirectory extends FileBase{
	constructor(public files:Array<FileBase> = []){
		super();
	}
}