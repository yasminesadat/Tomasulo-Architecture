package com.tomasulo.classes;

public class StoreFU extends FunctionalUnit {

    @Override
    public void execute(double address, double value, String type) {
        int size = 0;
        switch(type) {
            case "SW":
            case "S.S":
            size = 4;
            break;
            case "SD":
            case "S.D":
            size = 8;
            break;
        }
        Simulator.cache.access((int)address,value,size,type);
    }
    public boolean tryAccess(double address, String type){
        int size = 0;
        switch(type) {
            case "SW":
            case "S.S":
            size = 4;
            break;
            case "SD":
            case "S.D":
            size = 8;
            break;
        }
        Cache cache = Simulator.cache;
        // not a cache miss
        if(cache.access((int)address, null, size, type)!=null){
            return true;
        }
        // when a cache miss
        return false;
    }

}
