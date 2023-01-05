public class Process {
    public static int id = 0;
    public String name;
    public int pid, burstTime, arrivalTime, priority, quantum;
    public int waitingTime, turnAroundTime;
    public int remainingBurstTime, lastTimeRun, remainingQuantum;

    Process(String name, int burstTime, int arrivalTime){
        this.pid = id++;
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.remainingBurstTime = burstTime;
        this.remainingQuantum = quantum;
        this.lastTimeRun = arrivalTime;
        this.priority = -1;
        this.quantum = -1;
    }
    Process(String name, int burstTime, int arrivalTime, int priority){
        this(name, burstTime, arrivalTime);
        this.priority = priority;
    }
    Process(String name, int burstTime, int arrivalTime, int priority, int quantum){
        this(name, burstTime, arrivalTime, priority);
        this.quantum = quantum;
    }

    @Override
    public String toString() {
        return name +"\t\t\t"+ burstTime +"\t\t\t"+ arrivalTime +"\t\t\t"+ priority +"\t\t\t"+ quantum +"\t\t\t"+ waitingTime +"\t\t\t"+ turnAroundTime +"\n";
    }
}
