package src.presentacion;

import src.dominio.Compressor;
import src.dominio.Decompressor;
import src.persistencia.File;

import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GraphicMenu extends javax.swing.JFrame {

    private File sourceCompress = null;
    private File destinationCompress = null;
    private File sourceDecompress = null;
    private File destinationDecompress = null;
    private ErrorDialog message;
    
    public GraphicMenu() {
        initComponents();
        initialViewProperties();
    }
                        
    private void initComponents() {

        completePanel = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        btnCompress = new javax.swing.JPanel();
        comText = new javax.swing.JLabel();
        btnInfo = new javax.swing.JPanel();
        addText = new javax.swing.JLabel();
        btnDecompress = new javax.swing.JPanel();
        decText = new javax.swing.JLabel();
        menuSeparator = new javax.swing.JSeparator();
        titleProj = new javax.swing.JLabel();
        titulMode = new javax.swing.JPanel();
        compressTitle = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        closeButton = new javax.swing.JLabel();
        selectQual = new javax.swing.JComboBox<>();
        qualityText = new javax.swing.JLabel();
        selectAlg = new javax.swing.JComboBox<>();
        dstCompressButton = new javax.swing.JButton();
        dstCompressPath = new javax.swing.JTextField();
        srcCompressButton = new javax.swing.JButton();
        srcCompressPath = new javax.swing.JTextField();
        runCompressButton = new javax.swing.JButton();
        propTitle = new javax.swing.JLabel();
        algText = new javax.swing.JLabel();
        compressForm = new javax.swing.JPanel();
        titulModeDec = new javax.swing.JPanel();
        decompressTitle = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        dstDecompressPath = new javax.swing.JTextField();
        dstDecompressButton = new javax.swing.JButton();
        srcDecompressButton = new javax.swing.JButton();
        srcDecompressPath = new javax.swing.JTextField();
        runDecompressButton = new javax.swing.JButton();
        srcText = new javax.swing.JLabel();
        dstText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compressor/Decopressor - PROP");
        setLocationByPlatform(true);
        setUndecorated(true);

        completePanel.setBackground(new java.awt.Color(255, 255, 255));
        completePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidePanel.setBackground(new java.awt.Color(0, 18, 40));
        sidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCompress.setBackground(new java.awt.Color(32, 38, 50));
        btnCompress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCompressMousePressed(evt);
            }
        });

        comText.setBackground(new java.awt.Color(106, 116, 145));
        comText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comText.setForeground(new java.awt.Color(204, 204, 204));
        comText.setText("- Compress");

        javax.swing.GroupLayout btnCompressLayout = new javax.swing.GroupLayout(btnCompress);
        btnCompress.setLayout(btnCompressLayout);
        btnCompressLayout.setHorizontalGroup(
            btnCompressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCompressLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(comText, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        btnCompressLayout.setVerticalGroup(
            btnCompressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCompressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comText, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidePanel.add(btnCompress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 290, 50));

        btnInfo.setBackground(new java.awt.Color(32, 47, 90));
        btnInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnInfoMousePressed(evt);
            }
        });

        addText.setBackground(new java.awt.Color(32, 47, 90));
        addText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addText.setForeground(new java.awt.Color(204, 204, 204));
        addText.setText("+ Aditional Information");

        javax.swing.GroupLayout btnInfoLayout = new javax.swing.GroupLayout(btnInfo);
        btnInfo.setLayout(btnInfoLayout);
        btnInfoLayout.setHorizontalGroup(
            btnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnInfoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(addText, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        btnInfoLayout.setVerticalGroup(
            btnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addText, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidePanel.add(btnInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, -1, -1));

        btnDecompress.setBackground(new java.awt.Color(32, 47, 90));
        btnDecompress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDecompressMousePressed(evt);
            }
        });

        decText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        decText.setForeground(new java.awt.Color(204, 204, 204));
        decText.setText("- Decompress");

        javax.swing.GroupLayout btnDecompressLayout = new javax.swing.GroupLayout(btnDecompress);
        btnDecompress.setLayout(btnDecompressLayout);
        btnDecompressLayout.setHorizontalGroup(
            btnDecompressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDecompressLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(decText, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        btnDecompressLayout.setVerticalGroup(
            btnDecompressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDecompressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(decText, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidePanel.add(btnDecompress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, -1, -1));

        menuSeparator.setPreferredSize(new java.awt.Dimension(50, 5));
        sidePanel.add(menuSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 250, 30));

        titleProj.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titleProj.setForeground(new java.awt.Color(204, 204, 204));
        titleProj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleProj.setText("COMPRESSOR / DECOMPRESSOR");
        sidePanel.add(titleProj, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 250, 50));

        completePanel.add(sidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 520));

        titulMode.setBackground(new java.awt.Color(96, 116, 150));

        compressTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        compressTitle.setForeground(new java.awt.Color(255, 255, 255));
        compressTitle.setText("Compress File/Folder");

        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout titulModeLayout = new javax.swing.GroupLayout(titulMode);
        titulMode.setLayout(titulModeLayout);
        titulModeLayout.setHorizontalGroup(
            titulModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titulModeLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(compressTitle)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        titulModeLayout.setVerticalGroup(
            titulModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titulModeLayout.createSequentialGroup()
                .addGap(0, 52, Short.MAX_VALUE)
                .addGroup(titulModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titulModeLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titulModeLayout.createSequentialGroup()
                        .addComponent(compressTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        completePanel.add(titulMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 680, 110));

        closeButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        closeButton.setForeground(new java.awt.Color(51, 51, 51));
        closeButton.setText("X");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                closeButtonMousePressed(evt);
            }
        });
        completePanel.add(closeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 20, 20));

        selectQual.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        selectQual.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "50", "100", "150", "200" }));
        selectQual.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        completePanel.add(selectQual, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 240, -1));

        qualityText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        qualityText.setText("Choose the JPEG quality compression:");
        completePanel.add(qualityText, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 270, -1));

        selectAlg.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        selectAlg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "auto", "LZ78", "LZSS", "LZW", "JPEG" }));
        selectAlg.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        selectAlg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAlgActionPerformed(evt);
            }
        });
        completePanel.add(selectAlg, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 220, 240, -1));

        dstCompressButton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dstCompressButton.setText("Destination");
        dstCompressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dstCompressButtonActionPerformed(evt);
            }
        });
        completePanel.add(dstCompressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 390, 110, 20));

        dstCompressPath.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dstCompressPath.setText("Destination Path");
        completePanel.add(dstCompressPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, 530, -1));

        srcCompressButton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        srcCompressButton.setText("Source");
        srcCompressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcCompressButtonActionPerformed(evt);
            }
        });
        completePanel.add(srcCompressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 330, 110, 20));

        srcCompressPath.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        srcCompressPath.setText("Source Path");
        completePanel.add(srcCompressPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 310, 530, -1));

        runCompressButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        runCompressButton.setText("Run");
        runCompressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runCompressButtonActionPerformed(evt);
            }
        });
        completePanel.add(runCompressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 130, 30));

        propTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        propTitle.setForeground(new java.awt.Color(102, 102, 102));
        propTitle.setText("Projecte - PROP");
        completePanel.add(propTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 120, 30));

        algText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        algText.setText("Choose the compression algorithm:");
        completePanel.add(algText, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, 260, -1));

        compressForm.setBackground(new java.awt.Color(255, 255, 255));
        compressForm.setPreferredSize(new java.awt.Dimension(666, 510));
        compressForm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titulModeDec.setBackground(new java.awt.Color(96, 116, 150));
        titulModeDec.setPreferredSize(new java.awt.Dimension(666, 110));

        decompressTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        decompressTitle.setForeground(new java.awt.Color(255, 255, 255));
        decompressTitle.setText("Decompress File/Folder");

        jSeparator3.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout titulModeDecLayout = new javax.swing.GroupLayout(titulModeDec);
        titulModeDec.setLayout(titulModeDecLayout);
        titulModeDecLayout.setHorizontalGroup(
            titulModeDecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titulModeDecLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(decompressTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        titulModeDecLayout.setVerticalGroup(
            titulModeDecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titulModeDecLayout.createSequentialGroup()
                .addGap(0, 52, Short.MAX_VALUE)
                .addGroup(titulModeDecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titulModeDecLayout.createSequentialGroup()
                        .addComponent(decompressTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titulModeDecLayout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        compressForm.add(titulModeDec, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 680, 110));

        dstDecompressPath.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dstDecompressPath.setText("Destination Path");
        compressForm.add(dstDecompressPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 530, -1));

        dstDecompressButton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dstDecompressButton.setText("Destination");
        dstDecompressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dstDecompressButtonActionPerformed(evt);
            }
        });
        compressForm.add(dstDecompressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 110, 20));

        srcDecompressButton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        srcDecompressButton.setText("Source");
        srcDecompressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcDecompressButtonActionPerformed(evt);
            }
        });
        compressForm.add(srcDecompressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 260, 110, 20));

        srcDecompressPath.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        srcDecompressPath.setText("Source Path");
        compressForm.add(srcDecompressPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 530, -1));

        runDecompressButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        runDecompressButton.setText("Run");
        runDecompressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runDecompressButtonActionPerformed(evt);
            }
        });
        compressForm.add(runDecompressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 130, 30));

        srcText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        srcText.setText("Choose the source of the file/folder:");
        compressForm.add(srcText, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 260, -1));

        dstText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dstText.setText("Choose the destination of the Uncompressed file/folder:");
        compressForm.add(dstText, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 390, -1));

        completePanel.add(compressForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 680, 510));

        getContentPane().add(completePanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    final void initialViewProperties(){
        compressForm.setVisible(false);
        qualityText.setVisible(false);
        selectQual.setVisible(false);
        srcCompressPath.setEditable(false);
        dstCompressPath.setEditable(false);
        srcDecompressPath.setEditable(false);
        dstDecompressPath.setEditable(false);
    }
    
    void setColor(JPanel option){
        option.setBackground(new Color(32,38,50));
    }
    
    void resetColor(JPanel option){
        option.setBackground(new Color(32,47,90));
    }
    
    private void btnCompressMousePressed(java.awt.event.MouseEvent evt) {                                         
        setColor(btnCompress);
        resetColor(btnDecompress);
        resetColor(btnInfo);
        compressForm.setVisible(false);
        titulMode.setVisible(true);
        selectAlg.setVisible(true);
        dstCompressButton.setVisible(true);
        dstCompressPath.setVisible(true);
        srcCompressButton.setVisible(true);
        srcCompressPath.setVisible(true);
        runCompressButton.setVisible(true);
        algText.setVisible(true);
        optionQuality();
    }                                        

    private void btnDecompressMousePressed(java.awt.event.MouseEvent evt) {                                           
        setColor(btnDecompress);
        resetColor(btnCompress);
        resetColor(btnInfo);
        compressForm.setVisible(true);
        titulMode.setVisible(false);
        selectQual.setVisible(false);
        qualityText.setVisible(false);
        selectAlg.setVisible(false);
        dstCompressButton.setVisible(false);
        dstCompressPath.setVisible(false);
        srcCompressButton.setVisible(false);
        srcCompressPath.setVisible(false);
        runCompressButton.setVisible(false);
        algText.setVisible(false);
    }                                          

    private void btnInfoMousePressed(java.awt.event.MouseEvent evt) {                                     
        setColor(btnInfo);
        resetColor(btnDecompress);
        resetColor(btnCompress);
        compressForm.setVisible(false);
        titulMode.setVisible(false);
    }                                    

    private void closeButtonMousePressed(java.awt.event.MouseEvent evt) {                                         
        System.exit(0);
    }                                        

    private void runCompressButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        if (sourceCompress == null || destinationCompress == null){
            message = new ErrorDialog(this,true);
            message.changeValueLabel2("Source or Destination path not selected propperly !");
            message.setVisible(true);
        }
        else{ 
            String alg = (String)selectAlg.getSelectedItem();
            Compressor cmp = new Compressor(sourceCompress,destinationCompress,alg);
            cmp.compress();
            
            qualityText.setVisible(false); selectQual.setVisible(false);
            srcCompressPath.setText("Source Path");
            dstCompressPath.setText("Destination Path");
        }
        sourceCompress = null; destinationCompress = null;
    }                                                 

    private void srcCompressButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Text Files","txt");
        fc.setFileFilter(filtro);
        if ("JPEG".equals(selectAlg.getSelectedItem().toString())) {
            FileNameExtensionFilter filtroPpm = new FileNameExtensionFilter("Image Files","ppm");
            fc.setFileFilter(filtroPpm);
        }
        int select =  fc.showOpenDialog(this);
        if(select == JFileChooser.APPROVE_OPTION) {
            sourceCompress = new File(fc.getSelectedFile().getPath());
            this.srcCompressPath.setText(sourceCompress.getAbsolutePath());
        }
    }                                                 

    private void selectAlgActionPerformed(java.awt.event.ActionEvent evt) {                                          
        optionQuality();
    }                                         

    private void optionQuality(){
        if("JPEG".equals(selectAlg.getSelectedItem().toString())) {
            qualityText.setVisible(true);
            selectQual.setVisible(true);
        }
        else {
            qualityText.setVisible(false);
            selectQual.setVisible(false);
        }
    }
    private void dstCompressButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int select =  fc.showOpenDialog(this);
        
        if(select == JFileChooser.APPROVE_OPTION) {
            destinationCompress = new File(fc.getSelectedFile().getPath());
            this.dstCompressPath.setText(destinationCompress.getAbsolutePath());
        }
    }                                                 

    private void dstDecompressButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int select =  fc.showOpenDialog(this);
        
        if(select == JFileChooser.APPROVE_OPTION) {
            destinationDecompress = new File(fc.getSelectedFile().getPath());
            this.dstDecompressPath.setText(destinationDecompress.getAbsolutePath());
        }
    }                                                   

    private void srcDecompressButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Compressed Files","cmprss");
        fc.setFileFilter(filtro);
        int select =  fc.showOpenDialog(this);
        if(select == JFileChooser.APPROVE_OPTION) {
            sourceDecompress = new File(fc.getSelectedFile().getPath());
            this.srcDecompressPath.setText(sourceDecompress.getAbsolutePath());
        }
    }                                                   

    private void runDecompressButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        if (sourceDecompress == null || destinationDecompress == null){
            message = new ErrorDialog(this,true);
            message.changeValueLabel2("Source or Destination path not selected propperly !");
            message.setVisible(true);
        }
        else{ 
            Decompressor cmp = new Decompressor(sourceDecompress,destinationDecompress);
             cmp.decompress();
            srcDecompressPath.setText("Source Path");
            dstDecompressPath.setText("Destination Path");
        }
    }
    
    public static void printCompressStadistics(String time, String ogsize, String cmpsize, String cmpratio) {
        ErrorDialog message = new ErrorDialog(new JFrame(),true);
        message.showResults(time,ogsize,cmpsize,cmpratio);
        message.setVisible(true);
    }

    public static void printDecompressStadistics(String time) {
        ErrorDialog message = new ErrorDialog(new JFrame(),true);
        message.changeValueLabel2(time);
        message.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel addText;
    private javax.swing.JLabel algText;
    private javax.swing.JPanel btnCompress;
    private javax.swing.JPanel btnDecompress;
    private javax.swing.JPanel btnInfo;
    private javax.swing.JLabel closeButton;
    private javax.swing.JLabel comText;
    private javax.swing.JPanel completePanel;
    private javax.swing.JPanel compressForm;
    private javax.swing.JLabel compressTitle;
    private javax.swing.JLabel decText;
    private javax.swing.JLabel decompressTitle;
    private javax.swing.JButton dstCompressButton;
    private javax.swing.JTextField dstCompressPath;
    private javax.swing.JButton dstDecompressButton;
    private javax.swing.JTextField dstDecompressPath;
    private javax.swing.JLabel dstText;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator menuSeparator;
    private javax.swing.JLabel propTitle;
    private javax.swing.JLabel qualityText;
    private javax.swing.JButton runCompressButton;
    private javax.swing.JButton runDecompressButton;
    private javax.swing.JComboBox<String> selectAlg;
    private javax.swing.JComboBox<String> selectQual;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JButton srcCompressButton;
    private javax.swing.JTextField srcCompressPath;
    private javax.swing.JButton srcDecompressButton;
    private javax.swing.JTextField srcDecompressPath;
    private javax.swing.JLabel srcText;
    private javax.swing.JLabel titleProj;
    private javax.swing.JPanel titulMode;
    private javax.swing.JPanel titulModeDec;
    // End of variables declaration
    public static final long serialVersionUID = 1L;

}
