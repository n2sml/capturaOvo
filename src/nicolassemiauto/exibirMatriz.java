/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nicolassemiauto;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;

/**
 *
 * @author cientifico
 */
public class exibirMatriz extends javax.swing.JFrame {

    public exibirMatriz() {
        initComponents();
        centralizarComponente();
    }

    private ImageIcon imagemOriginal, imagemQuadrados, imagemCirculos, imagemCirculos2;
    private static final int VALOR_INTERVALO = 1;
    private static final int TAMANHO_QUADRADO = 50;
    private static final int MENOR_CANDIDATO = 9;
    public static int valor;
    private static Mat matrizOriginal;
    private static Mat matrizCirculos;
    private static Mat matrizCirculos2;
    private static Mat matrizQuadrado;
    janelaQuadrados janQuadrado;
    janelaCirculos janCirculos;
    janelaComAbas janelaAbas;

    public void ApresentaImagem(Mat aaa) throws IOException {
        imagemOriginal = new ImageIcon(MatConverter.ConverMatparaImgPng(aaa));
        this.Tela1.setIcon(imagemOriginal);

    }

    public void ApresentaImagemOriginal() throws IOException {
        imagemCirculos = new ImageIcon(MatConverter.ConverMatparaImgPng(matrizOriginal));
        janelaAbas.setGuia1(imagemOriginal);
    }

    public void ApresentaImagemQuadrados() throws IOException {
        imagemQuadrados = new ImageIcon(MatConverter.ConverMatparaImgPng(matrizQuadrado));
        janelaAbas.setGuia2(imagemQuadrados);
    }

    public void ApresentaImagemCirculos() throws IOException {
        imagemCirculos2 = new ImageIcon(MatConverter.ConverMatparaImgPng(matrizCirculos2));
        janelaAbas.setGuia3(imagemCirculos2);
        String legenda = "Quantidade de ovos: ";
        janelaAbas.setLegenda(legenda + valor);
    }

    private String FileName;
    JFileChooser fileChooser;

    private void CarregaImagem() throws IOException {
        //Carregando o arquivo da imagem...
        fileChooser = new JFileChooser();
        File DirInicial = new File(Configuracao.DiretorioRaizSelecionado);

        fileChooser.setCurrentDirectory(DirInicial);
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            FileName = file.toString();
            matrizOriginal = Highgui.imread(FileName);

            Mat myMat = matrizOriginal.clone();
            MatCalc matCalc = new MatCalc();
            ProcessaImagem meuProcessador = new ProcessaImagem();

            //Aplicando o primeiro filtro à imagem: removendo tons amarelados do fundo (falta threshold?)
            myMat = meuProcessador.processar(myMat);

            //Recebe os candidatos à ovo:
            ArrayList<MyPoint> meusCandidatos = matCalc.writeCandidatos(myMat, VALOR_INTERVALO, VALOR_INTERVALO, MENOR_CANDIDATO);

            //Definindo as matrizes com fundo branco:
            matrizCirculos = myMat.clone();
            matrizQuadrado = myMat.clone();
            matrizCirculos2 = myMat.clone();

            //Preenche com quadrados verdes a área onde há a probabilidade de haver um ovo:
            for (int i = 0; i < meusCandidatos.size(); i++) {
                MyPoint pontoAtual = meusCandidatos.get(i);
                MyPoint proximoPonto = new MyPoint(pontoAtual.getX() + VALOR_INTERVALO * 5, pontoAtual.getY() + VALOR_INTERVALO * 5);
                //String a = pontoAtual.toString();
                //System.out.println(a);
                Core.rectangle(matrizQuadrado, pontoAtual, proximoPonto, Cor.VERDE, 1);
            }

            //Retorna quantos quadrados verdes foram pintados na imagem:
            System.out.println("quadrados pintados: " + meusCandidatos.size());

            //IGNORE ISSO! SERVE PARA GERAR GRAFICOS DO EXCEL!
            //Escreve num CSV os pontos máximos:
            //A matriz, o tamanho do quadrado a ser desenhado, o intervalo do for, o limite minimo e o arrayList com as coordenadas
            //matCalc.CSVdesenharQuadrado(matrizQuadrado, matCalc.TAMANHO_OVO*10, 10, 10, 50, meusCandidatos);
            //Esse ArrayList recebe como parâmetro a matriz onde deseja-se pintar os picos, o ArrayList dos 
            //MyPoints onde há 
            /*
            ArrayList<MyPoint> teste = matCalc.pontoMaximo(matrizCirculos, meusCandidatos, 10);
            
            for (int a = 0; a < teste.size(); a++){
                MyPoint ponto = teste.get(a);
                Core.circle(matrizCirculos, ponto, 15, Cor.MAGENTA);
            }  
             */
            
            
            
            ArrayList<Area> teste2 = matCalc.pontoMaximo2(matrizCirculos2, 3);

            for (int a = 0; a < teste2.size(); a++) {
                Area meuPixel = teste2.get(a);
               // Core.circle(matrizCirculos2, new MyPoint(meuPixel.getX(), meuPixel.getY()), 5, Cor.VERMELHO);
                Core.rectangle(matrizCirculos2, new Point((double)meuPixel.getX(), (double)meuPixel.getY()), new Point((double)meuPixel.getX() + MatCalc.TAMANHO_OVO, (double)meuPixel.getY()+MatCalc.TAMANHO_OVO),Cor.VERDE);
            }

            
            
            ApresentaImagem(matrizOriginal);

            ApresentaImagemOriginal();
            ApresentaImagemQuadrados();
            ApresentaImagemCirculos();
            janelaAbas.setTitle("Quantidade de ovos na imagem - " + valor);
            janelaAbas.setVisible(true);
            //Menor ovo = 10
            //Maior ovo = 39*
        }

    }

    public void centralizarComponente() {
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dw = getSize();
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Tela1 = new javax.swing.JLabel();
        jButtonCarrega = new javax.swing.JButton();
        SubtituloCarregar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Carregar Imagem");
        setMaximumSize(new java.awt.Dimension(660, 556));
        setMinimumSize(new java.awt.Dimension(660, 556));

        Tela1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButtonCarrega.setText("Abrir Imagem");
        jButtonCarrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCarregaActionPerformed(evt);
            }
        });

        SubtituloCarregar.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        SubtituloCarregar.setText("  Clique aqui para carregar uma nova imagem:");
        SubtituloCarregar.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonCarrega)
                .addGap(276, 276, 276))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(SubtituloCarregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tela1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Tela1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubtituloCarregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCarrega, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCarregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCarregaActionPerformed
        try {
            // TODO add your handling code here:
            janelaAbas = new janelaComAbas();
            janelaAbas.setVisible(false);
            CarregaImagem();
        } catch (IOException ex) {
            Logger.getLogger(exibirMatriz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonCarregaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(exibirMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(exibirMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(exibirMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(exibirMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        System.loadLibrary("opencv_java249");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new exibirMatriz().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SubtituloCarregar;
    private javax.swing.JLabel Tela1;
    private javax.swing.JButton jButtonCarrega;
    // End of variables declaration//GEN-END:variables
}
