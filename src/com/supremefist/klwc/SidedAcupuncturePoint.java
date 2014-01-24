package com.supremefist.klwc;

public class SidedAcupuncturePoint implements Comparable {

    public static int LEFT = 2;
    public static int RIGHT = 3;
    
    public AcupuncturePoint p;
    public int side;
    
    public SidedAcupuncturePoint(AcupuncturePoint p, int side) {
        this.p = p;
        this.side = side;
    }

    @Override
    public int compareTo(Object o) {
        
        return this.p.number.compareTo(((SidedAcupuncturePoint)o).p.number);
    }
    
}
