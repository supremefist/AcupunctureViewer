package com.supremefist.klwc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Consultation implements Comparable {

    private int id = -1;
    private Date dateOfConsultation = null;
    private String history = "";
    private List<SidedAcupuncturePoint> points;
    
    public Consultation(int id, Date dateOfConsultation, String history, List<SidedAcupuncturePoint> points) {
        this.id = id;
        this.dateOfConsultation = dateOfConsultation;
        this.history = history;
        this.points = points;
    }

    public int getId() {
        return id;
    }
    
    public String getHistory() {
        return history;
    }
    
    public Date getConsultationDate() {
        return dateOfConsultation;
    }
    
    public List<SidedAcupuncturePoint> getAcupuncturePoints() {
        return points;
    }

    @Override
    public int compareTo(Object o) {
        return (this.getConsultationDate().compareTo(((Consultation)o).getConsultationDate()));
    }

}
