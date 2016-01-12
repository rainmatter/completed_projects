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
public class Entry {
    public int myIndex;
    public int myTag;
    public int myAddress;
    public int myOffsetSize;
    public int myIndexSize;
    public Mesi myMESIState;
    
    public Entry(int theAddress, int theBlockSize, int theEntries, int theRowSize) {
        myAddress = theAddress;
        myOffsetSize = getLogBase2(theBlockSize);
        //System.out.println("" + Math.ceil(Math.l((double) theBlockSize)));
        myIndexSize = getLogBase2((int) (theEntries / theRowSize));
        myIndex = calculateIndex(theAddress);
        myTag = theAddress >> (myIndexSize + myOffsetSize);
        myMESIState = Mesi.EXCLUSIVE;
    }
    
    public final int getLogBase2(int theBlockSize) {
        String bin = Integer.toBinaryString(theBlockSize);
        int base = 0;
        while (bin.charAt(bin.length() - 1) == '0') {
            base++;
            bin = bin.substring(0, bin.length() - 1);
        }
        return base;
    }
    
    private int calculateIndex(int theAddress) {
        //System.out.println(myIndexSize);
        //int mask = 1 << (myIndexSize - 1);
        int mask = (int) (Math.pow(2, myIndexSize)) - 1;
        int addressIndex = (theAddress >> myOffsetSize) & mask;
        //System.out.println(String.format("%" + index + "s", Integer.toBinaryString(addressIndex)).replace(' ', '0'));
        return addressIndex;
    }
    
    public boolean equals(Entry e) {
        return e.myTag == myTag;
    }
    
    @Override
    public String toString() {
        return Integer.toBinaryString(myIndex) + " " + myMESIState + " " + Integer.toBinaryString(myTag) + " " + Integer.toBinaryString(myAddress);
    }
}
