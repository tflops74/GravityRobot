/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotgravity;

/**
 *
 * @author Shun Baek  (BAEK Sung Hoon 백승훈)
 */


import java.util.Random;
import java.awt.Point;
import javax.swing.JPanel;

public class MovingRobot {
    private Random random = new Random();
    private Spacetime spacetime;
    private double bodyHalfWidth = 2;
    private double dt = 0.2;
    private double velocity = 5 ;
    public double x, y, angle;
    
    //private int count=0;
    
    private DPoint leftWheelPos = new DPoint();
    private DPoint rightWheelPos = new DPoint();
    
    public MovingRobot(double X, double Y, Spacetime spacetime) {
        x = X;
        y = Y;
        this.spacetime = spacetime;
    }
    
    public void move(double duration, JPanel jpanel) {

        angle = random.nextFloat()*3.1415926535*2;
        //angle += Math.PI / 16.0;

            
        for (double t = 0; t < duration; t+= dt) {
            double wsin;
            double wcos;

            wsin = bodyHalfWidth * Math.sin(angle);
            wcos = bodyHalfWidth * Math.cos(angle);
            leftWheelPos.setLocation(x-wsin, y+wcos);
            rightWheelPos.setLocation(x+wsin, y-wcos);

            
            double tau1 = spacetime.getTimeDilation(leftWheelPos);
            double tau2 = spacetime.getTimeDilation(rightWheelPos);

            //System.out.printf("tau1 %f at (%f,%f), tau2 %f at (%f, %f)\n", tau1, leftWheelPos.x, leftWheelPos.y, tau2, rightWheelPos.x, rightWheelPos.y);
            double l1 = velocity * tau1 * dt; 
            double l2 = velocity * tau2 * dt;
           
            double pi;
            if (l2 >= l1)
                pi = Math.asin((l2-l1)/(2*bodyHalfWidth));
            else 
                pi = -Math.asin((l1-l2)/(2*bodyHalfWidth));
            angle += pi;
            if (angle >= 2*Math.PI) 
                angle = angle - 2*Math.PI;
            else if (angle < 0) 
                angle = angle + 2*Math.PI;
            
            double l = Math.abs(l1+l2)/2;
            x += l*Math.cos(angle);
            y += l*Math.sin(angle);
            
            if (x>=RobotGravity.SpaceWidth-bodyHalfWidth)
                x = RobotGravity.SpaceWidth-1-bodyHalfWidth;
            else if (x<bodyHalfWidth)
                x = bodyHalfWidth;
            if (y>=RobotGravity.SpaceHeight-bodyHalfWidth)
                y = RobotGravity.SpaceHeight-1-bodyHalfWidth;
            else if (y<bodyHalfWidth)
                y = bodyHalfWidth;
            
            
            //printPosition();
        }
        
        
        
    }
    
    void printPosition()
    {
        System.out.printf("%8.2f, %8.2f, %8.3f\n", x, y, angle * 360 / (3.1415926535*2));
    }
    
    
}