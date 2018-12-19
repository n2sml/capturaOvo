/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nicolassemiauto;

import java.awt.Rectangle;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
 *
 * @author Nícolas Soares
 */
public class MatCalc {

    private static Mat matrizAtual, matrizOriginal, matrizAtual2;
    private static Rect meuRetangulo;
    private static MyPoint pontoMinimo, pontoMaximo;
    private static String nomeArquivo;
    private static int cont;
    public static final int TAMANHO_OVO = 80;
    public static final int INTERVALO = 4;
    public static final int TAMANHO_QUADRADO = 4;
    public static ArrayList<Integer> densidades;
    public static ArrayList<Integer> pontos;
    public static ArrayList<MyPoint> candidatos;
    private static RectCalc meuCalculadorDeRetangulo;
    private static FileWriter arquivo;
    private static PrintWriter gravarArq;
    private static int menor_candidato = 9;

    /*
    Futuramente, isso vai virar apenas um método onde se passa como parâmetro a imagem
    do Cataovo, e irá ter como retorno quantos ovos foram encontrados levando em consideração
    a densidade da imagem processada.
     */
    public MatCalc() throws IOException {

        densidades = new ArrayList<>();
        pontos = new ArrayList<>();
        candidatos = new ArrayList<>();

        // this.nomeArquivo = nomeArquivo;
        meuCalculadorDeRetangulo = new RectCalc();
        //
        //arquivo = new FileWriter(nomeArquivo + "Densidade.csv");

    }

    public void CSVwrite(Mat matriz) throws IOException {
        this.matrizAtual = matriz;
        gravarArq.printf("Posicao X,");
        gravarArq.printf("Posicao Y,");
        gravarArq.printf("Densidade,");
        gravarArq.printf("%n");

        //for passando pixel por pixel:        
        for (int i = 0; i < matrizOriginal.cols() - INTERVALO; i = i + INTERVALO) {
            for (int j = 0; j < matrizOriginal.rows() - INTERVALO; j = j + INTERVALO) {
                System.out.print("Linha: " + i + " ~ Coluna: " + j + ".\n");

                pontoMinimo = new MyPoint((double) i, (double) j);
                pontoMaximo = new MyPoint((double) i + TAMANHO_QUADRADO, (double) j + TAMANHO_QUADRADO);
                meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                matrizAtual = matrizOriginal.submat(meuRetangulo);

                densidades.add(meuCalculadorDeRetangulo.calcDensidadeDoRect(matrizAtual));
                gravarArq.printf("%d,", j);
                gravarArq.printf("%d,", i);
                gravarArq.printf("%d,", densidades.get(cont));
                gravarArq.printf("%n");
                System.out.println("TOTAL DE PRETOS: " + meuCalculadorDeRetangulo.calcDensidadeDoRect(matrizAtual));
                cont++;
            }
        }
        arquivo.close();
    }

    public void CSVPontuacao(Mat matriz) throws IOException {
        this.matrizAtual = matriz;
        this.arquivo = new FileWriter(nomeArquivo + "Pontuador.csv");
        gravarArq.printf("Posicao X,");
        gravarArq.printf("Posicao Y,");
        gravarArq.printf("Pontuacao,");
        gravarArq.printf("%n");

        //for passando pixel por pixel:        
        for (int y = 0; y < matrizOriginal.cols() - INTERVALO; y = y + INTERVALO) {
            for (int x = 0; x < matrizOriginal.rows() - INTERVALO; x = x + INTERVALO) { //
                System.out.print("Linha: " + y + " ~ Coluna: " + x + ".\n");

                pontoMinimo = new MyPoint((double) y, (double) x);
                pontoMaximo = new MyPoint((double) y + TAMANHO_OVO, (double) x + TAMANHO_OVO);
                meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                matrizAtual = matrizOriginal.submat(meuRetangulo);

                pontos.add(meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual));
                gravarArq.printf("%d,", x);
                gravarArq.printf("%d,", y);
                gravarArq.printf("%d,", pontos.get(cont));
                gravarArq.printf("%n");
                System.out.println("Ponto da linha " + x + " na coluna " + y + " : " + meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual));
                cont++;
            }
        }
        arquivo.close();
    }

    public ArrayList<Integer> writePontuacao(Mat matriz) throws IOException {
        this.matrizAtual = matriz;

        //for passando pixel por pixel:        
        for (int y = 0; y < matrizOriginal.cols() - INTERVALO; y = y + INTERVALO) {
            for (int x = 0; x < matrizOriginal.rows() - INTERVALO; x = x + INTERVALO) { //
                pontoMinimo = new MyPoint((double) y, (double) x);
                pontoMaximo = new MyPoint((double) y + TAMANHO_QUADRADO, (double) x + TAMANHO_QUADRADO);
                meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                matrizAtual = matrizOriginal.submat(meuRetangulo);

                pontos.add(meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual));

                System.out.println("Ponto da linha " + x + " na coluna " + y + " : " + meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual));
                cont++;
            }
        }

        return pontos;
    }

    /**
     *
     * @param matriz
     * @param intervalX
     * @param intervalY
     * @param menorCandidato
     * @return Um ArrayList de MyPoint com todas as coordenadas onde há uma
     * subMat candidata à ovo.
     * @throws IOException
     */
    public ArrayList<MyPoint> writeCandidatos(Mat matriz, int intervalX, int intervalY, int menorCandidato) throws IOException {
        menor_candidato = menorCandidato;
        //for passando pixel por pixel:        
        for (int y = 0; y < matriz.cols() - TAMANHO_OVO; y = y + INTERVALO + intervalY) {
            for (int x = 0; x < matriz.rows() - TAMANHO_OVO; x = x + INTERVALO + intervalX) { //
                pontoMinimo = new MyPoint((double) y, (double) x);
                pontoMaximo = new MyPoint((double) y + TAMANHO_QUADRADO, (double) x + TAMANHO_QUADRADO);
                meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                Mat matrizTemp = matriz.submat(meuRetangulo);
                int meuTestador = meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizTemp);

                if (meuTestador >= menorCandidato) {
                    candidatos.add(new MyPoint(y, x));
                }

                //System.out.println("Ponto da linha " + x + " na coluna " + y + " : " + meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizTemp));
            }
        }
        //System.out.println("quadrados pintados: "+ candidatos.size()/2);
        return candidatos;
    }

    public void CSVCandidatos(Mat matriz) throws IOException {

        this.matrizAtual = matriz;
        this.arquivo = new FileWriter(nomeArquivo + "Candidatos.csv");
        gravarArq.printf("Posicao X,");
        gravarArq.printf("Posicao Y,");

        //for passando pixel por pixel:        
        for (int y = 0; y < matrizOriginal.cols() - INTERVALO; y = y + INTERVALO) {
            for (int x = 0; x < matrizOriginal.rows() - INTERVALO; x = x + INTERVALO) { //
                System.out.print("Linha: " + y + " ~ Coluna: " + x + ".\n");

                pontoMinimo = new MyPoint((double) y, (double) x);
                pontoMaximo = new MyPoint((double) y + TAMANHO_OVO, (double) x + TAMANHO_OVO);
                meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                matrizAtual = matrizOriginal.submat(meuRetangulo);
                int meuTestador = meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual);

                if (meuTestador >= this.menor_candidato) {
                    candidatos.get(x);
                    candidatos.get(y);
                    gravarArq.printf("%d,", x);
                    gravarArq.printf("%d,", y);
                }

                System.out.println("Ponto da linha " + x + " na coluna " + y + " : " + meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual));
                cont++;
            }
            System.out.println("Rodando... " + y);
        }

    }

    public int[] writeEleito(Mat matriz, int posX, int posY) throws IOException {

        int melhorCandidato = 0;
        int[] posicaoDoMelhorCandidato = new int[2];

        /*
        IMPLEMENTAR OS CASOS ESPECIAIS!!!!!
        if (posX <= TAMANHO_OVO && posY <= TAMANHO_OVO){
            for (int y = 0; y < posY + 1.5 * (TAMANHO_OVO); y++) {
                for (int x = 0; x < posX + 1.5 * (TAMANHO_OVO); x++) { //
                    System.out.print("Linha: " + y + " ~ Coluna: " + x + ".\n");

                    pontoMinimo = new Point((double) y, (double) x);
                    pontoMaximo = new Point((double) y + TAMANHO_OVO, (double) x + TAMANHO_OVO);
                    meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                    matrizAtual = matrizOriginal.submat(meuRetangulo);
                    int meuTestador = meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual);

                    if (meuTestador >= melhorCandidato) {
                        melhorCandidato = meuTestador;
                        posicaoDoMelhorCandidato[0] = x;
                        posicaoDoMelhorCandidato[1] = y;

                    }

                    System.out.println("Ponto da linha " + x + " na coluna " + y + " : " + meuCalculadorDeRetangulo.calcPontuacaoDoRect(matrizAtual));
                    cont++;
                }
            }
        }
         */
        // else {
        for (int y = posY - INTERVALO; y <= posY + (2 * (INTERVALO)); y = y + 10) {
            for (int x = posX - INTERVALO; x <= posX + (2 * (INTERVALO)); x = x + 10) { //

                pontoMinimo = new MyPoint((double) x, (double) y);
                pontoMaximo = new MyPoint((double) x + TAMANHO_OVO, (double) y + TAMANHO_OVO);
                meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                Mat tempMat = matriz.submat(meuRetangulo);
                int meuTestador = meuCalculadorDeRetangulo.calcPontuacaoDoRect(tempMat);
                System.out.print("meuTestador = " + meuTestador);
                if (meuTestador >= melhorCandidato) {
                    melhorCandidato = meuTestador;
                    posicaoDoMelhorCandidato[0] = x;
                    posicaoDoMelhorCandidato[1] = y;
                }

                System.out.println("ELEITO - Ponto da linha " + x + " na coluna " + y + " : " + melhorCandidato);
            }
        }
        // }
        System.out.println(posicaoDoMelhorCandidato[0] + " " + posicaoDoMelhorCandidato[1]);
        return posicaoDoMelhorCandidato;
    }

    /*
     * Recebe como parâmetro o arraylist com as coordenadas dos x e y já pintados.
     * Um for vai correr com um intervalo passado como parâmetro, e a cada iteração
     * vai analisar quantos quadrados foram pintados dentro daquele intervalo.
     * Se uma quantidade maior que o limite passado como parâmetro for alcançado,
     * será pintado um quadrado na região.
     */
    //A matriz, o tamanho do quadrado a ser desenhado, o intervalo do for, o limite minimo e o arrayList com as coordenadas
    public void CSVdesenharQuadrado(Mat matriz, int tamanho, int intervalo, int limiteMin, int limiteMax, ArrayList<MyPoint> coordenadas) throws IOException {

        arquivo = new FileWriter("QuadradoDensidade.csv");
        gravarArq = new PrintWriter(arquivo);
        gravarArq.printf("Posicao X MAX = %d,", matriz.cols());
        gravarArq.printf("Posicao Y MAX = %d,", matriz.rows());
        gravarArq.printf("Pontuacao,");
        gravarArq.printf("%n");
        //Core.rectangle(matriz, new Point (0, 0), new Point (0 + tamanho, 0 + tamanho), new Scalar (0,0,0), 5);
        for (int y = 0; y <= matriz.rows(); y = y + intervalo) {
            for (int x = 0; x <= matriz.cols(); x = x + intervalo) {
                int pontuacao = meuCalculadorDeRetangulo.calcQuadradosDaArea(x, y, x + tamanho, y + tamanho, coordenadas);
                gravarArq.printf("%d,", x);
                gravarArq.printf("%d,", y);
                gravarArq.printf("%d,", pontuacao);
                gravarArq.printf("%n");
                if (pontuacao > limiteMin && pontuacao < limiteMax) {
                    System.out.println("PINTOU POSX: " + x + "POSY: " + y + "PONTOS: " + pontuacao);
                    Core.rectangle(matriz, new Point(x, y), new Point(x + tamanho, y + tamanho), new Scalar(0, 0, 0), 5);
                }
            }
        }
        arquivo.close();
    }

    //A matriz, o tamanho do quadrado a ser desenhado, o intervalo do for, o limite minimo e o arrayList com as coordenadas
    public void CSVdesenharQuadrado2(Mat matriz, int tamanho, int intervalo, ArrayList<MyPoint> coordenadas) throws IOException {

        arquivo = new FileWriter("PosEmLinha.csv");
        gravarArq = new PrintWriter(arquivo);
        gravarArq.printf("Posicao MAX X = " + matriz.cols() + " MAX Y = " + matriz.rows() + " TOTAL = " + matriz.cols() * matriz.rows());
        gravarArq.printf("Pontuacao,");
        gravarArq.printf("%n");
        //Core.rectangle(matriz, new Point (0, 0), new Point (0 + tamanho, 0 + tamanho), new Scalar (0,0,0), 5);
        //int pontuacao = meuCalculadorDeRetangulo.calcQuadradosDaArea(x, y, x + tamanho, y + tamanho, coordenadas);
        int contador = 0;
        for (MyPoint pontoAtual : coordenadas) {
            int pontuacao = meuCalculadorDeRetangulo.calcQuadradosDaArea(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getX() + TAMANHO_OVO, pontoAtual.getY() + TAMANHO_OVO, coordenadas);
            gravarArq.printf("%d,", contador);
            gravarArq.printf("%d,", pontuacao);
            gravarArq.printf("%n");
            contador++;
        }
        arquivo.close();
    }

    // X vai ser a posição, primeiro mudando Y pra depois mudar X [(0,0) (0,1) (0,2)... (1,0) (1,1)]
    public ArrayList<Area> pontoMaximo2(Mat matriz, int limite) throws IndexOutOfBoundsException, IOException {
        ArrayList<Area> temp = new ArrayList();
        ArrayList<Area> pico = new ArrayList();
        ArrayList<Area> retorno = new ArrayList();

        RectCalc rectCalc = new RectCalc();
        Rectangle RAtualOrdenado;
        Rectangle RAtualSeparado;
        int contadorTeste = 0;

        /*
        Primeiramente, eu vou percorrer a imagem bloco a bloco
        gerar um retangulo levando em consideração a posição atual, 
        calcular a densidade do retangulo em questão
        e se a densidade for acima de x, armazenar em um array
         */
        //percorrer a imagem bloco a bloco
        for (int i = 0; i < matriz.cols() - TAMANHO_OVO; i = i + limite) {
            for (int j = 0; j < matriz.rows() - TAMANHO_OVO; j = j + limite) {

                //gerando um retangulo levando em consideração a posição atual
                //Esse é o meu X
                pontoMinimo = new MyPoint((double) i, (double) j);
                pontoMaximo = new MyPoint((double) i + TAMANHO_OVO, (double) j + TAMANHO_OVO);
                meuRetangulo = new Rect(pontoMinimo, pontoMaximo);

                matrizAtual = matriz.submat(meuRetangulo);

                //Esse é o meu Y
                //calculando a densidade do retangulo
                int meuY = rectCalc.calcDensidadeDoRect(matrizAtual);
                //System.out.println(meuY);

                contadorTeste++;

                //A condição tem que ser atendida aqui:
                if (meuY > 50) {
                    temp.add(new Area(new MyPoint((double) i, (double) j), meuY));
                }
            }
        }
        System.out.println("tamanho do array temporário = " + temp.size());
        System.out.println("apague isto = " + contadorTeste);

        //Nós temos agora, um array de Areas com coordenadas e densidades
        //Precisamos agora criar um novo array de Areas ,
        //Só que ordenadas de acordo com a densidade
        //E sem colidirem entre si.
        /*
        arquivo = new FileWriter("Diferencas.csv");
        gravarArq = new PrintWriter(arquivo);
        gravarArq.printf("Eixo X = posicoes, ");
        gravarArq.printf("Eixo Y = subtracao de y2 por y1.");
        gravarArq.printf("%n");
         */
        //Aqui será executado o cálculo que vai saber se 
        //A curva da posição/densidade começou a descer.
        for (int c = 0; c < temp.size() - 2; c++) {

            //ALTERNATIVA 1:
            //Criar 3 pontos e comparar se a curva está descendo mas estava subindo antes
            /*
            int densidadeAtual = temp.get(c).getDensidade();
            int densidadeAnterior = temp.get(c-1).getDensidade();
            int densidadeProximo = temp.get(c+1).getDensidade();

            boolean subindo = densidadeAtual < densidadeProximo;
            boolean subindoAntes = densidadeAnterior < densidadeAtual;

            if ((!subindo) && (subindoAntes)) {
                pico.add(temp.get(a));
            }
             */
            //ALTERNATIVA 2:
            //Calcular x1 - x2 e verificar se esse valor é negativo:
            System.out.println(c);
            int calculo = temp.get(c).getDensidade() - temp.get(c + 1).getDensidade();
            System.out.println(calculo);
            System.out.println("%n");

            int key = 600;
            //AQUI!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (((calculo) > 10) && (temp.get(c).getDensidade() > key)) {
                pico.add(temp.get(c));
            }

        }

        Collections.sort(pico);

        //Ordenando as Areas de acordo com a densidade
        //(da maior para menor)
        //Adiciono logo no início do novo array temp o valor com a maior densidade
        //do array pico:
        retorno.add(pico.get(0));
        int i = 0;

        
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        System.out.println(retorno.get(0));
        
        
        return retorno;
    }

}
//
//////Testezinho:
//        temp = new ArrayList();
//        int qtd = 12;
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//        for (int i = 0; i < qtd*50; i = i + 50) {
//            
//            temp.add(pico.get(i));
//            System.out.println(pico.get(i).getDensidade());
//        }
//
//        return temp;
//    }
//}
