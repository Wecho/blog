class Student{
    public fullname;
    constructor(public number,public age){
        this.fullname = number+""+age;
    }
}
interface Person{
    fullname:String;
    age:String;
}
function greeter(person:Person){
    return "hello "+person.fullname+" age:"+person.age;
}
let user = new Student(123,123);
let info = greeter(user);
document.body.innerHTML = info;