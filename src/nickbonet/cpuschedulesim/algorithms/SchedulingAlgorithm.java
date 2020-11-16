package nickbonet.cpuschedulesim.algorithms;

import nickbonet.cpuschedulesim.Process;

import java.util.List;

public abstract class SchedulingAlgorithm {
    protected final List<Process> processList;
    protected final int[] waitingTimes;
    protected final int[] responseTimes;
    protected final int[] turnAroundTimes;
    protected final int[] completedTimes;
    protected Process currentProcess;

    public SchedulingAlgorithm(List<Process> processList) {
        this.processList = processList;
        this.waitingTimes = new int[processList.size()];
        this.responseTimes = new int[processList.size()];
        this.turnAroundTimes = new int[processList.size()];
        this.completedTimes = new int[processList.size()];
    }

    protected double computeAverageOf(int[] times) {
        int sum = 0;
        for (int waitingTime : times)
            sum += waitingTime;
        return ((double) sum / (double) times.length);
    }

    public abstract void runAlgorithm();
}
