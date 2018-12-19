/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nicolassemiauto;

import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
 
public class PrimeiroGrafico extends JFrame {
 
   public PrimeiroGrafico() {
      super( "Meu Primeiro Grafico" ); //Define o titulo da tela
      //add grafico
      this.pack(); //para ajustar automaticamente o Frame
      
      
      DefaultCategoryDataset ds = new DefaultCategoryDataset();
      /*
      for (int c = 0; c < meuArray.size(); c++){
           ds.addValue(meuArray.get(c), "min", "dia " + c); 
      }
      */
      ds.addValue(66, "maximo", "dia 5");
      ds.addValue(26, "maximo", "dia 6");
      ds.addValue(16, "maximo", "dia 7");
      ds.addValue(56, "maximo", "dia 8");
      ds.addValue(86, "minimo", "dia 7");
      ds.addValue(6, "minimo", "dia 8");
    

 
//Cria um objeto JFreeChart passando os seguintes parametros
JFreeChart grafico = ChartFactory.createLineChart("Meu Grafico", "Dia", 
    "Valor", ds, PlotOrientation.VERTICAL, true, true, false);
 
this.add( new ChartPanel( grafico ) );
   }
  
   public static void main(String[] args) {
      new PrimeiroGrafico().setVisible( true );
   }

}