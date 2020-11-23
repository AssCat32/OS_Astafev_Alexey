 public class Page {

    private int ramId = -1;

    public int getRamId() {
        return ramId;
    }

    public void setRamId(int value) {
        ramId = value;
    }

    private boolean isOnHdd = false;

    public boolean getOnHdd() {
        return isOnHdd;
    }

    public void setOnHdd(boolean value) {
        isOnHdd = value;
        
    }

    private boolean isRequest = false;

    public void setRequest(boolean value) {
        isRequest = value;
        if(value){
            status=1;
        }else{
            status=0;
        }
        
    }

    private int status = 0;

    public int getStatus() {
        return status;
    }

   
}
