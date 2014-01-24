package com.supremefist.klwc;

public class AcupuncturePoint {
    public String name = "";
    public String englishName = "";
    public String location = "";
    public String function = "";
    public String needlingMethod = "";
    public String indication = "";
    public String contraIndication = "";
    public String application = "";
    public String miscellaneous = "";
    public String commonName = "";
    public Integer number = -1;

    public AcupuncturePoint(String name, String englishName, String location,
            String function, String needlingMethod, String indication, String contraIndication,
            String application, String miscellaneous, String commonName, int number) {
        this.name = name;
        this.englishName = englishName;
        this.location = location;
        this.function = function;
        this.needlingMethod = needlingMethod;
        this.indication = indication;
        this.contraIndication = contraIndication;
        this.application = application;
        this.miscellaneous = miscellaneous;
        this.commonName = commonName;
        this.number = number;
    }

    public String toString() {
        return "AcupuncturePoint (" + this.commonName + ")";
    }

}
