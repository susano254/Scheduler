import java.util.*;

//just a model class to hold all scheduler output data
public class SchedulerOutput {
    public LinkedList<ProcessNode> processQueue = new LinkedList<>();
    public ArrayList<Process> processes;
    public double averageWaitingTime = 0, averageTurnAroundTime = 0;


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        //Gannt Chart
        output.append(new ArrayList<>(processQueue) + "\n");

        //output table
        output.append("Name" +"\t"+ "BurstTime" +"\t"+ "ArrivalTime" +"\t"+ "Priority" +"\t"+ "Quantum" +"\t"+ "waitingTime" +"\t"+ "TurnAroundTime"+"\n");
        for(int i = 0; i < processes.size(); i++)
            output.append(processes.get(i));
        output.append('\n');

        output.append("Average Waiting Time: " + averageWaitingTime + "\t\t\t" + "Average Turn Around Time: " + averageTurnAroundTime);

        return output.toString();
    }

}
