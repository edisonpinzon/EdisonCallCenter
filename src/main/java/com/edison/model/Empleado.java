package com.edison.model;

public class Empleado implements Comparable<Empleado> {
    private final String  name;    
    private Integer value;

    public void setValue(int num) {
        this.value = num;
    }
    
    public Integer getValue(){
        return this.value;
    }
    
    public Empleado(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Empleado o) {
        
        if(this.getValue()> o.getValue()){
            return 1;
        }else if(this.getValue()< o.getValue()){
            return -1;
        }
        return 0;
    }
    
    
}
