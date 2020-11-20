package nickbonet.cpuschedulesim.algorithms;

import nickbonet.cpuschedulesim.Process;
import nickbonet.cpuschedulesim.algorithms.base.SchedulingAlgorithm;

import java.util.Comparator;
import java.util.Scanner;

import static java.lang.System.out;

public class RoundRobin extends SchedulingAlgorithm {
    private final int quantumCycle;
    private int currentQuantumIndex;

    public RoundRobin(String fileName) {
        super(fileName);
        workingProcessList.sort(Comparator.comparing(Process::getArrivalTime));
        Scanner timeQuantum = new Scanner(System.in);
        out.println("Enter time quantum value: ");
        quantumCycle = timeQuantum.nextInt();
    }

    @Override
    protected void algorithmCycle() {
        // If there's a current process, check if it has finished its execution cycle.
        if (currentProcess != null && currentProcess.getCyclesRan() == currentProcess.getBurstTime()) {
            completeProcess();
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
        if (currentProcess == null && !readyQueue.isEmpty()) getNewProcess();

        // Run the process.
        // If there is no process currently, idle if the ready queue isn't empty.
        if (currentProcess != null) {
            runProcess();
            currentQuantumIndex++;
        } else if (!readyQueue.isEmpty()) idle();
    }
}
