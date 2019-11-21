package com.cerberus.models.motorcycle;

import com.cerberus.models.helpers.StringHelper;
import com.cerberus.models.helpers.string.SidedLine;
import com.cerberus.models.motorcycle.engine.Engine;
import com.cerberus.models.motorcycle.engine.CylinderVolume;
import javafx.geometry.Side;

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

    public String toDetailString() {

        StringBuilder builder = new StringBuilder();

        int width = StringHelper.width;
        String indent = StringHelper.create(" ", 2);

        String seperator = StringHelper.create("-", width);
        String leading, spacer, trailing;
        SidedLine line;

        Engine engine = this.getEngineType();

        builder.append(seperator).append("\n");

        leading = indent + this.getName().toUpperCase();
        builder.append(leading).append("\n");

        builder.append(seperator).append("\n");

        /* main body */

        builder.append(new SidedLine(
                width,
                "PRICE",
                "RF " + StringHelper.formatMoney(this.getPrice())
        ).toString());

        builder.append(new SidedLine(
                width,
                "BRAND",
                this.getBrand().toString()
        ).toString());

        builder.append(new SidedLine(
                width,
                "TRANSMISSION",
                this.getTransmissionType().toString()
        ).toString());

        builder.append(new SidedLine(
                width,
                "TANK CAPACITY",
                StringHelper.formatMoney(this.getGasolineTankCapacity()) + " Ltr"
        ).toString());

        builder.append("\n");

        builder.append(new SidedLine(
                width,
                "OIL CAPACITY",
                StringHelper.formatMoney(engine.getOilCapacity()) + " Ltr"
        ).toString());

        builder.append(new SidedLine(
                width,
                "STROKES",
                String.valueOf(engine.getStrokes())
        ).toString());

        builder.append(new SidedLine(
                width,
                "COOLING",
                engine.getCooling().toString()
        ).toString());

        builder.append(new SidedLine(
                width,
                "CYLINDER ARRANGEMENT",
                engine.getCylinderArrangement().toString()
        ).toString());

        builder.append(new SidedLine(
                width,
                "CYLINDER VOLUME",
                engine.getCylinderVolume().toString()
        ).toString());

        builder.append(new SidedLine(
                width,
                "FUEL SYSTEM",
                engine.getFuelSystem().toString()
        ).toString());

        builder.append(seperator).append("\n");
        builder.append(seperator).append("\n");

        return builder.toString();

    }

}
