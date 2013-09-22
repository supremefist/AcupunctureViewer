package com.supremefist.klwc;

import java.util.Date;

public class Consultation implements Comparable {

    private int id = -1;
    private Date dateOfConsultation = null;
    private String history = "";
    
    public Consultation(int id, Date dateOfConsultation, String history) {
        this.id = id;
        this.dateOfConsultation = dateOfConsultation;
        this.history = history;
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

    @Override
    public int compareTo(Object o) {
        return (this.getConsultationDate().compareTo(((Consultation)o).getConsultationDate()));
    }

}
