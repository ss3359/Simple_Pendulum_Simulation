/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author owner
 */
import java.util.Scanner;
import java.util.Vector;
import java.math.BigInteger;
import java.math.*;
import java.util.*;
import javax.swing.*;

/*
This is code that gives an simulation of pedulum motion. The motion of a
pendulum can be expressed as a second order differential equation as follows 

                d2θ/dt2 + (g/L)sinθ = 0, 
        θ(t) = pi/6 radians, θ'(t)= sqrt(2*g(1-cos(θ_0)). 

This indicates simple harmonic motion since this equation involves trigonometric functions. 

However, we can express this second order non-linear equation into a system of 
first order nonlinear equations. This will reduce down to the following system 

                θ1' = θ2 
                θ2' = (-g/L)sin(θ1) 

                θ1(t) = pi/6 radians
                θ2(t)= sqrt(2*g(1-cos(θ_0))

 */
public class Orecchitette {

    //Constants 
    double g = 9.81; //m per seconds^2
    double h=0.05; //time step
    double gamma=0.01; //damping coefficient

    private double L; //This is the length of the pendulum 
    private double m;
    private double theta;
    
    public double t=0; //Initial Time
    public Orecchitette(double length, double mass, double t) {
        L = length;
        m = mass;
        theta = t;
    }

    public double GetLength() {
        return L;
    }

    public double GetMass() {
        return m;
    }

    public double GetAngle() {
        return theta;
    }

    public double Getθ2() {
        return Math.sqrt(2 * g * (1 - Math.cos(theta)));
    }
     public double Getθ1() {
        return theta;
    }

    //This is a function representing the acceleration of the pendulum
    // That is represented as the differential equation as above
    
    public double θ1(double t, double theta1,double theta2) {
        return theta2;

    }
    public double θ2(double t,double theta1,double theta2) {
        double g = this.g;
        double L = this.L;

        return (-g / L) * Math.sin(theta1)-gamma*theta2;

    }

    public void RK() {
        int iter = 100;
        double theta_1= this.Getθ1(); 
        double theta_2=this.Getθ2();
        double j1 = 0, j2 = 0, j3 = 0, j4 = 0;
        double k1 = 0, k2 = 0, k3 = 0, k4 = 0;
        
        for (int i = 0; i <= iter; i++) {
            j1=this.θ1(t,theta_1,theta_2);
            k1 = this.θ2(t, theta_1,theta_2);
            
            j2=this.θ1(t+(h/2),theta_1+(j1/2),theta_2+(k1/2));
            k2= this.θ2(t+(h/2),theta_1+(j1/2),theta_2+(k1/2));
            
            
            j3=this.θ1(t+(h/2),theta_1+(j2/2),theta_2+(k2/2));
            k3=this.θ2(t+(h/2),theta_1+(j2/2),theta_2+(k2/2));
            
            j4=this.θ1(t+h,theta_1+j3,theta_2+k3);
            k4=this.θ2(t+h,theta_1+j3,theta_2+k3);
            
            theta_1+=(h/6)*(j1+(2*j2)+(2*j3)+j4);
            theta_2+=(h/6)*(k1+(2*k2)+(2*k3)+k4);
            
            
            System.out.println("New Position: "+theta_1);
            System.out.println("New Velocity: " +theta_2);
            System.out.println("------------------------------");
            t+=h;
        }

    }

    public static void main(String args[]) {
            double l=5; //meters 
            double θ=Math.PI/3;
            double mass=10; // kgs 
            Orecchitette o = new Orecchitette(l,mass,θ);
            
            o.RK();
    }

}
