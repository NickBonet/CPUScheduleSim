package nickbonet.cpuschedulesim.algorithms;

import nickbonet.cpuschedulesim.Process;
import nickbonet.cpuschedulesim.algorithms.base.SchedulingAlgorithm;

import java.util.Comparator;

import static java.lang.System.*;

public class FirstComeFirstServe extends SchedulingAlgorithm {

    public FirstComeFirstServe(String fileName) {
        super(fileName);
        workingProcessList.sort(Comparator.comparing(Process::getArrivalTime));
        out.println("Now simulating First Come First Serve (FCFS) scheduling.");
    }

    @Override
    public void algorithmCycle() {
        // If there's a current process, check if it has finished it's execution cycle.
        if (currentProcess != null && currentProcess.getCyclesRan() == currentProcess.getBurstTime()) completeProcess();

        // If there's no running task currently, get the first available process in the ready queue and run it.
        if (currentProcess == null && !readyQueue.isEmpty()) getNewProcess();

        // Run the process.
        // If there is no process currently, idle if the ready queue isn't empty.
        if (currentProcess != null) runProcess();
        else if (!readyQueue.isEmpty()) idle();
    }
}
