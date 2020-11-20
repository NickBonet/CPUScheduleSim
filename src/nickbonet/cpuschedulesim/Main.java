package nickbonet.cpuschedulesim;

import nickbonet.cpuschedulesim.algorithms.FirstComeFirstServe;
import nickbonet.cpuschedulesim.algorithms.NonPreemptiveShortestJobFirst;
import nickbonet.cpuschedulesim.algorithms.RoundRobin;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        String fileName = "processes.txt";
        out.println("CPU Scheduling Simulator");
        FirstComeFirstServe firstComeFirstServe = new FirstComeFirstServe(fileName);
        firstComeFirstServe.simulateAlgorithm();
        NonPreemptiveShortestJobFirst sjf = new NonPreemptiveShortestJobFirst(fileName);
        sjf.simulateAlgorithm();
        RoundRobin rr = new RoundRobin(fileName);
        rr.simulateAlgorithm();
    }
}
