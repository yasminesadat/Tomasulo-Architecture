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
        if(size == 4 && type.equals("SW"))
        Simulator.cache.access((int)address,(int)value,size,type);
        else if (size == 4 && type.equals( "S.S"))
        Simulator.cache.access((int)address,(float)value,size,type); 
        else
        Simulator.cache.access((int)address,value,size,type); 

        //for testing
        String accessType= "";
        switch(type){
        case "SW": 
        accessType = "LW"; 
        break;
        case "S.S":
        accessType = "L.S";
        break;
        case "SD":
        accessType = "LD"; 
        break;
        case "S.D":
        accessType = "L.D"; 
        break;
        }
        System.out.println("ACCESS STORED VALUE "+ Simulator.cache.access((int)address,null,size,accessType));

    }
    public boolean tryAccess(double address, String type){
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
            return true;
        }
        // when a cache miss
        return false;
    }

}
