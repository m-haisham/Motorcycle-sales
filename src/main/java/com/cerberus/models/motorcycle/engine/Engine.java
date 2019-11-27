package com.cerberus.models.motorcycle.engine;

import com.cerberus.input.option.EnumMenu;
import com.cerberus.input.query.Query;

import java.util.Scanner;

public class Engine {

    private final float oilCapacity;
    private final int strokes;
    private final EngineCooling cooling;
    private final CylinderArrangement cylinderArrangement;
    private final CylinderVolume cylinderVolume;
    private final FuelSystem fuelSystem;

    public Engine(float oilCapacity, int strokes, EngineCooling cooling, CylinderArrangement cylinderArrangement, CylinderVolume cylinderVolume, FuelSystem fuelSystem) {
        this.oilCapacity = oilCapacity;
        this.strokes = strokes;
        this.cooling = cooling;
        this.cylinderArrangement = cylinderArrangement;
        this.cylinderVolume = cylinderVolume;
        this.fuelSystem = fuelSystem;
    }

    public static Engine create() {
        Query query = Query.create();

        float oilCapacity = query.ask("Oil capacity", Scanner::nextFloat);
        int stroked = query.ask("Strokes", Scanner::nextInt);
        EngineCooling engineCooling = EnumMenu.create(EngineCooling.class).promptNoAction();
        CylinderArrangement cylinderArrangement = EnumMenu.create(CylinderArrangement.class).promptNoAction();
        CylinderVolume cylinderVolume = EnumMenu.create(CylinderVolume.class).promptNoAction();
        FuelSystem fuelSystem = EnumMenu.create(FuelSystem.class).promptNoAction();

        return new Engine(
                oilCapacity,
                stroked,
                engineCooling,
                cylinderArrangement,
                cylinderVolume,
                fuelSystem
        );

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

    public CylinderVolume getCylinderVolume() {
        return cylinderVolume;
    }
}
