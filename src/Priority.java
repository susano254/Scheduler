import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Priority implements Scheduler{
    SchedulerOutput schedulerOutput = new SchedulerOutput();
    HashMap<String, Process> processMap;
    ArrayList<Process> processList;
    int length = 0;
    int contextSwitching = 0;

    Priority(HashMap<String, Process> processMap){
        this.processMap = new HashMap<>(processMap);
        schedulerOutput.processes = new ArrayList<>(processMap.values());

        //init a list to be used to iterate the map
        processList = new ArrayList(processMap.values());

        //construct the length needed for the schedule
        for(int i = 0; i < processList.size(); i++)
            length += processList.get(i).burstTime;
    }
    Priority(HashMap<String, Process> processMap, int contextSwitching) {
        this(processMap);
        this.contextSwitching = contextSwitching;
    }

    @Override
    public SchedulerOutput schedule() {
        ArrayList<Process> list;
        Process prevProcess, currentProcess;

        //zero out the Accumulated values from any previous schedule
        Scheduler.reset(processList);
        //init to dummy process to mute compiler warning
        prevProcess = new Process(null, -1, -1);

        for(int currentTime = 0; currentTime < length; currentTime++){
            //get the processes arrived so far
            list = ArriValComparator.filter(new ArrayList<>(processMap.values()), currentTime);
            //sort them according to their required burst time (actually remaining burst time)
            Collections.sort(list, new PriorityComparator());
            //if nothing then just increment and try again
            if(list.size() == 0) { currentTime++; continue; }

            //current process is the one with the Highest Priority in the current arrival time
            //i.e. the first one in the sorted processes list
            currentProcess =  list.get(0);

            if(contextSwitching != 0) {
                currentTime = Scheduler.executeWithCs(currentProcess, prevProcess, currentTime, 1, schedulerOutput, contextSwitching);
                length += contextSwitching;
            }
            else
                currentTime = Scheduler.execute(currentProcess, prevProcess, currentTime, 1, schedulerOutput);

            //currentProcess.remainingBurstTime--;
            prevProcess = currentProcess;
            if(currentProcess.remainingBurstTime <= 0) processMap.remove(currentProcess.name);
        }
        Scheduler.calculateAvg(schedulerOutput);

        return schedulerOutput;
    }

    @Override
    public int executeProcess(Process currentProcess, Process prevProcess, int currentTime) {
        return 0;
    }
}
