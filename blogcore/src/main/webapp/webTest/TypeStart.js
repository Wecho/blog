var Student = /** @class */ (function () {
    function Student(number, age) {
        this.number = number;
        this.age = age;
        this.fullname = number + "" + age;
    }
    return Student;
}());
function greeter(person) {
    return "hello " + person.fullname + " age:" + person.age;
}
var user = new Student(123, 123);
var info = greeter(user);
document.body.innerHTML = info;
