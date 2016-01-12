/*
 * Kimberly Stewart
 * TCSS 372
 * Winter 2015
 * Project 4- Cache Simulator
 * The main class to the simulator program.
 * Also represents the CPU.
 */
package cachesimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simulates the cache hierarchy of a dual core processor. Main class. Also
 * simulates a dual core with 2 CPUs. Takes settings from a configuration file.
 * Scans a .cvs file of addresses and processed them. Tracks and displays
 * statistics.
 *
 * @author Kim Version 3- 3/17/2015
 */
public class Simulator {

    public CPU myCpu1;
    public CPU myCpu2;
    public L3Cache mySharedL3;
    public int[][] myMESIMatrix;

    /**
     * Main class that runs the simulator.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Get the configurations for the cache design setup.
        int[] configs = getConfigurations();
        //Test the configurations are correct.
        /*
         for (int i : configs) {
         System.out.println(i);
         }
         */
        //Open 2 scanners for 2 CPUs.
        Scanner input1;
        Scanner input2;

        try {
            Simulator duo = new Simulator(configs);
            ArrayList<Integer> ttc = new ArrayList<Integer>();
            ArrayList<Integer> insttc = new ArrayList<Integer>();
            input1 = new Scanner(new File("C:\\Users\\Kim\\Documents\\NetBeansProjects\\CacheSimulator\\src\\cachesimulator\\trace-2k.csv"));
            input2 = new Scanner(new File("C:\\Users\\Kim\\Documents\\NetBeansProjects\\CacheSimulator\\src\\cachesimulator\\trace-2k.csv"));
            scanSetOfAddresses(input1, duo.myCpu1, 40, ttc, insttc);
            
            while (input1.hasNextLine() && input2.hasNextLine()) {
                scanSetOfAddresses(input2, duo.myCpu2, 5, ttc, insttc);
                scanSetOfAddresses(input1, duo.myCpu1, 5, ttc, insttc);
            }
            while (input2.hasNextLine()) {
                scanSetOfAddresses(input2, duo.myCpu2, 10, ttc, insttc);
            }

            //System.out.println(duo);
            System.out.println("Associativity: " + configs[12]);
            displayResults(duo, ttc, insttc);
            //System.out.print(ttc);
        } catch (FileNotFoundException e) {
            System.out.println("File not read.");
        }

    }

    /**
     * Scans a specific number of addresses, if possible by a CPU.
     * 
     * @param input1
     * @param theCpu
     * @param lines
     * @param ttc
     * @param insttc 
     */
    public static void scanSetOfAddresses(Scanner input1, CPU theCpu, int lines, ArrayList<Integer> ttc, ArrayList<Integer> insttc) {
        for (int i = 0; i < lines; i++) {
            if (input1.hasNextLine()) {
                String line = input1.nextLine();
                //System.out.println(line);
                Scanner lineInput = new Scanner(line);
                lineInput.useDelimiter(",");
                if (lineInput.hasNext()) {
                    //Reads in an instruction address
                    int instruction = lineInput.nextInt();
                        //System.out.print(instruction + " ");
                    //System.out.println(Integer.toBinaryString(instruction));
                    theCpu.processAddress(instruction, false, 0);
                    ttc.add(theCpu.getTTC());
                    insttc.add(ttc.get(ttc.size() - 1));
                    //Reads in a read (0) or write(1) bit
                    if (lineInput.hasNextInt()) {
                        int readWrite = lineInput.nextInt();
                        if (lineInput.hasNext()) {
                            int data = lineInput.nextInt();
                                //System.out.print(data + " " + readWrite + " ");
                            //System.out.println(Integer.toBinaryString(data));
                            theCpu.processAddress(data, true, readWrite);
                            ttc.add(theCpu.getTTC());
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets configurations from a .txt file. The order is: Block size, L1 size,
     * L1 Latency, L2 size, L2 Latency, L3 size, L3 Latency, Mem1 size, Mem
     * 1latency, Mem2 size, Mem2 read latency, mem2 write latency, Cache
     * associativity
     *
     * @return array of the above configurations.
     */
    public static int[] getConfigurations() {
        int[] configs = new int[13];
        try {
            Scanner config = new Scanner(new File("C:\\Users\\Kim\\Documents\\NetBeansProjects\\CacheSimulator\\src\\cachesimulator\\config.txt"));

            int index = 0;
            while (config.hasNextLine()) {
                String line = config.nextLine();
                Scanner s = new Scanner(line);
                if (s.hasNext()) {
                    String skip = s.next();
                }
                if (s.hasNextInt()) {
                    configs[index] = s.nextInt();
                    index++;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("The file is not found.");
        }
        return configs;
    }

    public static void displayResults(Simulator duo, ArrayList<Integer> ttc, ArrayList<Integer> insttc) {
        System.out.println(duo.getHitMissStats());
        //Trace TTC
        int sum = 0;
        for (Integer i : ttc) {
            sum += i;
        }
        System.out.println("Total TTC for traces: " + sum);
        //Trace average TTC
        int inssum = 0;
        for (Integer i : insttc) {
            inssum += i;
        }
        double avg = inssum / insttc.size();
        System.out.println("Average TTC for instruction traces: " + avg);
        System.out.println("\nMESI Transition Matrix\n"
                + "\t\t\tTo:\n\t\tM\tE\tS\tI");
        System.out.print("From:");
        for (int i = 0; i < duo.myMESIMatrix.length; i++) {
            switch (i) {
                case 0:
                    System.out.print("\tM\t");
                    break;
                case 1:
                    System.out.print("\tE\t");
                    break;
                case 2:
                    System.out.print("\tS\t");
                    break;
                case 3:
                    System.out.print("\tI\t");
                    break;
            }

            for (int states : duo.myMESIMatrix[i]) {
                System.out.print(states + "\t");
            }
            System.out.println();
        }
    }

    public Simulator(int theConfigs[]) {
        //0- BlockSize, 5- L3Size, 6- L3Latency, 7- lm1s, 8- lm1l, 9- lm2s, 10- lm2r, 11- lm2w, 12- assoc, theFirstCPU
        mySharedL3 = new L3Cache(theConfigs[0], theConfigs[5], theConfigs[6], theConfigs[7], theConfigs[8], theConfigs[9], theConfigs[10], theConfigs[11], theConfigs[12], myCpu1);
        myCpu1 = new CPU(theConfigs, mySharedL3);
        myCpu2 = new CPU(theConfigs, mySharedL3);
        myMESIMatrix = new int[4][4];
        myCpu1.setOtherCPU(myCpu2, myMESIMatrix);
        myCpu2.setOtherCPU(myCpu1, myMESIMatrix);
        mySharedL3.setMESIMatrix(myMESIMatrix);
    }

    public String getHitMissStats() {
        String stats = "CPU1:\n";
        stats += "L1i: \t" + myCpu1.myL1i.getStatString();
        stats += "L1d: \t" + myCpu1.myL1d.getStatString();
        stats += "L2: \t" + myCpu1.myL2.getStatString();
        stats += "Shared L3: \t" + myCpu1.mySharedL3.getStatString();
        stats += "1LM: " + mySharedL3.myMem1Accesses + "\t\t2LM: " + mySharedL3.myMem2Accesses + "\n";
        stats += "CPU1 Totals:\n";
        stats += myCpu1.cpuHitMissTotals();
        stats += "\nCPU2:\n";
        stats += "L1i: \t" + myCpu2.myL1i.getStatString();
        stats += "L1d: \t" + myCpu2.myL1d.getStatString();
        stats += "L2: \t" + myCpu2.myL2.getStatString();
        stats += "SharedL3:\n \t" + myCpu2.mySharedL3.getStatString();
        stats += "1LM: " + mySharedL3.myMem1Accesses + "\t\t2LM: " + mySharedL3.myMem2Accesses + "\n\n";
        stats += "CPU2 Totals:\n";
        stats += myCpu2.cpuHitMissTotals();
        stats += "\nProcessor Totals: \n" + getProcessorHitMissTotals();

        return stats;
    }

    public String getProcessorHitMissTotals() {
        String totals = "";
        totals += "\tAccesses: " + (myCpu1.addAccesses() + myCpu2.addAccesses());
        totals += "\tHits: " + (myCpu1.addHits() + myCpu2.addHits());
        totals += "\t\tMisses: " + (myCpu1.addMisses() + myCpu2.addMisses()) + "\n";
        totals += "\t\t\tHit rate: " + getHitRate() + "%\t\tMiss Rate: " + getMissRate() + "%\n";
        return totals;
    }

    public double getHitRate() {
        return ((int) ((double) (myCpu1.addHits() + myCpu2.addHits()) / (myCpu1.addAccesses() + myCpu2.addAccesses()) * 1000) / 10.0);
    }

    public double getMissRate() {
        return ((int) ((double) (myCpu1.addMisses() + myCpu2.addMisses()) / (myCpu1.addAccesses() + myCpu2.addAccesses()) * 1000) / 10.0);
    }

    @Override
    public String toString() {
        String dual = "CPU 1\n";
        dual += myCpu1;
        dual += "CPU 2\n";
        //dual += myCpu2;
        dual += "\nShared L3:\n";
        dual += mySharedL3;
        return dual;
    }

}
