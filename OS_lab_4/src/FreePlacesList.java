public class FreePlaceList {

    private int firstFreeCluster = -1;

    public int searchFreePlace(Disk disk, int size) {
        int countFree = 0;

        for (int i = 0; i < disk.getCells().length; i++) {
            if (disk.getCells()[i].getCellStatus() == 0) {
                if(size == 0){
                    return i;
                }
                countFree++;
                if(countFree == size + 1){
                    firstFreeCluster = i - size;
                    break;
                }
            }
            if(disk.getCells()[i].getCellStatus() != 0){
                countFree = 0;
            }
        }
        return firstFreeCluster;
    }
}
