/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nicolassemiauto;

import org.opencv.core.Rect;

/**
 *
 * @author dexter
 */
public class MyPoint extends org.opencv.core.Point {
    public MyPoint(){
        super();
    }
    
    public MyPoint(double aX, double aY){
        super(aX, aY);
    }
    
    
    public MyPoint(double[] x){
        super(x);
    }
    
    public int getX(){
        return (int) this.x;
    };
    
    public int getY(){
        return (int) this.y;
    };

   
   

    
}
