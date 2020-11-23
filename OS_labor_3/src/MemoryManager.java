import java.util.LinkedList;

public class MemoryManager {

    private PageTable pageTable;

    private Ram ram;

    private Memory memory;

    public MemoryManager(LinkedList<Page> pageList, Ram ram) {
        pageTable = new PageTable(pageList);
        this.ram = ram;
        memory = new Memory(ram.getSize() * 2);
    }

    public boolean work(int id) {
        int ramIndex = pageTable.getPage(id).getRamId();
        if (ramIndex == -1) {
            System.out.println("    Сгенерировано страничное прерывание, процесс прерывает работу...");
            System.out.println("    Диспетчер памяти осуществляет поиск свободного места...");
            if (!ram.getFull()) {
                for (int i = 0; i < ram.getSize(); i++) {
                    if (ram.getId(i) == -1) {
                        insertInRam(ram, i, id);
                        System.out.println("    Процесс продолжает работу");
                        break;
                    }
                }
            } else {
                System.out.println("    В оперативной памяти закончилось свободное место, начинает работать алгоритм замещения страниц");
                int minStatus = 5;
                for (int i = 0; i < ram.getSize(); i++) {
                    if (pageTable.getPage(ram.getId(i)).getStatus() < minStatus) {
                        minStatus = pageTable.getPage(ram.getId(i)).getStatus();
                    }
                }
                for (int i = 0; i < ram.getSize(); i++) {
                    if (pageTable.getPage(ram.getId(i)).getStatus() == minStatus) {
                        pageTable.getPage(ram.getId(i)).setOnHdd(true);
                        System.out.println("    Виртуальная страница с id " + ram.getId(i) + " размещенная в физической странице с адресом " + i + " выгружается на жесткий диск");
                        if(!memory.insertPage(ram.getId(i))){
                            return false;
                        }
                        insertInRam(ram, i, id);
                        System.out.println("    Процесс продолжает работу");
                        break;
                    }
                }
            }
        } else {
            if (pageTable.getPage(id).getOnHdd()) {
                System.out.println("    Осуществляется подкачка виртуальной страницы с id " + ram.getId(ramIndex));
                memory.freeSpace(ram.getId(ramIndex));
                int minStatus = 5;
                for (int i = 0; i < ram.getSize(); i++) {
                    if (pageTable.getPage(ram.getId(i)).getStatus() < minStatus) {
                        minStatus = pageTable.getPage(ram.getId(i)).getStatus();
                    }
                }
                for (int i = 0; i < ram.getSize(); i++) {
                    if (pageTable.getPage(ram.getId(i)).getStatus() == minStatus) {
                        pageTable.getPage(ram.getId(i)).setOnHdd(true);
                        System.out.println("    Виртуальная страница с id " + ram.getId(i) + " размещенная в физической странице с адресом " + i + " выгружается на жесткий диск");
                        if(!memory.insertPage(ram.getId(i))){
                            return false;
                        }
                        insertInRam(ram, i, id);
                        break;
                    }
                }
            }
        }
        System.out.println("Обращение к виртуальной странице с id " + id + " прошло успешно\n");
        return true;
    }

    private void insertInRam(Ram ram, int i, int id) {
        ram.insertPage(i, id);
        System.out.println("    Виртуальная страница с id " + id + " отображена в физическую страницу с адресом " + i);
        pageTable.getPage(id).setRamId(i);
        pageTable.getPage(id).setRequest(true);
        pageTable.getPage(id).setOnHdd(false);
        pageTable.resetPageRequest(id);
        ram.checkIsFull();
    }

    private void insertInHdd(int id) {
    	memory.insertPage(id);
    }
}
