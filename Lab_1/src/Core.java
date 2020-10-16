import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Core {
    private final int systemCallsCount = 5;
    private ArrayList<SystemCall> patterns;
    private Stack stack;
    private SystemCall call;


    public Core(Stack stack1) {
        patterns = new ArrayList<>();
        patterns.add(0, new SystemCall(0, new Object[]{1, 1}));
        patterns.add(1, new SystemCall(1, new Object[]{"string", 1.5, "string"}));
        patterns.add(2, new SystemCall(2, new Object[]{"string", 1}));
        patterns.add(3, new SystemCall(3, new Object[]{"string", "string"}));
        patterns.add(4, new SystemCall(4, new Object[]{1, 1.5, "string"}));
        stack = stack1;
    }


    public void printSystemCalls() {
        for (int i = 0; i < systemCallsCount; i++) {
            StringBuilder str = new StringBuilder("");
            str.append("Id: "+i+" Args: ");
            for (int j = 0; j < patterns.get(i).getArgs().length; j++) {
                str.append(patterns.get(i).getArgs()[j].getClass().getSimpleName()+",");
            }
            str.append("\n");
            System.out.println(str.toString());
        }
    }

    public void performSystemCall(int id) {
        try{
            call = patterns.get(id);
        }
        catch (Exception e){
            System.out.println("Error: Системного вызова с таким id не существует!");
        }

        if (stack.getSize() == 0) {
            System.out.println("Стэк пуст");
            return;
        }
        if (!patterns.contains(call)) {
            System.out.println("Error: Системного вызова с таким id нет");
            return;
        }

        int stackSize = stack.getSize();
        Object[] args = new Object[stackSize];
        for (int i = 0; i < stackSize; i++) {
            args[i] = stack.pop();
        }

        int argsLength = call.getArgs().length;

        if (args.length != call.getArgs().length) {
            System.out.println("Error: Системного вызова с таким количеством аргументов не существует");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].getClass() != call.getArgs()[i].getClass()) {
                System.out.println("Error: Системного вызова с таким набором аргументов не существует");
                return;
            }
        }
        System.out.println("Выполняется системный вызов с id " + id);
    }
}
