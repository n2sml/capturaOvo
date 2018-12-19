/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nicolassemiauto;

import nicolassemiauto.*;
import java.awt.Image;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 *
 * @author Meusejr
 */
public class Configuracao {
public class TuplaQuadricula {
   private int x=50;
   private int y=50;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    
}   
  TuplaQuadricula Tupla  = new TuplaQuadricula();
  static Mat matrizSource;
  static Mat OPCV_imageMickey;
  static Mat ImgQuadricula;
  static Mat ImgQuadriculaClicada;
  static Mat ImgQuadriculaClicadaAnterior;
  static Rect[] rect;
  static String DiretorioRaizSelecionado="C:\\users\\dexter\\Desktop\\UltimaPlaca\\Teste\\";//".\\";
  
  public  void SetaTuplaQuadricula(int x, int y)  
  {
      this.Tupla.setX(x);
      this.Tupla.setY(y);
  }

    public TuplaQuadricula getTupla() {
        return Tupla;
    }

    public void setTupla(TuplaQuadricula Tupla) {
        this.Tupla = Tupla;
    }
  
  
}
