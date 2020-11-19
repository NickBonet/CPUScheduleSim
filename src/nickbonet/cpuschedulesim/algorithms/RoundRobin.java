package nickbonet.cpuschedulesim.algorithms;

import nickbonet.cpuschedulesim.Process;
import nickbonet.cpuschedulesim.algorithms.base.SchedulingAlgorithm;

import java.util.Comparator;
import java.util.Scanner;

import static java.lang.System.out;

public class RoundRobin extends SchedulingAlgorithm {
    private int quantumCycle;
    private int currentQuantumIndex;

    public RoundRobin(String fileName) {
        super(fileName);
        workingProcessList.sort(Comparator.comparing(Process::getArrivalTime));
        out.println("Now simulating Round Robin scheduling.");
        Scanner timeQuantum = new Scanner(System.in);
        out.println("Enter time quantum value: ");
        quantumCycle = timeQuantum.nextInt();
    }

    @Override
    public void calculateTimes() {
        for (int i = 0; i < numberOfProcesses; i++) {
            Process process = processList.get(i);
            turnAroundTimes.put(process.getPid(), completedTimes.get(process.getPid()) - process.getArrivalTime());
            waitingTimes.put(process.getPid(), turnAroundTimes.get(process.getPid()) - process.getBurstTime());
        }
        out.println("Average waiting time: " + computeAverageOf(waitingTimes.values()));
        out.println("Average turn around time: " + computeAverageOf(turnAroundTimes.values()));
        out.println("Average response time: " + computeAverageOf(responseTimes.values()));
    }

    @Override
    public void algorithmCycle() {
        // If there's a current process, check if it has finished it's execution cycle.
        if (currentProcess != null && currentProcess.getCyclesRan() == currentProcess.getBurstTime()) {
            workingProcessList.remove(currentProcess);
            completedTimes.put(currentProcess.getPid(), time);
            currentProcess = null;
            currentQuantumIndex = 0;
        }

        // If the limit of the quantum cycle was met, check the ready queue and assign
        // the next available process for execution.
        if (currentQuantumIndex == quantumCycle) {
            if (!readyQueue.isEmpty() && currentProcess != null) {
                readyQueue.add(currentProcess);
                getNewProcess();
            }
            currentQuantumIndex -= quantumCycle;
        }

        // If there's no running task currently, get the first available process in the ready queue and run it.
        if (currentProcess == null && !readyQueue.isEmpty()) {
            getNewProcess();
        }

        // Run the process.
        // If there is no process currently, idle if the ready queue isn't empty.
        if (currentProcess != null) {
            currentProcess.run();
            currentProcess.incrementCyclesRan();
            currentQuantumIndex++;
        } else if (!readyQueue.isEmpty()) idle();
    }
}
