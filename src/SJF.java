import java.util.*;

public class SJF implements Scheduler{
    HashMap<String, Process> processMap;
    SchedulerOutput schedulerOutput = new SchedulerOutput();
    ArrayList<Process> processList;
    int length = 0, contextSwitching = 0, currentTime = 0;

    SJF(HashMap<String, Process> processMap){
        this.processMap = new HashMap<>(processMap);
        //pass the processes to the Scheduler output
        schedulerOutput.processes = new ArrayList<>(processMap.values());

        //init a list to be used to iterate the map
        processList = new ArrayList(processMap.values());
        //construct the length needed for the schedule
        for(int i = 0; i < processList.size(); i++)
            length += processList.get(i).burstTime;
    }
    SJF(HashMap<String, Process> processMap, int contextSwitching){
        this(processMap);
        this.contextSwitching = contextSwitching;
    }


    @Override
    public SchedulerOutput schedule() {
        ArrayList<Process> list;
        Process prevProcess, currentProcess;

        //init to dummy process to mute compiler warning
        prevProcess = new Process(null, -1, -1);

        //zero out the Accumulated values from any previous schedule
        Scheduler.reset(processList);

        for(currentTime = 0; currentTime < length; currentTime++){
            //get the processes arrived so far
            list = ArriValComparator.filter(new ArrayList<>(processMap.values()), currentTime);
            //sort them according to their required burst time (actually remaining burst time)
            Collections.sort(list, new BurstTimeComparator());
            //if nothing then just increment and try again
            if(list.size() == 0) { currentTime++; continue; }

            //current process is the one with the least burst time in the current arrival time
            //i.e. the first one in the sorted processes list
            currentProcess =  list.get(0);

            if(contextSwitching != 0) {
                currentTime = Scheduler.executeWithCs(currentProcess, prevProcess, currentTime, 1, schedulerOutput, contextSwitching);
                length += contextSwitching;
            }
            else
                currentTime = Scheduler.execute(currentProcess, prevProcess, currentTime, 1, schedulerOutput);



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
