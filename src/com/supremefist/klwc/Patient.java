package com.supremefist.klwc;

import java.util.Date;

public class Patient implements Comparable {
    private int id = -1;
    private String firstName = "";
    private String surName = "";
    private String street = "";
    private String houseNumber = "";
    private String residence = "";
    private String country = "";
    private Date dateOfBirth = null;
    private Date dateOfRegistration = null;
    private String phoneNumber = "";
    private String emailAddress = "";
    private String postCode = "";
    private String sex = "";
    private String bsn = "";

    public Patient(int id, String firstName, String surName, String street,
            String houseNumber, String residence, String country,
            Date dateOfBirth, Date dateOfRegistration, String phoneNumber,
            String emailAddress, String postCode, String sex, String bsn) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.residence = residence;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.postCode = postCode;
        this.sex = sex;
        this.bsn = bsn;
    }

    public String toString() {
        return  "Patient (" + 
                "id: " + id + ", " +
                "name: " + firstName + " " + surName + ", " +
                "street: " + street + ", " +
                "houseNumber: " + houseNumber + ", " +
                "residence: " + residence + ", " +
                "country: " + country + ", " +
                "dateOfBirth: " + dateOfBirth + ", " +
                "dateOfRegistration: " + dateOfRegistration + ", " +
                "phoneNumber: " + phoneNumber + ", " +
                "emailAddress: " + emailAddress + ", " +
                "postCode: " + postCode + ", " +
                "sex: " + sex + ", " +
                "bsn: " + bsn;
                
    }

    public String getName() {
        return firstName + " " + surName;
    }

    @Override
    public int compareTo(Object o) {
        return (this.getName().compareTo(((Patient)o).getName()));
    }
}
