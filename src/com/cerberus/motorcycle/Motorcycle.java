package com.cerberus.motorcycle;

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
    private final MotorcycleCylinderVolume cylinderVolume;

    /**
     * default contructor
     * @param _name name of motorcycle
     * @param _price price of motorcycle
     * @param _type type of motorcycle
     */
    public Motorcycle(String _name, double _price, MotorcycleTransmissionType _type, MotorcycleBrand _brand, MotorcycleCylinderVolume _enginePower) {
        name = _name;
        price = _price;
        transmissionType = _type;
        brand = _brand;
        cylinderVolume = _enginePower;
        id = UUID.randomUUID().toString();
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
     * @return cylinder volume of motorcycle
     */
    public MotorcycleCylinderVolume getCylinderVolume() {
        return cylinderVolume;
    }

    /**
     * @return manufacturer brand of motorcycle
     */
    public MotorcycleBrand getBrand() {
        return brand;
    }

//    /**
//     * Sets price of motorcycle
//     * @param _price amount to be set
//     */
//    public void setPrice(double _price) {
//        price = _price;
//    }
}
