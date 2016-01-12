/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

/**
 *
 * @author Kim
 */
public class L3Cache extends L12Cache {
    public int myMem1Size;
    public int myMem1Latency;
    public int myMem2Size;
    public int myMem2Reads;
    public int myMem2Writes;
    public int myMem2Accesses;
    public int myMem1Accesses;
    
    public L3Cache(int theBlockSize, int theEntries, int theLatency, int theMem1Size, int theMem1Latency, int theMem2Size, int theMem2Reads, int theMem2Writes, int theNWay, CPU theCPU) {
        super(theBlockSize, theEntries, theLatency, theNWay, theCPU, null);
        myMem1Size = theMem1Size * 1024;
        myMem1Latency = theMem1Latency;
        myMem2Size = theMem2Size * 1024 * 1024;
        myMem2Reads = theMem2Reads;
        myMem2Writes = theMem2Writes;
        myMem1Accesses = 0;
        myMem2Accesses = 0;
    }
    
    /**
     * Pulls from memory if a read.
     * Writes to memory if write.
     * Decodes to correct memory level.
     * @param theAddress
     * @param theReadWrite 0-read, 1-  write
     * @return the latency of this operation
     */
    public int pullFromMemory(int theAddress, int theReadWrite) {
        if (theAddress > myMem1Size) {
            //System.out.println(theAddress);
            myMem2Accesses++;
            myMem1Accesses++;
            if (theReadWrite == 0) {
            return myMem2Reads + myMem1Latency;
            } else {
                return myMem2Writes + myMem1Latency;
            }
        }
        //System.out.println("pulling from mem1");
        if (theReadWrite == 0){
        myMem1Accesses++;
        }
        return myMem1Latency;
    }
}
