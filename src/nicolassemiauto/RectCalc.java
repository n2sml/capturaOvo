package nicolassemiauto;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import nicolassemiauto.*;
import org.opencv.core.Rect;


/**
 *
 * @author Nícolas Soares
 */
class RectCalc {
       
        public static final int TAM_HISTOGRAMA = 256;
        private static final float[] alcance = {0, 256};
        public static int pontuacao = 0;
        public static int quantidadeBrancos = 0;
        public static int quantidadePretos = 0;
        public static int larguraMat;
        public static int alturaMat;
        public static int contPixel = larguraMat*alturaMat;
        

        /**
         * Método que recebe uma matriz, e à partir dela, conta quantos pixels há nela em cada faixa do canal [que vai de 0 à 255].        
         * @param src Recebe a matriz a qual se deseja saber quantos pixels tem em cada faixa do canal.
         */
        public void countHistograma(Mat src) {
        List<Mat> canaisBGR = new ArrayList<>();
        Core.split(src, canaisBGR);
        
      
        MatOfFloat rangeHistograma = new MatOfFloat(alcance);
        boolean acumulado = false;
        Mat histogramaMat = new Mat();
        Imgproc.calcHist(canaisBGR, new MatOfInt(2), new Mat(), histogramaMat, new MatOfInt(TAM_HISTOGRAMA), rangeHistograma, acumulado);
      
        float[] rHistData = new float[(int) (histogramaMat.total() * histogramaMat.channels())];
        histogramaMat.get(0, 0, rHistData);
         
      
        for (int i = 0; i <= 255; i++){
        System.out.println(i + " - " + (int)rHistData[i]);      

        }
    }
    
    /**
     * Recebe um mat como parâmetro e retorna um mat com um histograma básico.   
     * @param src Recebe a matriz a qual se deseja calcular um histograma simples.
     * @return Histograma simples.
     */
    public Mat matHist(Mat src) {
        List<Mat> canaisBGR = new ArrayList<>();
        Core.split(src, canaisBGR);
        int tamanhoHistograma = 256;
        float[] alcance = {0, 256}; 
        MatOfFloat rangeHistograma = new MatOfFloat(alcance);
        boolean acumulado = false;
        Mat histogramaMat = new Mat();    
        Imgproc.calcHist(canaisBGR, new MatOfInt(0), new Mat(), histogramaMat, new MatOfInt(tamanhoHistograma), rangeHistograma, acumulado);

        int LarguraImagemHistograma = 512, AlturaImagemHistograma = 400;
        int binW = (int) Math.round((double) LarguraImagemHistograma / tamanhoHistograma);
        Mat histImage = new Mat( AlturaImagemHistograma, LarguraImagemHistograma, CvType.CV_8UC3, new Scalar( 0, 0, 0));
        Core.normalize(histogramaMat, histogramaMat, 0, histImage.rows(), Core.NORM_MINMAX);

        float[] bHistData = new float[(int) (histogramaMat.total() * histogramaMat.channels())];
        histogramaMat.get(0, 0, bHistData);

        
        for( int i = 1; i < tamanhoHistograma; i++ ) {
        
            Core.line(histImage, new Point(binW * (i - 1), AlturaImagemHistograma - Math.round(bHistData[i - 1])),
                    new Point(binW * (i), AlturaImagemHistograma - Math.round(bHistData[i])), new Scalar(255, 0, 0), 2);

        }    
         
        return histImage;
    }
    
    
    /**
     * Recebe uma matriz e retorna a sua densidade.
     * @param src Recebe a matriz a qual se deseja saber a densidade
     * @return o resultado da divisão entre as dimensões da matriz pela quantidade de pixels "pretos".
     */
    
    public int calcDensidadeDoRect(Mat src) {        
        larguraMat = src.cols();
        alturaMat = src.rows();
        
        
        double [] myColor = new double[3];
        
       
        for (int i = 0; i < larguraMat; i++){
            for (int j = 0; j < alturaMat; j++){
                if (src.get(i,j)[0]>=156){
                   quantidadeBrancos++;
                }
                else quantidadePretos++;                
                
                contPixel++;
            }
        } 
        //int retorno = quantidadePretos/contPixel; //CONSTRUÍDO COM SUCESSO (tempo total: 4 minutos 21 segundos)
        contPixel = 0;
        int retorno = quantidadePretos;
        quantidadePretos = 0;
        return retorno;

    }
    
    public int calcPontuacaoDoRect(Mat matriz) {       
        larguraMat = matriz.cols();
        alturaMat = matriz.rows();
       
        for (int y = 0; y < larguraMat; y++){
            for (int x = 0; x < alturaMat-1; x++){
                                
                if ((matriz.get(x,y)[0]<=156) && (matriz.get(x+1,y)[0]<=156)){
                   pontuacao++;
                }     
            }
        }     
        int retorno = pontuacao;
        pontuacao = 0;
        return retorno;

    }    
    
    public int calcQuadradosDaArea(int minX, int minY, int limite, ArrayList<MyPoint>pontos){
        int maxX = minX + limite, maxY = minY + limite;
        return calcQuadradosDaArea(minX, minY, maxX, maxY, pontos);
    }
    
 
    //Vortigaunt attacks
    public int calcQuadradosDaArea(int minX, int minY, int maxX, int maxY, ArrayList<MyPoint> pontos) {       
        int retorno = 0;
        for (int x = 0; x < pontos.size(); x++){
            MyPoint pontoAtual = pontos.get(x);
             //Se o ponto x atual for maior que o mínimo aceitavel e
             //Se o ponto x atual for menor que o máximo aceitavel e
             //Se o ponto y atual for maior que o mínimo aceitavel e
             //Se o ponto y atual for menor que o máximo aceitável.
            if (((pontoAtual.getX() > minX) && (pontoAtual.getX() <= maxX)) && ((pontoAtual.getY() > minY) && (pontoAtual.getY() <= maxY))){
                retorno++;
                System.out.print((pontoAtual.getX() > minX) + " ");
                System.out.print((pontoAtual.getX() <= maxX) + " ");
                System.out.print((pontoAtual.getY() > minX) + " ");
                System.out.println((pontoAtual.getY() <= maxY));
            }           
        }         
        
        //System.out.println("pontuacao calcQuadrados = " + retorno);
        return retorno;

    }    
    
    public int calcQuadradosDaArea(MyPoint pontoMinimo, int limite, ArrayList <MyPoint> coordenadas){
        int minX = (int) pontoMinimo.getX();
        int minY = (int) pontoMinimo.getY();
        int maxX = minX + limite;
        int maxY = minY + limite;
        int retorno = calcQuadradosDaArea(minX, minY, maxX, maxY, coordenadas);
        //System.out.println(retorno);
        return retorno;
    }
    
    public int calcQuadradosDaArea(MyPoint pontoMinimo, MyPoint pontoMaximo, ArrayList <MyPoint> coordenadas){
        int minX = (int) pontoMinimo.getX();
        int minY = (int) pontoMinimo.getY();
        int maxX = (int) pontoMaximo.getX();
        int maxY = (int) pontoMaximo.getY();
        return calcQuadradosDaArea(minX, minY, maxX, maxY, coordenadas);
    }
        
   
        
}