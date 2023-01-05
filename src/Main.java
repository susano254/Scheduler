import java.util.*;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Process> list = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        int n = 0, rrQuantum = 0, contextSwitching = 0;

        //System.out.print("Enter Number of processes: ");
        //n = scanner.nextInt();
        //System.out.print("Enter Round Robin Quantum: ");
        //rrQuantum = scanner.nextInt();
        //System.out.print("Enter ContextSwitching: ");
        //contextSwitching = scanner.nextInt();

        //for(int i = 0; i < n; i++){
        //    String name = scanner.nextLine().trim();
        //    int burstTime = scanner.nextInt();
        //    int arrivalTime = scanner.nextInt();
        //    int priorityValue = scanner.nextInt();
        //    list.put(name, new Process(name, burstTime, arrivalTime, priorityValue));
        //}


        list.put("P1", new Process("P1", 1, 0, 2));
        list.put("P2", new Process("P2", 7, 1, 6));
        list.put("P3", new Process("P3", 3, 2, 3));
        list.put("P4", new Process("P4", 6, 3, 5));
        list.put("P5", new Process("P5", 5, 4, 4));
        list.put("P6", new Process("P6", 15, 5, 10));
        list.put("P7", new Process("P7", 8, 15, 9));

        contextSwitching = 1;
        Scheduler fcfs = new FCFS(list, contextSwitching);
        Scheduler priorityNonP = new PriorityNonP(list, contextSwitching);
        Scheduler sjf = new SJF(list, contextSwitching);
        Scheduler priority = new Priority(list, contextSwitching);
        Scheduler RR = new RR(list, 2, contextSwitching);


        //System.out.println(fcfs.schedule());
        //System.out.println(priority.schedule());
        //System.out.println(priorityNonP.schedule());
        System.out.println(sjf.schedule());
        //System.out.println(RR.schedule());
    }
}

//list.put("P1", new Process("P1", 4, 0, 2));
//list.put("P2", new Process("P2", 8, 1, 6));
//list.put("P3", new Process("P3", 2, 3, 3));
//list.put("P4", new Process("P4", 6, 10, 5));
//list.put("P5", new Process("P5", 5, 12, 4));
//list.put("P1", new Process("P1", 17, 0, 4, 7));
//list.put("P2", new Process("P2", 6, 2, 7, 9));
//list.put("P3", new Process("P3", 11, 5, 3, 4));
//list.put("P4", new Process("P4", 4, 15, 6, 6));
