import java.util.HashMap;

class SystemCall {
    private Object[] args;
    private int id;

    public SystemCall(int Id,Object[] Args){
        id=Id;
        args=Args;
    }

    public Object[] getArgs(){
        return args;
    }

    public int getId(){
        return id;
    }
}
