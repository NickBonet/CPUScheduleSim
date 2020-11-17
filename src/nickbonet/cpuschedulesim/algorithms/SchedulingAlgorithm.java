package nickbonet.cpuschedulesim.algorithms;

import nickbonet.cpuschedulesim.Process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.*;

public abstract class SchedulingAlgorithm {
    protected final List<Process> processList;
    protected final List<Process> readyQueue;
    protected final List<Process> workingProcessList;
    protected final HashMap<Integer, Integer> waitingTimes = new HashMap<>();
    protected final HashMap<Integer, Integer> responseTimes = new HashMap<>();
    protected final HashMap<Integer, Integer> turnAroundTimes = new HashMap<>();
    protected final HashMap<Integer, Integer> completedTimes = new HashMap<>();
    protected Process currentProcess;
    protected int time = 0;
    protected int numberOfProcesses;

    public SchedulingAlgorithm(List<Process> processList) {
        this.processList = processList;
        this.workingProcessList = new ArrayList<>();
        this.workingProcessList.addAll(processList);
        this.readyQueue = new ArrayList<>();
        this.numberOfProcesses = processList.size();
    }

    public void simulateAlgorithm() {
        while(!workingProcessList.isEmpty()) {
            algorithmCycle();
            time++;
        }
        calculateTimes();
    }

    protected void idle() {
        out.println("Idling.");
    }

    protected double computeAverageOf(Collection<Integer> times) {
        int sum = 0;
        for (int waitingTime : times)
            sum += waitingTime;
        return ((double) sum / (double) times.size());
    }

    public abstract void algorithmCycle();

    public abstract void calculateTimes();
}
