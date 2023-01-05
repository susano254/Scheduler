import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//Non-Pre-emptive priority
public class PriorityNonP implements Scheduler{
    SchedulerOutput schedulerOutput = new SchedulerOutput();
    HashMap<String, Process> processMap;
    int contextSwitching = 0;

    PriorityNonP(HashMap processMap){
        this.processMap = new HashMap<>(processMap);
        schedulerOutput.processes = new ArrayList<>(processMap.values());
    }
    PriorityNonP(HashMap processMap, int contextSwitching){
        this(processMap);
        this.contextSwitching = contextSwitching;
    }

    @Override
    public SchedulerOutput schedule() {
        ArrayList<Process> list;
        ProcessNode currentNode;
        int currentTime = 0;
        int length = processMap.size();
        Process currentProcess, prevProcess;

        //init to dummy process to mute compiler warning
        prevProcess = new Process(null, -1, -1);

        for(int i = 0; i < length; i++) {
            //get the processes arrived so far
            list = ArriValComparator.filter(new ArrayList<>(processMap.values()), currentTime);
            //sort them according to their required burst time (actually remaining burst time)
            Collections.sort(list, new PriorityComparator());
            //if nothing then just increment and try again
            if (list.size() == 0) { currentTime++; continue; }

            //current process is the one with the Highest Priority in the current arrival time
            //i.e. the first one in the sorted processes list
            currentProcess = list.get(0);

            if(contextSwitching != 0) {
                currentTime = Scheduler.executeWithCs(currentProcess, prevProcess, currentTime, currentProcess.burstTime, schedulerOutput, contextSwitching);
                length += contextSwitching;
            }
            else
                currentTime = Scheduler.execute(currentProcess, null, currentTime, currentProcess.burstTime, schedulerOutput);

            prevProcess = currentProcess;
            currentTime +=  currentProcess.burstTime;
            processMap.remove(currentProcess.name);
        }

        Scheduler.calculateAvg(schedulerOutput);

        return schedulerOutput;

    }

    @Override
    public int executeProcess(Process currentProcess, Process prevProcess, int currentTime) {
        return 0;
    }
}
