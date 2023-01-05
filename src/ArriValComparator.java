import java.util.ArrayList;
import java.util.Comparator;

public class ArriValComparator implements Comparator<Process> {
    @Override
    public int compare(Process process, Process t1) {
        if(process.arrivalTime == t1.arrivalTime) return 0;
        else if(process.arrivalTime > t1.arrivalTime) return 1;
        else return -1;
    }

    public static ArrayList filter(ArrayList<Process> processes, int arrivalTime){
        ArrayList<Process> list = new ArrayList<>();

        for(int i = 0; i < processes.size(); i++){
            Process currentProcess = processes.get(i);
            if(currentProcess.arrivalTime <= arrivalTime) list.add(currentProcess);
        }
        return  list;
    }
    public static ArrayList filter(ArrayList<Process> processes, int arrivalTime, int lastTime){
        ArrayList<Process> list = new ArrayList<>();

        for(int i = 0; i < processes.size(); i++){
            Process currentProcess = processes.get(i);
            if(currentProcess.arrivalTime <= arrivalTime && currentProcess.arrivalTime > lastTime) list.add(currentProcess);
        }
        return  list;
    }
}
