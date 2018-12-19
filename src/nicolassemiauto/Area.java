/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nicolassemiauto;

import org.opencv.core.Rect;

/**
 *
 * @author Nícolas Soares
 */
public class Area implements Comparable<Area>{
    private MyPoint ponto;
    private int densidade;
    private double x;
    private double y;
    private Rect rect;
    
    public Area(MyPoint myPoint, int den){
        this.ponto = myPoint;
        this.densidade = den;
        this.x = myPoint.getX();
        this.y = myPoint.getY();
        MyPoint pontoMinimo = new MyPoint((double) this.x, (double) this.y);
        MyPoint pontoMaximo = new MyPoint((double) this.x + MatCalc.TAMANHO_OVO, (double) this.x + MatCalc.TAMANHO_OVO);
        rect = new Rect(pontoMinimo, pontoMaximo);
    }
    
    public int getDensidade(){
        return this.densidade;
    }

    @Override
    public int compareTo(Area myPixel) {
     if (this.densidade > myPixel.getDensidade()) {
          return -1;
     }
     if (this.densidade < myPixel.getDensidade()) {
          return 1;
     }
     return 0;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    public Rect getRect(){
        return this.rect;
    }

    
}
