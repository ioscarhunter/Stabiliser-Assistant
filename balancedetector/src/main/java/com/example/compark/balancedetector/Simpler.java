package com.example.compark.balancedetector;

/**
 * Created by Compark on 5/19/2015.
 */
public class Simpler {
    private Boolean x_status = false;
    private Boolean y_status = false;
    private Boolean z_status = false;
    private float x_max,x_min;
    private float y_max,y_min;
    private float z_max,z_min;
    public String status(float x,float y,float z){
        if(x>=x_min && x<=x_max){
            x_status = true;
        }
        if(y>=y_min && y<=y_max){
            y_status = true;
        }
        if(z>=z_min && z<=z_max){
            z_status = true;
        }
        if(x_status == true && y_status == true && z_status == true){
            return "BALANCE";
        }
        return "UNBALANCE";
    }
}
