package com.cerberus.models.motorcycle.engine;

public class Engine {

    private final float oilCapacity;
    private final int strokes;
    private final EngineCooling cooling;
    private final CylinderArrangement cylinderArrangement;
    private final FuelSystem fuelSystem;

    public Engine(float oilCapacity, int strokes, EngineCooling cooling, CylinderArrangement cylinderArrangement, FuelSystem fuelSystem) {
        this.oilCapacity = oilCapacity;
        this.strokes = strokes;
        this.cooling = cooling;
        this.cylinderArrangement = cylinderArrangement;
        this.fuelSystem = fuelSystem;
    }


    public float getOilCapacity() {
        return oilCapacity;
    }

    public int getStrokes() {
        return strokes;
    }

    public EngineCooling getCooling() {
        return cooling;
    }

    public CylinderArrangement getCylinderArrangement() {
        return cylinderArrangement;
    }

    public FuelSystem getFuelSystem() {
        return fuelSystem;
    }
}
