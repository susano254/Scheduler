public class ProcessNode {
    public String processName;
    public int startTime, endTime;


    ProcessNode(String processName, int startTime, int endTime){
        this.processName = processName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() { return  startTime + " --> " + processName + " --> " + endTime; }
}
