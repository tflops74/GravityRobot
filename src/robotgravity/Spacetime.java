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

public class Spacetime {
    public double dStarX, dStarY;
    public double dStarRadius;
    public double R = 45.0;

    
    Spacetime(int x, int y, int r) {
        dStarX = x;
        dStarY = y;
        dStarRadius = r;
    }
    
    double getTimeDilation(double x, double y) {
        
        double dx = x - dStarX;
        double dy = y - dStarY;
        double dist = Math.sqrt( dx*dx + dy*dy);
        
        if (dist <= dStarRadius)
            dist = dStarRadius;
        
        //return Math.exp(-R/dist);
        return 1.0 - R/dist;
    }
    
    double getTimeDilation(DPoint p) {
        return getTimeDilation(p.x, p.y);
    }
}