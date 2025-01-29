export class User {
    id:number;
    name:string;
    email:string;
    password:string;
    rule:string;

    constructor( id:number, name:string, email:string, password:string, rule:string){

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rule = rule;
    }
}
