/**
 * Represents the CPU in the Dual Core Processor.
 *
 * @author Kim Version 3- 3/17/2015
 */
package cachesimulator;

/**
 * CPU with levels of cache memory.
 *
 * @author Kim
 */
public class CPU {
    
    public L12Cache myL1i;
    public L12Cache myL1d;
    public L12Cache myL2;
    public L3Cache mySharedL3;
    public int myBlockSize;
    public Integer myTraceTTC;
    public CPU myOtherCPU;
    public int[][] myMESIMatrix;
    
    public CPU(int[] theConfigs, L3Cache theSharedL3) {
        mySharedL3 = theSharedL3;
        //0- BlockSize, 1- L1Size, 2- L1Latency, 12- assoc, theFirstCPU
        myL1i = new L12Cache(theConfigs[0], theConfigs[1], theConfigs[2], theConfigs[12], this, mySharedL3);
        myL1d = new L12Cache(theConfigs[0], theConfigs[1], theConfigs[2], theConfigs[12], this, mySharedL3);
        //0- BlockSize, 3- L2Size, 4- L2Latency, 12- assoc, theFirstCPU
        myL2 = new L12Cache(theConfigs[0], theConfigs[3], theConfigs[4], theConfigs[12], this, mySharedL3);
        myTraceTTC = 0;
        
    }
    
    public void setOtherCPU(CPU theOther, int[][] theMESIMatrix) {
        myOtherCPU = theOther;
        myMESIMatrix = theMESIMatrix;
        myL1i.setMESIMatrix(theMESIMatrix);
        myL1d.setMESIMatrix(theMESIMatrix);
        myL2.setMESIMatrix(theMESIMatrix);
        
    }

    /**
     * Processes an address for instruction or data. Different operations for
     * read or write.
     *
     * @param theAddress the address being processed
     * @param isData whether the address is to data
     * @param theReadWrite 0- Read, 1- write
     */
    public void processAddress(int theAddress, boolean isData, int theReadWrite) {
        //if (theReadWrite == 0) {
        boolean isNew = false;
        boolean isShared = false;
        boolean isLocal = false;
        boolean entryWasModified = false;
        //Contains method checks for invalid entries.
        if (!isData && myL1i.contains(theAddress)) {
            //System.out.println("in L1i");
            myTraceTTC += myL1i.myLatency;
        } else if (isData && myL1d.contains(theAddress)) {
            System.out.println("in L1d");
            myTraceTTC += myL1d.myLatency;
            isShared = myL1d.entryIsShared(theAddress);
            isLocal = true;
        } else if (myL2.contains(theAddress)) {
            System.out.println("in L2");
            if (isData) {
                entryWasModified = myL1d.addEntry(theAddress);
                isShared = myL1d.entryIsShared(theAddress);
                myTraceTTC += myL1d.myLatency;
            } else {
                entryWasModified = myL1i.addEntry(theAddress);
                myTraceTTC += myL1i.myLatency;
            }
            myTraceTTC += myL2.myLatency;
            if (entryWasModified) {
                myTraceTTC += mySharedL3.pullFromMemory(theAddress, 0);
            }
            isLocal = true;
        } else if (mySharedL3.contains(theAddress)) {
                //This is the shared memory
            //System.out.println("in L3");
            if (isData) {
                entryWasModified = myL1d.addEntry(theAddress);
                myTraceTTC += myL1d.myLatency;
            } else {
                entryWasModified = myL1i.addEntry(theAddress);
                myTraceTTC += myL1i.myLatency;
            }
            if (entryWasModified || myL2.addEntry(theAddress)) {
                myTraceTTC += mySharedL3.pullFromMemory(theAddress, 0);
                exclusiviseLocally(theAddress);
            }
            //Shares the entry if it is in L3, but not invalid
            share(theAddress);
            myTraceTTC += myL2.myLatency;
            myTraceTTC += mySharedL3.myLatency;
            isShared = true;
        } else {
            myTraceTTC += mySharedL3.pullFromMemory(theAddress, 0);
            if (isData) {
                myL1d.addEntry(theAddress);
            } else {
                myL1i.addEntry(theAddress);
            }
            //Latency add for modified write back.
            //System.out.println(myL1i.isModified());
            /*
             if (myL1i.isModified()) {
             myL2.setExclsive(theAddress);
             myTraceTTC += myL2.myLatency;
             mySharedL3.setExclsive(theAddress);
             myTraceTTC += mySharedL3.myLatency;
             myTraceTTC += mySharedL3.pullFromMemory(theAddress, 1);
             }*/
            myL2.addEntry(theAddress);
            mySharedL3.addEntry(theAddress);
            if (isData) {
                myTraceTTC += myL1d.myLatency;
            } else {
                myTraceTTC += myL1i.myLatency;
            }
            myTraceTTC += myL2.myLatency;
            myTraceTTC += mySharedL3.myLatency;
            isNew = true;
        }
        //}
        if (theReadWrite == 1) {
            if (isNew) {
                modifyLocally(theAddress, isNew);
            } else if (isShared) {
                modifyAndInvalidate(theAddress);
            } else if (isLocal) {
                modifyLocally(theAddress, !isNew);
            }
        }
    }
    
    public void share(int theAddress) {
        myL1i.setShared(theAddress);
        myL1d.setShared(theAddress);
        myL2.setShared(theAddress);
        myOtherCPU.myL1i.setShared(theAddress);
        myOtherCPU.myL1d.setShared(theAddress);
        myOtherCPU.myL2.setShared(theAddress);
        mySharedL3.setShared(theAddress);
    }
    
    public void invalidate(int theAddress) {
        myL1i.setInvalid(theAddress);
        myL1d.setInvalid(theAddress);
        myL2.setInvalid(theAddress);
        //mySharedL3.setInvalid(theAddress);
    }
    
    public void exclusiviseLocally(int theAddress) {
        myL1i.setExclusive(theAddress);
        myL1d.setExclusive(theAddress);
        myL2.setExclusive(theAddress);
        mySharedL3.setExclusive(theAddress);
    }
    
    public void modifyLocally(int theAddress, boolean isNew) {
        myL1d.setModified(theAddress, isNew);
        myL2.setModified(theAddress, isNew);
        mySharedL3.setModified(theAddress, isNew);
    }
    
    public void modifyAndInvalidate(int theAddress) {
        modifyLocally(theAddress, false);
        myOtherCPU.invalidate(theAddress);
    }
    
    public String cpuHitMissTotals() {
        String totals = "";
        totals += "\tAccesses: " + addAccesses() + "\tHits: " + addHits() + "\t\tMisses: " + addMisses() + "\n";
        totals += "\t\t\tHit rate: " + getHitRate() + "%\tMiss Rate: " + getMissRate() + "%\n";
        return totals;
    }
    
    public int addAccesses() {
        return myL1i.myAccesses + myL1d.myAccesses + myL2.myAccesses + mySharedL3.myAccesses;
    }
    
    public int addHits() {
        return myL1i.myHits + myL1d.myHits + myL2.myHits + mySharedL3.myHits;
    }
    
    public int addMisses() {
        return myL1i.myMisses + myL1d.myMisses + myL2.myMisses + mySharedL3.myMisses;
    }
    
    public double getHitRate() {
        return ((int) ((double) addHits() / addAccesses() * 1000) / 10.0);
    }
    
    public double getMissRate() {
        return ((int) ((double) addMisses() / addAccesses() * 1000) / 10.0);
    }
    
    public int getTTC() {
        int ttc = myTraceTTC;
        myTraceTTC = 0;
        return ttc;
    }
    
    @Override
    public String toString() {
        String cpu = "L1i:\n";
        cpu += myL1i;
        cpu += "L1d:\n" + myL1d;
        cpu += "L2:\n" + myL2;
        return cpu;
    }
}
