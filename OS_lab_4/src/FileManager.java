import java.util.Random;

public class FileManager {

    private final File mainFile;
    private final Disk disk;
    private final Random random;

    public FileManager(Disk disk) {
        random = new Random();
        this.disk = disk;
        mainFile = new File("Root", 1, selectMemory(0));
    }

    public File addFile(String fileName, int size) {
        int firstCell = selectMemory(size);
        if (firstCell == -1) {
            return null;
        }
        return new File(fileName, size, firstCell);
    }

    public File addFolder(String fileName) {
        int firstCell = selectMemory(0);
        if (firstCell == -1) {
            return null;
        }
        return new File(fileName, 1, firstCell);
    }

    public void deleteFile(File file) {
        for(int i = file.getFirstCell(); i <= file.getFirstCell() + file.getSize()/ disk.getSizeSector(); i++){
            System.out.println(i + " " + file.getFirstCell() + " " + file.getFirstCell() + " " + file.getSize());
            disk.getCells()[i].setCellStatus(0);
        }
    }

    public int selectMemory(int size) {
        size /= disk.getSizeSector();
        int firstCell = -1;
        FreePlace freePlace = new FreePlace();

        if (freePlace.searchFreePlace(disk, size) == -1) {
            return -1;
        }
        firstCell = freePlace.searchFreePlace(disk, size);
        for (int i = firstCell; i <= size + firstCell; i++) {
            Cell buffer = disk.getCells()[i];
            buffer.setCellStatus(1);
        }
        return firstCell;
    }

    public void selectFile(File file) {
        for(int i = file.getFirstCell(); i <= file.getFirstCell() + file.getSize()/ disk.getSizeSector(); i++){
            disk.getCells()[i].setCellStatus(2);
        }
    }

    public void removeSelecting() {
        for (int i = 0; i < disk.getCells().length; i++) {
            if (disk.getCells()[i].getCellStatus() == 2) {
                disk.getCells()[i].setCellStatus(1);
            }
        }
    }

    public File getMainFile() {
        return mainFile;
    }
}
