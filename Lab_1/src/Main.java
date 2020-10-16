public class Main {

    public static void main(String[] args) {
        Stack stack = new Stack();
        Core core = new Core(stack);
        core.printSystemCalls();

        stack.push(15);
        stack.push(10);
        stack.push(16);
        core.performSystemCall(0);

        stack.push("str");
        stack.push(15.45);
        stack.push("str");
        core.performSystemCall(1);

        stack.push(15);
        stack.push("str");
        core.performSystemCall(2);

        stack.push(15);
        stack.push("str");
        core.performSystemCall(3);

        stack.push("str");
        stack.push(15.4);
        stack.push(15);
        core.performSystemCall(4);

        core.performSystemCall(5);
    }
}
