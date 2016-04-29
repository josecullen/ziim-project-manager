class FileBase{
	constructor(public name:string = "", public path:string = ""){}
}

export class WorkspaceRoot extends FileBase{
	constructor(public projectRoots:Array<ProjectRoot> = []){
		super();
	}
}

export class ProjectRoot extends FileBase{
	constructor(public target:string, public targetDirectories:Array<TargetDirectory> = []){
		super();
	}
}

export class TargetDirectory extends FileBase{
	constructor(public systemDirectories:Array<SystemDirectory> = []){
		super();
	}
}

export class SystemDirectory extends FileBase{
	constructor(public files:Array<FileBase> = []){
		super();
	}
}