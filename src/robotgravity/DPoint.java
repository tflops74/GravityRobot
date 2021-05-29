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

public class DPoint {
    public double x, y;
    public DPoint(){
        x = y = 0.0;
    }
    public DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
}