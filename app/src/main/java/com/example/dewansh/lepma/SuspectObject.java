package com.example.dewansh.lepma;

public class SuspectObject {
    private String id,UID,modeDetect,contactRoot,name,age,sex,dob,address,gps,relative,aadhar,regDate,nextAppointment,photo;

    public SuspectObject(String id, String UID, String modeDetect, String contactRoot, String name, String age, String sex, String dob, String address, String gps, String relative, String aadhar, String regDate, String nextAppointment, String photo) {
        this.id = id;
        this.UID = UID;
        this.modeDetect = modeDetect;
        this.contactRoot = contactRoot;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.dob = dob;
        this.address = address;
        this.gps = gps;
        this.relative = relative;
        this.aadhar = aadhar;
        this.regDate = regDate;
        this.nextAppointment = nextAppointment;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SuspectObject() {
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "SuspectObject{" +
                "UID='" + UID + '\'' +
                ", modeDetect='" + modeDetect + '\'' +
                ", contactRoot='" + contactRoot + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", gps='" + gps + '\'' +
                ", relative='" + relative + '\'' +
                ", aadhar='" + aadhar + '\'' +
                ", regDate='" + regDate + '\'' +
                ", nextAppointment='" + nextAppointment + '\'' +
                '}';
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getModeDetect() {
        return modeDetect;
    }

    public void setModeDetect(String modeDetect) {
        this.modeDetect = modeDetect;
    }

    public String getContactRoot() {
        return contactRoot;
    }

    public void setContactRoot(String contactRoot) {
        this.contactRoot = contactRoot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getNextAppointment() {
        return nextAppointment;
    }

    public void setNextAppointment(String nextAppointment) {
        this.nextAppointment = nextAppointment;
    }
}
