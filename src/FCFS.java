import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FCFS implements Scheduler{
    SchedulerOutput schedulerOutput = new SchedulerOutput();
    HashMap<String, Process> processMap;
    int contextSwitching = 0;

    FCFS(HashMap processMap){
        this.processMap = new HashMap<>(processMap);
        schedulerOutput.processes = new ArrayList<>(processMap.values());
    }
    FCFS(HashMap processMap, int contextSwitching){
        this(processMap);
        this.contextSwitching = contextSwitching;
    }

    @Override
    public SchedulerOutput schedule() {
        int currentTime = 0;
        ArrayList<Process> processList = new ArrayList<>(processMap.values());
        Collections.sort(processList, new ArriValComparator());
        Process currentProcess, prevProcess;

        //init to dummy process to mute compiler warning
        prevProcess = new Process(null, -1, -1);
        int length = processList.size();

        for(int i = 0; i < length; i++){
            currentProcess =  processList.get(i);

            if(currentProcess.arrivalTime > currentTime) currentTime = currentProcess.arrivalTime;

            if(contextSwitching != 0) {
                currentTime = Scheduler.executeWithCs(currentProcess, prevProcess, currentTime, currentProcess.burstTime, schedulerOutput, contextSwitching);
                length += contextSwitching;
            }
            else
                currentTime = Scheduler.execute(currentProcess, null, currentTime, currentProcess.burstTime, schedulerOutput);

            prevProcess = currentProcess;
            currentTime +=  currentProcess.burstTime;
        }

        Scheduler.calculateAvg(schedulerOutput);

        return schedulerOutput;
    }

    @Override
    public int executeProcess(Process currentProcess, Process prevProcess, int currentTime) {
        if(contextSwitching != 0)
            currentTime = Scheduler.executeWithCs(currentProcess, prevProcess, currentTime, currentProcess.burstTime, schedulerOutput, contextSwitching);
        else
            currentTime = Scheduler.execute(currentProcess, null, currentTime, currentProcess.burstTime, schedulerOutput);

        return  currentTime;
    }

}
