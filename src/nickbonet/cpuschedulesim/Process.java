package nickbonet.cpuschedulesim;

import static java.lang.System.*;

public class Process {
    private final int pid;
    private final int arrivalTime;
    private final int burstTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public void run() {
        out.println("Current process running: " + this.pid);
    }

    public int getPid() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }
}
