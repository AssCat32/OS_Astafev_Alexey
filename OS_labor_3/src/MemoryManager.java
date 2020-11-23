import java.util.LinkedList;

public class MemoryManager {

    private PageTable pageTable;

    private Ram ram;

    private HDD hdd;

    public MemoryManager(LinkedList<Page> pageList, Ram ram) {
        pageTable = new PageTable(pageList);
        this.ram = ram;
        hdd = new HDD(ram.getMemory().length * 2);
    }

    public boolean work(int id) {
        int ramIndex = pageTable.getPage(id).getRamId();
        if (ramIndex == -1) {
            System.out.println("    ������������� ���������� ����������, ������� ��������� ������...");
            System.out.println("    ��������� ������ ������������ ����� ���������� �����...");
            if (!ram.getFull()) {
                for (int i = 0; i < ram.getMemory().length; i++) {
                    if (ram.getMemory()[i] == -1) {
                        insertInRam(ram, i, id);
                        System.out.println("    ������� ���������� ������");
                        break;
                    }
                }
            } else {
                System.out.println("    � ����������� ������ ����������� ��������� �����, �������� �������� �������� ��������� �������");
                int minStatus = 5;
                for (int i = 0; i < ram.getMemory().length; i++) {
                    if (pageTable.getPage(ram.getMemory()[i]).getStatus() < minStatus) {
                        minStatus = pageTable.getPage(ram.getMemory()[i]).getStatus();
                    }
                }
                for (int i = 0; i < ram.getMemory().length; i++) {
                    if (pageTable.getPage(ram.getMemory()[i]).getStatus() == minStatus) {
                        pageTable.getPage(ram.getMemory()[i]).setOnHdd(true);
                        System.out.println("    ����������� �������� � id " + ram.getMemory()[i] + " ����������� � ���������� �������� � ������� " + i + " ����������� �� ������� ����");
                        if(!hdd.insertPage(ram.getMemory()[i])){
                            return false;
                        }
                        insertInRam(ram, i, id);
                        System.out.println("    ������� ���������� ������");
                        break;
                    }
                }
            }
        } else {
            if (pageTable.getPage(id).getOnHdd()) {
                System.out.println("    �������������� �������� ����������� �������� � id " + ram.getMemory()[ramIndex]);
                hdd.freeSpace(ram.getMemory()[ramIndex]);
                int minStatus = 5;
                for (int i = 0; i < ram.getMemory().length; i++) {
                    if (pageTable.getPage(ram.getMemory()[i]).getStatus() < minStatus) {
                        minStatus = pageTable.getPage(ram.getMemory()[i]).getStatus();
                    }
                }
                for (int i = 0; i < ram.getMemory().length; i++) {
                    if (pageTable.getPage(ram.getMemory()[i]).getStatus() == minStatus) {
                        pageTable.getPage(ram.getMemory()[i]).setOnHdd(true);
                        System.out.println("    ����������� �������� � id " + ram.getMemory()[i] + " ����������� � ���������� �������� � ������� " + i + " ����������� �� ������� ����");
                        if(!hdd.insertPage(ram.getMemory()[i])){
                            return false;
                        }
                        insertInRam(ram, i, id);
                        break;
                    }
                }
            }
        }
        System.out.println("��������� � ����������� �������� � id " + id + " ������ �������\n");
        return true;
    }

    private void insertInRam(Ram ram, int i, int id) {
        ram.insertPage(i, id);
        System.out.println("    ����������� �������� � id " + id + " ���������� � ���������� �������� � ������� " + i);
        pageTable.getPage(id).setRamId(i);
        pageTable.getPage(id).setRequest(true);
        pageTable.getPage(id).setOnHdd(false);
        pageTable.resetPageRequest(id);
        ram.checkIsFull();
    }

    private void insertInHdd(int id) {
        hdd.insertPage(id);
    }
}