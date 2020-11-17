package nickbonet.cpuschedulesim.algorithms;

import nickbonet.cpuschedulesim.Process;

import java.util.Comparator;
import java.util.List;

import static java.lang.System.*;

public class FirstComeFirstServe extends SchedulingAlgorithm {

    public FirstComeFirstServe(List<Process> processList) {
        super(processList);
        processList.sort(Comparator.comparing(Process::getArrivalTime));
        out.println("Now simulating First Come First Serve (FCFS) scheduling.");
    }

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
        for (int i = 0; i < workingProcessList.size(); i++)
            // Check if any processes are ready to be moved to the ready queue.
            if (workingProcessList.get(i).getArrivalTime() == time) readyQueue.add(workingProcessList.get(i));

        // Run the process.
        if (currentProcess != null) {
            currentProcess.run();
            currentProcess.incrementCyclesRan();

            if (currentProcess.getCyclesRan() == currentProcess.getBurstTime()) {
                workingProcessList.remove(currentProcess);
                completedTimes.put(currentProcess.getPid(), time);
                currentProcess = null;
            }
        } else idle();

        // If there's no running task currently, get the first available process in the ready queue and run it.
        if (currentProcess == null && !readyQueue.isEmpty()) {
            currentProcess = readyQueue.get(0);
            responseTimes.put(currentProcess.getPid(), time - currentProcess.getArrivalTime());
            readyQueue.remove(currentProcess);
        }
    }
}
