package nickbonet.cpuschedulesim;

import static java.lang.System.out;

public class Process {
    private final int pid;
    private final int arrivalTime;
    private final int burstTime;
    private int cyclesRan = 0;
    private int timesExecuted = 0;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public void run() {
        out.println("Running process " + this.pid + ".");
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

    public void incrementCyclesRan() {
        cyclesRan++;
    }

    public int getCyclesRan() {
        return cyclesRan;
    }

    public void incrementTimesExecuted() {
        timesExecuted++;
    }

    public int getTimesExecuted() {
        return timesExecuted;
    }
}
