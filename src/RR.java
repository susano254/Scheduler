import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RR implements Scheduler{
    SchedulerOutput schedulerOutput = new SchedulerOutput();
    HashMap<String, Process> processMap;
    ArrayList<Process> processList;
    int quantum, length = 0;
    int contextSwitching = 0;

    RR(HashMap<String, Process> processMap, int quantum){
        this.processMap = new HashMap<>(processMap);
        this.quantum = quantum;
        schedulerOutput.processes = new ArrayList<>(processMap.values());

        //init a list to be used to iterate the map
        processList = new ArrayList(processMap.values());

        //construct the length needed for the schedule
        for(int i = 0; i < processList.size(); i++)
            length += processList.get(i).burstTime;
    }
    RR(HashMap<String, Process> processMap, int quantum, int contextSwitching){
        this(processMap, quantum);
        this.contextSwitching = contextSwitching;
    }

    @Override
    public SchedulerOutput schedule() {
        Process prevProcess, currentProcess;
        int executionTime = 0;

        //zero out the Accumulated values from any previous schedule
        Scheduler.reset(processList);
        //init to dummy process to mute compiler warning
        prevProcess = new Process(null, -1, -1);
        currentProcess = new Process(null, -1, -1);

        int currentTime = 0, i = 0;
        while(currentTime < length){

            if(i < processList.size()) currentProcess = processList.get(i);
            if(i >= processList.size() || currentProcess.arrivalTime > currentTime) { i = 0; continue; }

            executionTime = quantum < currentProcess.remainingBurstTime ? quantum : currentProcess.remainingBurstTime;

            if(contextSwitching != 0) {
                currentTime = Scheduler.executeWithCs(currentProcess, prevProcess, currentTime, executionTime, schedulerOutput, contextSwitching);
                length += contextSwitching;
            }
            else
                currentTime = Scheduler.execute(currentProcess, prevProcess, currentTime, executionTime, schedulerOutput);

            if(currentProcess.remainingBurstTime <= 0) {
                processList.remove(currentProcess);
                i--;
            }

            prevProcess = currentProcess;
            currentTime += executionTime;
            i++;
        }
        Scheduler.calculateAvg(schedulerOutput);

        return schedulerOutput;
    }

    @Override
    public int executeProcess(Process currentProcess, Process prevProcess, int currentTime) {
        return 0;
    }
}
