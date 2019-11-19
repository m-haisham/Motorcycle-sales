package com.cerberus.models.motorcycle;

import com.cerberus.models.motorcycle.engine.Engine;
import com.cerberus.models.motorcycle.engine.CylinderVolume;

import java.util.UUID;

/**
 * @author sahaf and yoosuf
 *
 */
public class Motorcycle {

    private final String id;
    private final String name;
    private final double price;
    private final MotorcycleTransmissionType transmissionType;
    private final MotorcycleBrand brand;

    private float gasolineTankCapacity;
    private final Engine engineType;

    /**
     * default constructor
     * @param _name name of motorcycle
     * @param _price price of motorcycle
     * @param _type type of motorcycle
     * @param engineType type of engine equipped on motorcycle
     * @param gasolineTankCapacity tank fuel capacity
     */
    public Motorcycle(String _name, double _price, MotorcycleTransmissionType _type, MotorcycleBrand _brand, Engine engineType, float gasolineTankCapacity) {
        this.name = _name;
        this.price = _price;
        this.transmissionType = _type;
        this.brand = _brand;
        this.engineType = engineType;
        this.gasolineTankCapacity = gasolineTankCapacity;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * @return name of motorcycle
     */
    public String getName() {
        return name;
    }

    /**
     * @return price of motorcycle
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return type of motorcycle
     */
    public MotorcycleTransmissionType getTransmissionType() {
        return transmissionType;
    }

    /**
     * @return manufacturer brand of motorcycle
     */
    public MotorcycleBrand getBrand() {
        return brand;
    }

    public float getGasolineTankCapacity() {
        return gasolineTankCapacity;
    }

    public void setGasolineTankCapacity(float gasolineTankCapacity) {
        this.gasolineTankCapacity = gasolineTankCapacity;
    }

    public Engine getEngineType() {
        return engineType;
    }

//    /**
//     * Sets price of motorcycle
//     * @param _price amount to be set
//     */
//    public void setPrice(double _price) {
//        price = _price;
//    }
}
