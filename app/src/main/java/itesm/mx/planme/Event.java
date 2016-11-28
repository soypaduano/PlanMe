package itesm.mx.planme;

import java.io.Serializable;

public class Event implements Serializable{

    private String name;
    private String description;
    private String time;
    private String plantype;
    private String date;
    private String address;
    private String byteArray;
    private String uid;

    public Event() {
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public Event(String name, String description, String time, String plantype, String date, String address, String byteArray, String uid) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.plantype = plantype;
        this.date = date;
        this.address = address;
        this.byteArray = byteArray;
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String getplantype() {
        return plantype;
    }

    public void setplantype(String plantype) {
        this.plantype = plantype;
    }

    public String getByteArray() {
        return byteArray;
    }

    public void setByteArray(String byteArray) {
        this.byteArray = byteArray;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public boolean equals(Event other) {
        if (!(other instanceof Event)) {
            return false;
        }
        return true;
    }
}
