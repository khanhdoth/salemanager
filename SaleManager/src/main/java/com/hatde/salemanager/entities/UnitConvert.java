package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "UnitConvert")
public class UnitConvert implements java.io.Serializable {

    private int unitConvertId;
    private Unit unitA;
    private float convertRate;

    public UnitConvert() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUnitConvertId() {
        return unitConvertId;
    }

    public void setUnitConvertId(int unitConvertId) {
        this.unitConvertId = unitConvertId;
    }

    @OneToOne
    @JoinColumn(name = "unitId")
    public Unit getUnitA() {
        return unitA;
    }

    public void setUnitA(Unit unitA) {
        this.unitA = unitA;
    }

    @Column(name = "ConvertRate")
    public float getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(float convertRate) {
        this.convertRate = convertRate;
    }
}