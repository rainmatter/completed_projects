/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

import java.util.Random;

/**
 *
 * @author Kim
 */
public class L12Cache {
    public int myBlockSize;
    public int myEntries;
    public int myLatency;
    public int myNWay;
    public int myRowSize;
    public int myColSize;
    public int mySets;
    public Entry[][] content;
    public int[][] myLRU;
    public int myAccesses;
    public int myHits;
    public int myMisses;
    public CPU myCPU;
    public boolean entryIsModified;
    public Entry myModEntry;
    public int[][] myMESIMatrix;
    public L3Cache mySharedL3;
    
    public L12Cache(int theBlockSize, int theEntries, int theLatency, int theNWay, CPU theCPU, L3Cache theSharedL3) {
        myBlockSize = theBlockSize;
        myEntries = theEntries;
        myLatency = theLatency;
        myNWay = theNWay;
        mySets = myEntries / myNWay;
        myRowSize = mySets;
        myColSize = myNWay;
        content = new Entry[myRowSize][myColSize];
        myLRU = new int[myRowSize][myColSize];
        myHits = 0;
        myMisses = 0;
        myAccesses = 0;
        myCPU = theCPU;
        mySharedL3 = theSharedL3;
        entryIsModified = false;
        
    }
    
    public void setMESIMatrix (int[][] theMESIMatrix) {
        myMESIMatrix = theMESIMatrix;
    }
    
    /**
     * Checks if the Cache contains an address.
     * Increments the hit or miss counts.
     * @param theAddress
     * @return whether or not the address is contained.
     */
    public boolean contains(int theAddress) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        myAccesses++;
        //boolean hit = false;
        for (Entry e : content[entry.myIndex]) {
            if (e != null && e.equals(entry) && e.myMESIState != Mesi.INVALID) {
                myHits++;
                //hit = true;
                return true;
            }
        }
        //if (!hit) {
            myMisses++;
        //}
        return false;
    }
    
    public boolean addEntry(int theAddress) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        boolean added = false;
        for (int i = 0; i < content[entry.myIndex].length; i++) {
            if (content[entry.myIndex][i] == null) {
                content[entry.myIndex][i] = entry;
                added = true;
                break;
            }
        }
        boolean modified = false;
        if (!added) {
            modified = replaceRandom(theAddress);
        }
        return modified;
    }
    
    public boolean replaceRandom(int theAddress) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        boolean isModified = false;
        Random r = new Random();
        int i = r.nextInt(myNWay);
        //System.out.println(i);
        if (content[entry.myIndex][i].myMESIState == Mesi.MODIFIED) {
            isModified = true;
        } 
        //Replace the entry with the new one.
        content[entry.myIndex][i] = entry;
        return isModified;
    }
    
    
    
    public void setShared(int theAddress) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        for (Entry e : content[entry.myIndex]) {
            if (e != null && e.equals(entry) && e.myMESIState != Mesi.INVALID) {
                if (e.myMESIState == Mesi.EXCLUSIVE) {
                    myMESIMatrix[1][2]++;
                } else if (e.myMESIState == Mesi.MODIFIED) {
                    myMESIMatrix[0][2]++;
                }
                
                e.myMESIState = Mesi.SHARED;
                 
            }
        }
    }
    
    public void setInvalid(int theAddress) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        for (Entry e : content[entry.myIndex]) {
            if (e != null && e.equals(entry) && e.myMESIState != Mesi.INVALID) {
                if (e.myMESIState == Mesi.EXCLUSIVE) {
                    myMESIMatrix[1][3]++;
                } else if (e.myMESIState == Mesi.MODIFIED) {
                    myMESIMatrix[0][3]++;
                } else if (e.myMESIState == Mesi.SHARED) {
                    myMESIMatrix[2][3]++;
                }
                
                e.myMESIState = Mesi.INVALID;
                 
            }
        }
    }
    
    public void setExclusive(int theAddress) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        for (Entry e : content[entry.myIndex]) {
            if (e != null && e.equals(entry) && e.myMESIState != Mesi.INVALID) {
                if (e.myMESIState == Mesi.MODIFIED) {
                    myMESIMatrix[0][1]++;
                } if (e.myMESIState == Mesi.SHARED) {
                    myMESIMatrix[2][1]++;
                }
                
                e.myMESIState = Mesi.EXCLUSIVE;
                 
            }
        }
    }
    
    public void setModified(int theAddress, boolean isNew) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        for (Entry e : content[entry.myIndex]) {
            if (e != null && e.equals(entry) && e.myMESIState != Mesi.INVALID) {
                //Register state change, if entry is not from memory
                if (!isNew && e.myMESIState == Mesi.EXCLUSIVE) {
                    myMESIMatrix[1][0]++; //What if new entry?
                } else if (!isNew && e.myMESIState == Mesi.SHARED) {
                    myMESIMatrix[2][0]++; //What if new entry?
                } 
                e.myMESIState = Mesi.MODIFIED;
            }
        }
    }
    
    public boolean entryIsShared (int theAddress) {
        Entry entry = new Entry(theAddress, myBlockSize, myEntries, myNWay);
        for (Entry e : content[entry.myIndex]) {
            if (e != null && e.equals(entry)) {
                return e.myMESIState == Mesi.SHARED;
                 
            }
        }
        return false;
    }
    
    public String getStatString() {
        String stats = "Accesses: " + myAccesses + "\tHits: " + myHits + "\t\tMisses: " + myMisses + "\n";
        stats += "\t\t\tHit rate: " + ((int)((double) myHits / myAccesses * 1000) / 10.0) + "%\tMiss Rate: " + ((int)((double) myMisses / myAccesses * 1000) / 10.0) + "%\n";
        return stats;
    }
    
    @Override
    public String toString() {
        String cache = "";
        for (int i = 0; i < content.length; i++) {
            for (Entry e : content[i]){
                cache += e + "\n";
            }
            cache += "\n";
        }
        return cache;
    }
}
