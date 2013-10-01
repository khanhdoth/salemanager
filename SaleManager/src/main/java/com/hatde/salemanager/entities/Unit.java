package com.hatde.salemanager.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity(name = "Unit")
public class Unit implements java.io.Serializable {

    private int unitId;
    private String name;
    private Collection<UnitConvert> listOfUnitConvert = new ArrayList<>();

    public Unit() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "unit_unitConvert", joinColumns = {
        @JoinColumn(name = "unitId")}, inverseJoinColumns = {
        @JoinColumn(name = "unitConvertId")})
    public Collection<UnitConvert> getListOfUnitConvert() {
        return listOfUnitConvert;
    }

    public void setListOfUnitConvert(Collection<UnitConvert> listOfUnitConvert) {
        this.listOfUnitConvert = listOfUnitConvert;
    }
}