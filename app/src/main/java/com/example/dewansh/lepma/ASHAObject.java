package com.example.dewansh.lepma;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.Map;

public class ASHAObject implements Serializable {
    private String id,UID,type,name,age,sex,address,contactno,aadhar,subcenter,PHC,blockPHC,district,state,photo,email,approval;

    public ASHAObject() {
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ASHAObject(String UID, String type, String name, String age, String sex, String address, String contactno, String aadhar, String subcenter, String PHC, String blockPHC, String district, String state, String photo, String email, String approval) {
        this.UID = UID;
        this.type = type;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.contactno = contactno;
        this.aadhar = aadhar;
        this.subcenter = subcenter;
        this.PHC = PHC;
        this.blockPHC = blockPHC;
        this.district = district;
        this.state = state;
        this.photo = photo;
        this.email = email;
        this.approval = approval;
    }

    public ASHAObject(String id, String UID, String type, String name, String age, String sex, String address, String contactno, String aadhar, String subcenter, String PHC, String blockPHC, String district, String state, String photo, String email, String approval) {
        this.id = id;
        this.UID = UID;
        this.type = type;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.contactno = contactno;
        this.aadhar = aadhar;
        this.subcenter = subcenter;
        this.PHC = PHC;
        this.blockPHC = blockPHC;
        this.district = district;
        this.state = state;
        this.photo = photo;
        this.email = email;
        this.approval = approval;
    }

    public ASHAObject(Map<String,Object> user) {
        this.UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.type = user.get("type").toString();
        this.name = user.get("name").toString();
        this.age = user.get("age").toString();
        this.sex = user.get("sex").toString();
        this.address = user.get("address").toString();
        this.contactno = user.get("contactno").toString();
        this.aadhar = user.get("aadhar").toString();
        this.subcenter = user.get("subcenter").toString();
        this.PHC = user.get("PHC").toString();
        this.blockPHC = user.get("blockPHC").toString();
        this.district = user.get("district").toString();
        this.state = user.get("state").toString();
        this.photo = user.get("photo").toString();
        this.email = user.get("email").toString();

    }

    @Override
    public String toString() {
        return "ASHAObject{" +
                "id='" + id + '\'' +
                ", UID='" + UID + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", contactno='" + contactno + '\'' +
                ", aadhar='" + aadhar + '\'' +
                ", subcenter='" + subcenter + '\'' +
                ", PHC='" + PHC + '\'' +
                ", blockPHC='" + blockPHC + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", approval='" + approval + '\'' +
                '}';
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getSubcenter() {
        return subcenter;
    }

    public void setSubcenter(String subcenter) {
        this.subcenter = subcenter;
    }

    public String getPHC() {
        return PHC;
    }

    public void setPHC(String PHC) {
        this.PHC = PHC;
    }

    public String getBlockPHC() {
        return blockPHC;
    }

    public void setBlockPHC(String blockPHC) {
        this.blockPHC = blockPHC;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
