import java.util.*;

public class AG implements Scheduler{
    HashMap<String, Process> processMap;
    SchedulerOutput schedulerOutput = new SchedulerOutput();
    ArrayList<Process> processList;
    int length = 0, contextSwitching = 0, currentTime = 0;

    AG(HashMap<String, Process> processMap){
        this.processMap = new HashMap<>(processMap);
        //pass the processes to the Scheduler output
        schedulerOutput.processes = new ArrayList<>(processMap.values());

        //init a list to be used to iterate the map
        processList = new ArrayList(processMap.values());
        //construct the length needed for the schedule
        for(int i = 0; i < processList.size(); i++)
            length += processList.get(i).burstTime;
    }

    AG(HashMap<String, Process> processMap, int contextSwitching){
        this(processMap);
        this.contextSwitching = contextSwitching;
    }

    @Override
    public SchedulerOutput schedule() {
        Queue<Process> processQueue = new LinkedList<>();
        Process prevProcess, currentProcess;
        int executionTime = 0;


        //zero out the Accumulated values from any previous schedule
        Scheduler.reset(processList);
        //init to dummy process to mute compiler warning
        prevProcess = new Process(null, -1, -1);
        currentProcess = new Process(null, -1, -1);

        int currentTime = 0, lastTime = -1;
        initQueue(processQueue, currentTime);
        while(currentTime < length){
            //current process is the one with the Highest Priority in the current arrival time
            //i.e. the first one in the sorted processes list
            currentProcess = processQueue.remove();

            executionTime = (int) Math.ceil(currentProcess.quantum/4.0);
            currentTime = Scheduler.execute(currentProcess, prevProcess, currentTime, executionTime, schedulerOutput);


            //insert Processes arrived during the process execution
            lastTime = currentTime;
            currentTime += executionTime;
            updateQueue(processQueue, currentTime, lastTime);
            prevProcess = currentProcess;
            //if it still has some work to do add it to the last
            if(currentProcess.remainingQuantum > 0) processQueue.add(currentProcess);
        }
        Scheduler.calculateAvg(schedulerOutput);

        return schedulerOutput;
    }

    public void initQueue(Queue<Process> processQueue, int currentTime){
        ArrayList<Process> list = new ArrayList<>();
        while (list.size() == 0) {
            //get the processes arrived so far
            list = ArriValComparator.filter(new ArrayList<>(processMap.values()), currentTime);
            //sort them according to their arrival time
            Collections.sort(list, new ArriValComparator());
            if(list.size() == 0) currentTime++;
        }
        processQueue.add(list.get(0));
    }

    public void updateQueue(Queue<Process> processQueue, int currentTime, int lastTime){
        ArrayList<Process> list = new ArrayList<>();
        //get the processes arrived so far
        list = ArriValComparator.filter(new ArrayList<>(processMap.values()), currentTime, lastTime);
        //sort them according to their arrival time
        Collections.sort(list, new ArriValComparator());
        for(int i = 0; i < list.size(); i++)
            processQueue.add(list.get(i));

    }

    @Override
    public int executeProcess(Process currentProcess, Process prevProcess, int currentTime) {
        return 0;
    }
}
