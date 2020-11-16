package nickbonet.cpuschedulesim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.*;

public class Main {
    private static final ArrayList<Process> processList = new ArrayList<>();

    public static void main(String[] args) {
        out.println("CPU Scheduling Simulator");
        try {
            readProcessFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readProcessFile(String filename) throws IOException {
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
}
