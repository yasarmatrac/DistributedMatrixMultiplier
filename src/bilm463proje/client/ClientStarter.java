package bilm463proje.client;

import bilm463proje.exception.IrregularMatrixException;
import bilm463proje.exception.InvalidResponseException;
import bilm463proje.client.request.ClientMatrixMultipleRequest;
import bilm463proje.client.response.ClientErrorResponse;
import bilm463proje.client.response.ClientMatrixMultipleResponse;
import bilm463proje.client.response.ClientResponse;
import bilm463proje.client.response.ClientResponseType;
import bilm463proje.exception.ConnectionFailedException;
import bilm463proje.server.Matrix;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * İstemci için tasarlanmış GUI nesnesidir. Hesaplamalar bu arayüz ile sunucuya
 * iletilir.
 *
 * @author 07051102,07051124,07050941
 */
public class ClientStarter extends javax.swing.JFrame {

    private Client client;

    /**
     * Default Contructor 
     */
    public ClientStarter() {
        initComponents();
        buttonCalculate.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaMatrixA = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaMatrixB = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaResultMatrix = new javax.swing.JTextArea();
        buttonCalculate = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textFieldUrl = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textFieldPort = new javax.swing.JTextField();
        buttonConnect = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textAreaMatrixA.setColumns(20);
        textAreaMatrixA.setRows(5);
        jScrollPane1.setViewportView(textAreaMatrixA);

        textAreaMatrixB.setColumns(20);
        textAreaMatrixB.setRows(5);
        jScrollPane2.setViewportView(textAreaMatrixB);

        textAreaResultMatrix.setColumns(20);
        textAreaResultMatrix.setRows(5);
        jScrollPane3.setViewportView(textAreaResultMatrix);

        buttonCalculate.setText("Hesapla");
        buttonCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalculateActionPerformed(evt);
            }
        });

        jLabel1.setText("Server:");

        textFieldUrl.setText("localhost");

        jLabel2.setText("Port:");

        textFieldPort.setText("6789");

        buttonConnect.setText("Bağlan");
        buttonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(textFieldUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonConnect))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(buttonConnect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(buttonCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(185, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Hesaplama buttonu çağırır. Hesaplama isteği yollanır.
     *
     * @param evt
     */
    private void buttonCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalculateActionPerformed
        String textMatrixA = textAreaMatrixA.getText();
        String textMatrixB = textAreaMatrixB.getText();

        try {
            Matrix matrixA = MatrixHelper.convertTextToMatrix(textMatrixA);
            Matrix matrixB = MatrixHelper.convertTextToMatrix(textMatrixB);
            ClientMatrixMultipleRequest request = new ClientMatrixMultipleRequest(matrixA, matrixB);
            client.sendRequest(request);
            ClientResponse response = client.receiveResponse();
            if (response.getType().equals(ClientResponseType.RESULT)) {
                ClientMatrixMultipleResponse multipleResponse = (ClientMatrixMultipleResponse) response;
                textAreaResultMatrix.setText(multipleResponse.getMatrix().toString());
            } else if (response.getType().equals(ClientResponseType.ERROR)) {
                ClientErrorResponse errorResponse = (ClientErrorResponse) response;
                JOptionPane.showMessageDialog(this,
                        errorResponse.getMessage(),
                        "Hata Mesajı",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                throw new InvalidResponseException();
            }

        } catch (IrregularMatrixException ex) {
            JOptionPane.showMessageDialog(this,
                    "Matrix satırları eşit uzunlukta olmalıdır.",
                    "Hatalı giriş",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ClientStarter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Sadece sayı girişi yapabilirsiniz.",
                    "Yanlış Format Girişi",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ClientStarter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "İstek sunucuya gönderilemedi.",
                    "Bağlantı Hatası",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ClientStarter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | InvalidResponseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Doğru İstek alınamadı.",
                    "Bağlantı Hatası",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ClientStarter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonCalculateActionPerformed

    /**
     * Sunucuya bağlanma butonu çalıştırır. Sunucuya bağlanmasını sağlar
     *
     * @param evt
     */
    private void buttonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConnectActionPerformed
        client = new Client(textFieldUrl.getText(), Integer.valueOf(textFieldPort.getText()));
        try {
            client.connect();
            buttonCalculate.setEnabled(true);
        } catch (ConnectionFailedException ex) {
            JOptionPane.showMessageDialog(this,
                    "Sunucuya bağlanılamadı.",
                    "Bağlantı Hatası",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonConnectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientStarter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientStarter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientStarter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientStarter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientStarter().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCalculate;
    private javax.swing.JButton buttonConnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea textAreaMatrixA;
    private javax.swing.JTextArea textAreaMatrixB;
    private javax.swing.JTextArea textAreaResultMatrix;
    private javax.swing.JTextField textFieldPort;
    private javax.swing.JTextField textFieldUrl;
    // End of variables declaration//GEN-END:variables
}
