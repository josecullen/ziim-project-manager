export class FileBase{
	constructor(public name:string = " ", public path:string = " ", public floor:string = " "){}
}

export class WorkspaceRoot extends FileBase{
	constructor(public projectRoots:Array<ProjectRoot> = [new ProjectRoot()]){
		super();
	}
}

export class ProjectRoot extends FileBase{
	constructor(public target?	:string, public targetDirectories:Array<TargetDirectory> = [new TargetDirectory()]){
		super();
	}
}

export class TargetDirectory extends FileBase{
	constructor(public systemDirectories:Array<SystemDirectory> = [new SystemDirectory()]){
		super();
	}
}

export class SystemDirectory extends FileBase{
	constructor(public files:Array<FileBase> = []){
		super();
	}
}