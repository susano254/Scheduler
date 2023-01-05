import java.util.Comparator;

public class PriorityComparator implements Comparator<Process> {
    @Override
    public int compare(Process process, Process t1) {
        if(process.priority == t1.priority) return 0;
        else if(process.priority > t1.priority) return 1;
        else return -1;
    }
}
