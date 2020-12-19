public class File {

    private final int size;
    private final String fileName;
    private final int firstCell;

    public File(String fileName, int size, int firstCell) {
        this.fileName = fileName;
        this.size = size;
        this.firstCell = firstCell;
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        return fileName;
    }

    public int getFirstCell() {
        return firstCell;
    }
}
