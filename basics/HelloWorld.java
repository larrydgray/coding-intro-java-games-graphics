// Your first Java program. Compile it, then run it:
//   javac HelloWorld.java
//   java HelloWorld
//   java HelloWorld Larry        (give it a name, if you like)
public class HelloWorld {

    // A FIELD: memory that belongs to each HelloWorld object. It is private, so
    // only code inside this class can touch it (encapsulation).
    private String name;

    // A CONSTRUCTOR: runs when we build a HelloWorld object with "new".
    public HelloWorld(String name) {
        this.name = name;
    }

    // A PRIVATE METHOD: a helper only this class can call. Other languages call
    // this a function or a procedure.
    private String buildGreeting() {
        return "Hello, " + name + "!";
    }

    // main is where Java starts. It has to be public static void so Java can call
    // it before any object exists.
    public static void main(String[] args) {
        // Part 1: the classic
        System.out.println("Hello, World!");

        // Part 2: the extended version, using a class and an object
        String who = args.length > 0 ? args[0] : "Java student";  // a LOCAL variable
        HelloWorld greeter = new HelloWorld(who);                  // build an object
        System.out.println(greeter.buildGreeting());
    }
}
