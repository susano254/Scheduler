import java.util.Comparator;

public class BurstTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process process, Process t1) {
        if(process.remainingBurstTime == t1.remainingBurstTime) return 0;
        else if(process.remainingBurstTime > t1.remainingBurstTime) return 1;
        else return -1;
    }
}
