/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nicolassemiauto;


import nicolassemiauto.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.CellRendererPane;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;


/**
 *
 * @author Meusejr
 */
public class  MatConverter {
    
    /*
    public static void salvaImagCApWebCam(Mat Imagem)
    {
        FateiaImagem.UltimaImagemcapWebCam = JOptionPane.showInputDialog("Entre nome do arquivo");
        Highgui.imwrite(FateiaImagem.UltimaImagemcapWebCam+".png", Imagem);
        
    }
    */
    /*
    public static Image capturaQuadriculaImg(java.awt.Point PontoClickMouse) throws IOException
    {   
        System.out.println(PontoClickMouse);
        int contaquad=0;       
        for(Rect retangulo:Configuracao.rect)
        { 
            
           // System.out.print("Relação xp<=>xr "+PontoClickMouse.x+"<=>"+retangulo.x+"\n Cont="+contaquad+
           //           "\n"+ Configuracao.rect[contaquad].x+"\n"+Configuracao.rect[contaquad]+"\n");
            contaquad++;
            //System.out.print("Clicou-se na quadricula Nº "+FateiaImagem.Conf.rect[5]);
           // System.out.print("Clicou-se na quadricula Nº "+FateiaImagem.Conf.rect.length);
           
         if (
              (PontoClickMouse.x>retangulo.x) && (PontoClickMouse.x<(retangulo.x+retangulo.width))
              && 
              (PontoClickMouse.y>retangulo.y) && (PontoClickMouse.y<(retangulo.y+retangulo.height))   
            )    
         {
             System.out.print("Clicou-se na quadricula Nº "+contaquad);
             UltimaQuadriculaClicada=contaquad-1;
             break;
         }
              
        }        
        
        //Mat ImgQuadricula=Configuracao.OPCV_image.submat(Configuracao.rect[contaquad-1]);
        
        Configuracao.ImgQuadricula=Configuracao.OPCV_image.submat(Configuracao.rect[contaquad-1]);
        
        FateiaImagem.t.imageToHist = Configuracao.ImgQuadricula;
        
        Image Img_aux=ConverMatparaImg(Configuracao.ImgQuadricula);
      
        //Image imageQuad = Img_aux.getScaledInstance(20*Img_aux.getWidth(null),20*Img_aux.getHeight(null), Image.SCALE_SMOOTH);

        Image imageQuad = Img_aux.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        
        
       return  imageQuad; //ConverMatparaImg(ImgQuadricula);
        
    }
    *//*
     public static Mat capturaQuadriculaMat(java.awt.Point PontoClickMouse) throws IOException
    {   
        System.out.println(PontoClickMouse);
        int contaquad=0;       
        for(Rect retangulo:Configuracao.rect)
        { 
            
           // System.out.print("Relação xp<=>xr "+PontoClickMouse.x+"<=>"+retangulo.x+"\n Cont="+contaquad+
           //           "\n"+ Configuracao.rect[contaquad].x+"\n"+Configuracao.rect[contaquad]+"\n");
            contaquad++;
            //System.out.print("Clicou-se na quadricula Nº "+FateiaImagem.Conf.rect[5]);
           // System.out.print("Clicou-se na quadricula Nº "+FateiaImagem.Conf.rect.length);
           
         if (
              (PontoClickMouse.x>retangulo.x) && (PontoClickMouse.x<(retangulo.x+retangulo.width))
              && 
              (PontoClickMouse.y>retangulo.y) && (PontoClickMouse.y<(retangulo.y+retangulo.height))   
            )    
         {
             System.out.print("Clicou-se na quadricula Nº "+contaquad);
             UltimaQuadriculaClicada=contaquad-1;
             break;
         }
              
        }        
        
        //Mat ImgQuadricula=Configuracao.OPCV_image.submat(Configuracao.rect[contaquad-1]);
        
        Configuracao.ImgQuadricula = Configuracao.OPCV_image.submat(Configuracao.rect[contaquad-1]);
         
        return  Configuracao.ImgQuadricula;
        
    }
    
    *//*
    
    public static Image QuadriculaImg(Mat Quad_image) throws IOException{
           
      //  Imgproc.cvtColor(Quad_image, Quad_image, Imgproc.COLOR_BGR2GRAY);
        Mat Quad_imageLocal=Quad_image.clone();
    
        //Imgproc.cvtColor(Quad_imageLocal, Quad_imageLocal, Imgproc.COLOR_BGR2GRAY);
        
        double imageX=Quad_image.size().width;
        double imageY=Quad_image.size().height;
        int XQuad=FateiaImagem.Conf.getTupla().getX();
        int YQuad=FateiaImagem.Conf.getTupla().getY();
        double AreaImagem=imageX*imageY;
        double AreaQuadricula=XQuad*YQuad;
        int QuantdiadeQuadricula=(int)( AreaImagem/AreaQuadricula);
        
        System.out.println("Resolução de image =("+imageX+","+imageY+")=>"+QuantdiadeQuadricula);
        
        Configuracao.rect= new Rect[QuantdiadeQuadricula];
        
        
      
            int i=0; // Conta Retângulos.
            for(int yinit=0; yinit<(imageY-YQuad); yinit+=YQuad)
            {
              for(int xinit=0; xinit<(imageX-XQuad); xinit+=XQuad)   
              {
                  
                Configuracao.rect[i]=new Rect();
                Configuracao.rect[i].width=XQuad;
                Configuracao.rect[i].height=YQuad;
                Configuracao.rect[i].x=xinit; 
                Configuracao.rect[i].y=yinit;
    
               Core.rectangle(Quad_imageLocal, new Point(Configuracao.rect[i].x, Configuracao.rect[i].y), new Point(Configuracao.rect[i].x + Configuracao.rect[i].width, Configuracao.rect[i].y + Configuracao.rect[i].height), new Scalar(0, 255, 0));           
           /*
               if (i==UltimaQuadriculaClicada)
               {
               Core.rectangle(Quad_imageLocal, new Point(Configuracao.rect[i].x, Configuracao.rect[i].y), new Point(Configuracao.rect[i].x + Configuracao.rect[i].width, Configuracao.rect[i].y + Configuracao.rect[i].height), new Scalar(0, 0, 255));           
                   
               }
             
               i++;
              }
              }
      
        
          Configuracao.ImgQuadriculaClicada=Quad_imageLocal;   
       
          return ConverMatparaImg(Quad_imageLocal);
        
    }
    */
    public static Image ConverMatparaImg(Mat Quad_image) throws IOException
    {
        MatOfByte bytemat = new MatOfByte(); 
        Highgui.imencode(".jpg", Quad_image, bytemat);
        byte[] bytes = bytemat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage Img = ImageIO.read(in);
        
        return Img;
    }
    
    public static Image ConverMatparaImgPng(Mat Quad_image) throws IOException
    {
        MatOfByte bytemat = new MatOfByte(); 
        Highgui.imencode(".png", Quad_image, bytemat);
        byte[] bytes = bytemat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage Img = ImageIO.read(in);
        
        return Img;
    }
    
    public static BufferedImage componentToImage(Component component, boolean visible) {
        
        if (visible) {
            BufferedImage img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            component.paintAll(g2d);
            return img;
        } 
        
        else {
            component.setSize(component.getPreferredSize());
            layoutComponent(component);
            BufferedImage img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TRANSLUCENT);
            CellRendererPane crp = new CellRendererPane();
            crp.add(component);
            crp.paintComponent(img.createGraphics(), component, crp, component.getBounds());
            return img;
        }
    }

    private static void layoutComponent(Component c) {
        synchronized (c.getTreeLock()) {
            c.doLayout();
            if (c instanceof Container) {
                for (Component child : ((Container) c).getComponents()) {
                    layoutComponent(child);
                }
            }
        }
    }
    
    public static Mat buffToMat(BufferedImage buff){
        /*
        byte[] pixels = ((DataBufferByte) buff.getRaster().getDataBuffer()).getData();
        for(int i = 0; i < buff.getWidth(); i++){
            for(int j = 0; j < buff.getHeight(); j++){
                
            }
        }
        */
        int[] pixel = null;
        int j = 0;
        
        Mat image = new Mat(buff.getHeight(), buff.getWidth(), CvType.CV_32SC1);
        
        for (int y = 0; y < buff.getHeight(); y++) {
            for (int x = 0; x < buff.getWidth(); x++) {
                pixel =  buff.getRaster().getPixel(x, y, new int[buff.getWidth()*buff.getHeight()]);
                image.put(y, x, pixel);
                System.out.println(pixel[0] + " - " + pixel[1] + " - " + pixel[2] + " - " + (buff.getWidth() * y + x));
            }
        }
        
        return image;
        
    }
    
    
}
