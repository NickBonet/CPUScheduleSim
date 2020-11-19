package nickbonet.cpuschedulesim.algorithms.base;

import nickbonet.cpuschedulesim.Process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.*;

public abstract class SchedulingAlgorithm {
    protected final List<Process> processList = new ArrayList<>();
    protected List<Process> readyQueue;
    protected List<Process> workingProcessList;
    protected final HashMap<Integer, Integer> waitingTimes = new HashMap<>();
    protected final HashMap<Integer, Integer> responseTimes = new HashMap<>();
    protected final HashMap<Integer, Integer> turnAroundTimes = new HashMap<>();
    protected final HashMap<Integer, Integer> completedTimes = new HashMap<>();
    protected Process currentProcess;
    protected int time = 0;
    protected int numberOfProcesses;

    public SchedulingAlgorithm(String fileName) {
        try {
            readProcessFile(fileName);
            this.workingProcessList = new ArrayList<>();
            this.workingProcessList.addAll(processList);
            this.readyQueue = new ArrayList<>();
            this.numberOfProcesses = processList.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void simulateAlgorithm() {
        while(!workingProcessList.isEmpty()) {
            // Check if any processes are ready to be moved to the ready queue.
            for (Process process : workingProcessList) if (process.getArrivalTime() == time) readyQueue.add(process);
            algorithmCycle();
            time++;
        }
        calculateTimes();
    }

    protected void readProcessFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] lineParse = line.split(" ");

                if (lineParse.length != 3) {
                    out.println("Incorrectly formatted process file, exiting.");
                    exit(1);
                }

                int[] processAttributes = new int[3];
                for (int i = 0; i < 3; i++) {
                    processAttributes[i] = Integer.parseInt(lineParse[i]);
                }

                Process newProcess = new Process(processAttributes[0], processAttributes[1], processAttributes[2]);
                processList.add(newProcess);

                String formattedStatus = String.format("New process added! PID: %d / Arrival Time: %d / Burst Time: %d", newProcess.getPid(),
                        newProcess.getArrivalTime(), newProcess.getBurstTime());
                out.println(formattedStatus);
            }
        }
    }

    protected void idle() {
        out.println("Idling.");
    }

    protected void getNewProcess() {
        currentProcess = readyQueue.get(0);
        responseTimes.put(currentProcess.getPid(), time - currentProcess.getArrivalTime());
        readyQueue.remove(currentProcess);
    }

    protected void completeProcess() {
        workingProcessList.remove(currentProcess);
        completedTimes.put(currentProcess.getPid(), time);
        currentProcess = null;
    }

    protected void runProcess() {
        currentProcess.run();
        currentProcess.incrementCyclesRan();
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
