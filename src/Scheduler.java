import java.util.ArrayList;

public interface Scheduler {
    SchedulerOutput schedule();
     int executeProcess(Process currentProcess, Process prevProcess, int currentTime);


    static void reset(ArrayList<Process> processList){
        Process currentProcess;
        for(int i = 0; i < processList.size(); i++) {
            currentProcess = processList.get(i);
            currentProcess.waitingTime = 0;
            currentProcess.lastTimeRun = currentProcess.arrivalTime;
            currentProcess.remainingBurstTime = currentProcess.burstTime;
        }
    }

    static void calculateAvg(SchedulerOutput schedulerOutput){
        ArrayList<Process> processes = new ArrayList<>(schedulerOutput.processes);
        schedulerOutput.averageWaitingTime = 0;
        schedulerOutput.averageTurnAroundTime = 0;

        for(int i = 0; i < processes.size(); i++){
            schedulerOutput.averageWaitingTime += processes.get(i).waitingTime;
            schedulerOutput.averageTurnAroundTime += processes.get(i).turnAroundTime;
        }
        schedulerOutput.averageWaitingTime /= processes.size();
        schedulerOutput.averageTurnAroundTime /= processes.size();
    }

    static int execute(Process currentProcess, Process prevProcess, int currentTime, int executionTime, SchedulerOutput schedulerOutput){
        ProcessNode currentNode;

        //wait time is the current time - last time the process had chance to execute
        currentProcess.waitingTime += currentTime - currentProcess.lastTimeRun;
        //update the turnAroundTime by the new Waiting time
        currentProcess.turnAroundTime = currentProcess.waitingTime + currentProcess.burstTime;
        //update last time run to be used in next iteration to calculate wait time
        currentProcess.lastTimeRun = currentTime+executionTime;


        //if it's just the same process as before
        if(currentProcess == prevProcess) {
            currentNode = schedulerOutput.processQueue.getLast();
            currentNode.endTime += executionTime;
        }
        else {
            currentNode = new ProcessNode(currentProcess.name, currentTime, currentTime + executionTime);
            schedulerOutput.processQueue.add(currentNode);
        }

        currentProcess.remainingBurstTime -= executionTime;

        return  currentTime;
    }

    static int executeWithCs(Process currentProcess, Process prevProcess, int currentTime, int executionTime, SchedulerOutput schedulerOutput, int contextSwitching){
        ProcessNode currentNode;


        //if it's just the same process as before
        if(currentProcess == prevProcess) {
            currentNode = schedulerOutput.processQueue.getLast();
            currentNode.endTime += executionTime;
        }
        //add context switching then
        else {
            //if the process queue is not empty and prevProcess is not a dummy one update its values
            if (schedulerOutput.processQueue.size() > 0 && prevProcess.burstTime != -1) {
                currentNode = schedulerOutput.processQueue.getLast();
                currentNode.endTime += contextSwitching;
                prevProcess.waitingTime += contextSwitching;
                prevProcess.turnAroundTime += contextSwitching;
                prevProcess.lastTimeRun += contextSwitching;
                currentTime += contextSwitching;
            }

            //wait time is the current time - last time the process had chance to execute
            currentProcess.waitingTime += currentTime - currentProcess.lastTimeRun;
            //update the turnAroundTime by the new Waiting time
            currentProcess.turnAroundTime = currentProcess.waitingTime + currentProcess.burstTime;
            //update last time run to be used in next iteration to calculate wait time
            currentProcess.lastTimeRun = currentTime+executionTime;


            currentNode = new ProcessNode(currentProcess.name, currentTime, currentTime + executionTime);
            schedulerOutput.processQueue.add(currentNode);
        }

        currentProcess.remainingBurstTime -= executionTime;

        return  currentTime;
    }
}
