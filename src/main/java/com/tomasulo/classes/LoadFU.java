package com.tomasulo.classes;

public class LoadFU extends FunctionalUnit {

    @Override
    public void execute(double address, double useless, String type) {

    }

    public boolean execute(double address, String type){
        int size = 0;
        switch(type) {
            case "LW":
            case "L.S":
            size = 4;
            break;
            case "LD":
            case "L.D":
            size = 8;
            break;
        }
        Cache cache = Simulator.cache;
        // not a cache miss
        if(cache.access((int)address, null, size, type)!=null){
            Object res = cache.access((int)address, null, size, type);
            if (res instanceof Double){
                this.result = (Double)res;
            }
            else{
                this.result=((Integer) res).doubleValue();
            }
            return true;
        }
        // when a cache miss
        return false;
    }

}
