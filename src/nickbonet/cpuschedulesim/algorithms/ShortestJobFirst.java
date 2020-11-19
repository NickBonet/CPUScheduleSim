package nickbonet.cpuschedulesim.algorithms;

import nickbonet.cpuschedulesim.Process;

import java.util.Comparator;

import static java.lang.System.*;

public class ShortestJobFirst extends SchedulingAlgorithm {

    public ShortestJobFirst(String fileName) {
        super(fileName);
        workingProcessList.sort(Comparator.comparing(Process::getArrivalTime));
        out.println("Now simulating non-preemptive Shortest Job First (SJF) scheduling.");
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
        // Check if any processes are ready to be moved to the ready queue.
        for (Process process : workingProcessList) if (process.getArrivalTime() == time) readyQueue.add(process);

        // Sort ready queue by shortest burst time.
        if (!readyQueue.isEmpty()) readyQueue.sort(Comparator.comparing(Process::getBurstTime));

        // If there's a current process, check if it has finished it's execution cycle.
        if (currentProcess != null && currentProcess.getCyclesRan() == currentProcess.getBurstTime()) {
            workingProcessList.remove(currentProcess);
            completedTimes.put(currentProcess.getPid(), time);
            currentProcess = null;
        }

        // If there's no running task currently, get the first available process in the ready queue and run it.
        if (currentProcess == null && !readyQueue.isEmpty()) {
            currentProcess = readyQueue.get(0);
            responseTimes.put(currentProcess.getPid(), time - currentProcess.getArrivalTime());
            readyQueue.remove(currentProcess);
        }

        // Run the process.
        // If there is no process currently, idle if the ready queue isn't empty.
        if (currentProcess != null) {
            currentProcess.run();
            currentProcess.incrementCyclesRan();
        } else if (!readyQueue.isEmpty()) idle();
    }
}
