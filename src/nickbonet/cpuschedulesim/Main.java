package nickbonet.cpuschedulesim;

import nickbonet.cpuschedulesim.algorithms.FirstComeFirstServe;
import nickbonet.cpuschedulesim.algorithms.ShortestJobFirst;

import static java.lang.System.*;

public class Main {
    private static String fileName;

    public static void main(String[] args) {
        fileName = args[0];
        out.println("CPU Scheduling Simulator");
        FirstComeFirstServe firstComeFirstServe = new FirstComeFirstServe(fileName);
        firstComeFirstServe.simulateAlgorithm();
        ShortestJobFirst sjf = new ShortestJobFirst(fileName);
        sjf.simulateAlgorithm();
    }
}
