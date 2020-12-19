import java.util.LinkedList;
import java.util.Queue;

public class Core {

    private final Queue<Process> processesWithLock;
    private final Queue<Process> processesWithoutLock;
    private int timeWithLock;
    private int timeWithoutLock;
    private final int timeWorkDevice = 2000;

    public Core() {
        processesWithLock = new LinkedList<>();
        processesWithoutLock = new LinkedList<>();
    }

    public void createProcess(int pID, int processTime, boolean interactionWithDevice) {
        processesWithLock.add(new Process(pID, processTime, interactionWithDevice));
        processesWithoutLock.add(new Process(pID, processTime, interactionWithDevice));
    }

    public void planningProcessWithoutLock() {
        Process buffer = processesWithoutLock.poll();
        while (buffer != null) {
            System.out.println("������� " + buffer.getpID() + " ����� ������");
            if (buffer.isInteractionWithDevice()) {
                System.out.println("������� " + buffer.getpID() + " �������� � ����������� �����/������");
                System.out.println("����������� �������������");
                
                System.out.println("����������� ���������� ������");
            }
            System.out.println("������� " + buffer.getpID() + " �������� ������");
            timeWithoutLock += buffer.getProcessTime();
            buffer = processesWithoutLock.poll();
        }
    }

    public void planningProcessWithLock() {
        Process buffer = processesWithLock.poll();
        while (buffer != null) {
            if (buffer.isLock()) {
                timeWithLock += 100;
                if (timeWithLock - buffer.getTimeWithLock() > timeWorkDevice) {
                    System.out.println("������� " + buffer.getpID() + " �������� ������ � ����������� �����/������, ������� �������������");
                    System.out.println("������� " + buffer.getpID() + " �������� ������");
                    timeWithLock += buffer.getProcessTime();
                } else {
                    processesWithLock.add(buffer);
                }
                buffer = processesWithLock.poll();
                continue;
            }
            System.out.println("������� " + buffer.getpID() + " ����� ������");
            if (buffer.isInteractionWithDevice()) {
                System.out.println("������� " + buffer.getpID() + " �������� � ����������� �����/������");
                System.out.println("������������ ��������");
                buffer.setLock(true);
                buffer.setTimeWithLock(timeWithLock);
                processesWithLock.add(buffer);
            } else {
                timeWithLock += buffer.getProcessTime();
                for (Process process : processesWithLock) {
                    if (process.isLock()) {
                        if (timeWithLock - process.getTimeWithLock() > timeWorkDevice) {
                            System.out.println("������� " + process.getpID() + " �������� ������ � ����������� �����/������, ������� �������������");
                            System.out.println("������� " + process.getpID() + " �������� ������");
                            timeWithLock += process.getProcessTime();
                            processesWithLock.remove(process);
                            break;
                        }
                    }
                }
                System.out.println("������� " + buffer.getpID() + " �������� ������");
            }
            buffer = processesWithLock.poll();
        }
    }
}