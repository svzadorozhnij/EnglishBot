package com.example.EnglishBot.entity;

import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Type;
import org.hibernate.type.TextType;

@Entity
public class UserEntity {

    @Id
    private Long id;
    private String name;
    private Date dateReg;
    private Date lastDate;
    private String studiedStr;
    private HashMap<Long,Integer> studied = null;

    public String getStudiedStr() {
        return studiedStr;
    }

    public void setStudiedStr(String studiedStr) {
        this.studiedStr = studiedStr;
    }



    public UserEntity(Long id, String name, Date dateReg) {
        this.id = id;
        this.name = name;
        this.dateReg = dateReg;
    }


    public UserEntity() {
    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setStudied(HashMap<Long, Integer> studied) {
        this.studied = studied;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public HashMap<Long, Integer> getStudied() {
        return studied;
    }

}
