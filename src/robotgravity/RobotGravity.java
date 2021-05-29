/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotgravity;

/**
 *
 * @author baek
 */
public class RobotGravity {
    public static int SpaceWidth = 1024;
    public static int SpaceHeight = 576;
    protected static Spacetime spacetime = new Spacetime(SpaceWidth/2,SpaceHeight/2, 50);
    protected static MovingRobot object = new MovingRobot(100.0, 100.0, spacetime);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SpacetimeFrame("Robot in Spacetime", spacetime, object);
        //System.out.println("sssss");
    }
    
}