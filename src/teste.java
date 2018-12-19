
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
//import org.opencv.highgui.Highgui;

public class teste {

    private static String nomeDoArquivo;
    private static Mat minhaMatriz;
    private static final double[] VERDE = {0, 200, 0};
    private static final double[] BRANCO = {255, 255, 255};

    /*
    Futuramente, isso vai virar apenas um método onde se passa como parâmetro a imagem 
    ORIGINAL do Cataovo, e então ele vai pintar o fundo da imagem de branco, para que,
    em outra classe, sejam aplicados os algoritmos de reconhecimento do ovo.
     */
    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        nomeDoArquivo = "gato";
        minhaMatriz = Highgui.imread(nomeDoArquivo + ".png");

        // Possui problemas com .jpeg; converti na mão para .jpg
        // minhaMatriz = Highgui.imread("umOvo.png");
        Mat retorno = minhaMatriz.clone();
        BufferedImage bufImg = matToBuffImg(minhaMatriz);
        int cont = 0, contPintado = 0;

        //for passando pixel por pixel:
        for (int i = 0; i < bufImg.getWidth(); i++) {
            for (int j = 0; j < bufImg.getHeight(); j++) {
                System.out.print("Linha: " + i + " ~ Coluna" + j + ".\n");

                //variavel que vai receber a cor da vez no for:
                Color c = new Color(bufImg.getRGB(i, j));
                //imagem em RGB, mas trabalhamos com BGR
                double red = c.getRed();
                double green = c.getGreen();
                double blue = c.getBlue();

                // nesses parametros devemos passar aquilo que necessariamente
                // será alterado.               
                //CONDIÇÃO 0 - PRIMITIVA, IMAGEM ORIGINAL:
                //if((blue > (red + 100)) || (red >200 && blue >200 && green >200 ))
                //CONDIÇÃO 1 - IMAGEM PROCESSADA:
                //SATURAÇÃO - 5; EROSÃO 2X; MEDIAN BLUR RAIO 15:
                //if(!(red<50 && blue <50 && green<50))
                //CONDIÇÃO 2 - IMAGEM PROCESSADA:
                //SATURAÇÃO - 5; EROSÃO 2X; MEDIAN BLUR RAIO 15
                //diferente da anterior essa tenta evidenciar que
                //o ponto preto em questão se encontra
                //dentro de uma mancha.
                if ((red < 150) && (blue > 122)) {
                    Core.circle(retorno, new Point (i, j), 1, new Scalar (64 ,128, 255));
                    //matriz, point (x, y), 1, scalar (bgr)
                }

            }
        }
        Highgui.imwrite("resultado.png", retorno);

    }

    public Mat processar(Mat matriz) throws IOException {
        minhaMatriz = matriz;

        // Possui problemas com .jpeg; converti na mão para .jpg
        // minhaMatriz = Highgui.imread("umOvo.png");
        BufferedImage bufImg = matToBuffImg(minhaMatriz);
        int cont = 0, contPintado = 0;

        //for passando pixel por pixel:
        for (int i = 0; i < bufImg.getWidth(); i++) {
            for (int j = 0; j < bufImg.getHeight(); j++) {
                System.out.print("Linha: " + i + " ~ Coluna" + j + ".\n");

                //variavel que vai receber a cor da vez no for:
                Color c = new Color(bufImg.getRGB(i, j));
                //imagem em RGB, mas trabalhamos com BGR
                double blue = c.getRed();
                double green = c.getGreen();
                double red = c.getBlue();

                // nesses parametros devemos passar aquilo que necessariamente
                // será alterado.               
                //CONDIÇÃO 0 - PRIMITIVA, IMAGEM ORIGINAL:
                //if((blue > (red + 100)) || (red >200 && blue >200 && green >200 ))
                //CONDIÇÃO 1 - IMAGEM PROCESSADA:
                //SATURAÇÃO - 5; EROSÃO 2X; MEDIAN BLUR RAIO 15:
                //if(!(red<50 && blue <50 && green<50))
                //CONDIÇÃO 2 - IMAGEM PROCESSADA:
                //SATURAÇÃO - 5; EROSÃO 2X; MEDIAN BLUR RAIO 15
                //diferente da anterior essa tenta evidenciar que
                //o ponto preto em questão se encontra
                //dentro de uma mancha.
                if ((blue > (red + 30)) || (red > 80 && blue > 80 && green > 80)) {
                    minhaMatriz.put(j, i, BRANCO);
                    contPintado++;
                }
                cont++;
            }
        }
        System.out.println("pixels: " + cont);
        System.out.println("pixels pintados: " + contPintado);
        return minhaMatriz;

    }

    public static BufferedImage matToBuffImg(Mat img) {

        MatOfByte bytemat = new MatOfByte();
        Highgui.imencode(".png", img, bytemat);
        byte[] bytes = bytemat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);

        BufferedImage output = null;
        try {
            output = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;

    }

}
