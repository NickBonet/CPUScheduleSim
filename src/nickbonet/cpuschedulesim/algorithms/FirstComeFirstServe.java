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
        for (int i = 0; i < processList.size(); i++) {
            Process process = processList.get(i);
            turnAroundTimes[i] = completedTimes[i] - process.getArrivalTime();
            waitingTimes[i] = turnAroundTimes[i] - process.getBurstTime();
        }
    }

    @Override
    public void runAlgorithm() {
        for (int i = 0; i < processList.size(); i++) {
            currentProcess = processList.get(i);

            int runTime = currentProcess.getBurstTime();
            if (i > 0) completedTimes[i] = runTime + completedTimes[i-1];
            else completedTimes[0] = runTime;

            while (runTime != 0) {
                currentProcess.run();
                runTime--;
            }
        }
        calculateTimes();
        out.println("Average waiting time: " + computeAverageOf(waitingTimes));
        out.println("Average turn around time: " + computeAverageOf(turnAroundTimes));
    }
}
