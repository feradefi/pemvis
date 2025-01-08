/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tgspemvis;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author verra
 */
public class homeFixxx extends javax.swing.JFrame {
    
    private double saldo = 0;
    private double totalPendapatan = 0;
    private double totalPengeluaran = 0;
    private javax.swing.ButtonGroup tagihanStatusGroup;
    DefaultListModel<String> listModelTagihan = new DefaultListModel<>();
    private boolean isEditMode = false;
    private int editingRow = -1;
    private DefaultTableModel editingModel = null;
    

    /**
     * Creates new form home1
     */
    public homeFixxx() {
        initComponents();
        tabelTagihan.setDefaultRenderer(Object.class, new CustomRenderer());
         // Di dalam konstruktor atau metode inisialisasi setelah initComponents();
        tabelPemasukan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPemasukan2MouseClicked(evt);
            }
        });

        tabelPengeluaran2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPengeluaran2MouseClicked(evt);
            }
        });
        
                tabelTagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTagihanMouseClicked(evt);
            }
        });

        tabelBayarTagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBayarTagihanMouseClicked(evt);
            }
        });
        
        tagihanStatusGroup = new javax.swing.ButtonGroup();
        tagihanStatusGroup.add(belumDibayar);
        tagihanStatusGroup.add(sudahDibayar);
        listTagihan.setModel(listModelTagihan);
        listTagihan1.setModel(listModelTagihan);
         // Sync the home tables with the input tables on program start
        syncHomeTableWithData((DefaultTableModel) tabelPemasukan.getModel(), (DefaultTableModel) tabelPemasukan2.getModel());
        syncHomeTableWithData((DefaultTableModel) tabelPengeluaran.getModel(), (DefaultTableModel) tabelPengeluaran2.getModel());
          
    }
    
    public class CustomRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        // Call the parent class method to keep standard behavior
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        
        // Check if the tenggat date is past
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date tenggatDate = dateFormat.parse(table.getModel().getValueAt(row, 2).toString()); // Assuming column 2 is tenggat
            if (tenggatDate.before(new Date())) {
                // Change background color for overdue
                c.setBackground(Color.RED); 
                c.setForeground(Color.WHITE); 
            } else {
                // Standard colors for rows not meeting the condition
                if (!isSelected) {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c;
    }
}
    
  private void updateSaldo() {
        Saldo.setText(String.format("%.2f", saldo));
        Saldo1.setText(String.format("%.2f", saldo)); // Update saldo di tab pengaturan juga
        jumlahPendapatan.setText(String.format("%.2f", totalPendapatan));
        jumlahPendapatan1.setText(String.format("%.2f", totalPendapatan)); // Update pendapatan di tab pengaturan juga
        jumlahPengeluaran.setText(String.format("%.2f", totalPengeluaran));
        jumlahPengeluaran1.setText(String.format("%.2f", totalPengeluaran)); // Update pengeluaran di tab pengaturan juga
    }
    private void updateTableNumbers(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0); // Set the new row number
        }
    }
    private void syncHomeTableWithData(DefaultTableModel sourceModel, DefaultTableModel destinationModel) {
         destinationModel.setRowCount(0); // Clear the destination table first
        for (int i = 0; i < sourceModel.getRowCount(); i++) {
            // Copy each row from the source to the destination
            Object[] rowData = new Object[sourceModel.getColumnCount()];
            for (int j = 0; j < sourceModel.getColumnCount(); j++) {
                rowData[j] = sourceModel.getValueAt(i, j);
            }
            destinationModel.addRow(rowData);
        } }
    
    private void setEditMode(boolean isEdit, int row, DefaultTableModel model) {
        
        if (isEditMode != isEdit) {
        isEditMode = isEdit;
        editingRow = row;
        editingModel = model;
        simpan.setText(isEdit ? "Edit" : "Simpan"); // Mengubah teks tombol
        
         // Disable radio buttons during edit mode
    belumDibayar.setEnabled(!isEdit);
    sudahDibayar.setEnabled(!isEdit);
        // Nonaktifkan JComboBox
        pilihInputan.setEnabled(!isEdit);

        if (!isEdit) {
            // Reset form saat keluar dari mode edit
            resetForm();
        }
    }
    }

    private void resetForm() {
        inputNama.setText("");
        inputTanggal.setText("");
        InputNominal.setText("");
        inputKeterangan.setText("");
        pilihInputan.setSelectedIndex(0); // Reset the JComboBox to its default state
        setEditMode(false, -1, null); // Reset the edit mode
        simpan.setText("Simpan"); // Reset button text to "Simpan"
    }
    private void setTagihanEditMode(boolean isEdit, int row, DefaultTableModel model) {
    isEditMode = isEdit;
    editingRow = row;
    editingModel = model;
    simpanTagihan.setText(isEdit ? "Edit" : "Simpan");

    // Nonaktifkan tombol radio saat edit mode aktif
    belumDibayar.setEnabled(!isEdit);
    sudahDibayar.setEnabled(!isEdit);
    if (isEdit) {
            inputNamaTagihan.setText((String)model.getValueAt(row, 1));
            inputTenggatTagihan.setText((String)model.getValueAt(row, 2));
            InputNominalTagihan.setText(model.getValueAt(row, 3).toString());
            inputKeteranganTagihan.setText((String)model.getValueAt(row, 4));
            boolean status = ((String)model.getValueAt(row, 5)).equals("Sudah Dibayar");
            sudahDibayar.setSelected(status);
            belumDibayar.setSelected(!status);
        }
    }
    private void resetTagihan() {
        // Reset all input fields to default
        inputNamaTagihan.setText("");
        inputTenggatTagihan.setText("");
        InputNominalTagihan.setText("");
        inputKeteranganTagihan.setText("");
        tagihanStatusGroup.clearSelection(); // Pastikan ini mengacu pada ButtonGroup yang benar
        setTagihanEditMode(false, -1, null);
        simpanTagihan.setText("Simpan");
    }
    private void cekTenggatTagihan() {
    DefaultTableModel modelTagihan = (DefaultTableModel) tabelTagihan.getModel();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date currentDate = new Date();

    for (int i = 0; i < modelTagihan.getRowCount(); i++) {
        try {
            Date tenggatDate = dateFormat.parse(modelTagihan.getValueAt(i, 2).toString());
            if (tenggatDate.before(currentDate)) {
                // Tandai baris dengan warna merah
                String statusTagihan = modelTagihan.getValueAt(i, 5).toString();
                if (statusTagihan.equals("Belum Dibayar")) {
                    modelTagihan.setValueAt("Belum Dibayar", i, 5);
                } else {
                    modelTagihan.setValueAt("", i, 5);  // Remove the text for paid tagihan
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }}}
    private boolean checkForDuplicate(String namaTagihan, String tenggat) {
        DefaultTableModel modelTagihan = (DefaultTableModel) tabelTagihan.getModel();
        DefaultTableModel modelBayarTagihan = (DefaultTableModel) tabelBayarTagihan.getModel();

        for (int i = 0; i < modelTagihan.getRowCount(); i++) {
            if (modelTagihan.getValueAt(i, 1).equals(namaTagihan) && modelTagihan.getValueAt(i, 2).equals(tenggat)) {
                return true;
            }
        }
        for (int i = 0; i < modelBayarTagihan.getRowCount(); i++) {
            if (modelBayarTagihan.getValueAt(i, 1).equals(namaTagihan) && modelBayarTagihan.getValueAt(i, 2).equals(tenggat)) {
                return true;
            }
        }
        return false;
    }
    
private void updateHomePengeluaranTable(DefaultTableModel model) {
    DefaultTableModel modelHome = (DefaultTableModel) tabelPengeluaran2.getModel();

    // Clear existing data in the home table
    modelHome.setRowCount(0);

    // Add all rows from the input table to the home table
    for (int i = 0; i < model.getRowCount(); i++) {
        modelHome.addRow(new Object[]{
            model.getValueAt(i, 0),
            model.getValueAt(i, 1),
            model.getValueAt(i, 2),
            model.getValueAt(i, 3),
            model.getValueAt(i, 4)
        });
    }

    // Update total pengeluaran after updating the home table
    updateSaldo();
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPengaturan = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        Saldo = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jumlahPendapatan = new javax.swing.JTextField();
        jumlahPengeluaran = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listTagihan = new javax.swing.JList<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tabelPemasukan2 = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        detailDataTabel = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tabelPengeluaran2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        InputNominal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputKeterangan = new javax.swing.JTextArea();
        resetForm = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        pilihInputan = new javax.swing.JComboBox<>();
        inputNama = new javax.swing.JTextField();
        inputTanggal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        HapusPemasukan = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelPengeluaran = new javax.swing.JTable();
        HapusPengeluaran = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelPemasukan = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        InputNominalTagihan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        inputKeteranganTagihan = new javax.swing.JTextArea();
        resetFormTagihan = new javax.swing.JButton();
        simpanTagihan = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        belumDibayar = new javax.swing.JRadioButton();
        sudahDibayar = new javax.swing.JRadioButton();
        inputNamaTagihan = new javax.swing.JTextField();
        inputTenggatTagihan = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        TADetailTagihan = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        HapusDataTagihan1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelTagihan = new javax.swing.JTable();
        HapusDataTagihan = new javax.swing.JButton();
        bayarTagihan = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabelBayarTagihan = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        namaAkun = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        passwordAkun = new javax.swing.JTextField();
        ubahNamaPassword = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        Saldo1 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jumlahPendapatan1 = new javax.swing.JTextField();
        jumlahPengeluaran1 = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        listTagihan1 = new javax.swing.JList<>();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        resetSemua = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabPengaturan.setBackground(new java.awt.Color(0, 51, 51));
        tabPengaturan.setForeground(new java.awt.Color(255, 255, 0));
        tabPengaturan.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabPengaturan.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        tabPengaturan.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(0, 51, 51));

        jLabel27.setFont(new java.awt.Font("Constantia", 3, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 102));
        jLabel27.setText(" Halaman Home");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(0, 51, 51));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 255, 255));
        jLabel17.setText("Saldo Anda (Rp):");

        Saldo.setEditable(false);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 255, 255));
        jLabel23.setText("Data Ringkasan Keuangan Anda");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 255, 255));
        jLabel24.setText("Jumlah Pendapatan(Rp) :");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 255, 255));
        jLabel25.setText("Jumlah Pengeluaran(Rp):");

        jumlahPendapatan.setEditable(false);

        jumlahPengeluaran.setEditable(false);

        listTagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listTagihanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listTagihan);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 255, 255));
        jLabel26.setText("List Tagihan Harus Dibayar");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jumlahPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlahPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23)
                        .addGap(40, 40, 40)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(Saldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jumlahPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jumlahPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Detail Data ");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Tabel Data Pemasukan");

        tabelPemasukan2.setBackground(new java.awt.Color(204, 255, 204));
        tabelPemasukan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"
            }
        ));
        tabelPemasukan2.setShowGrid(true);
        tabelPemasukan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPemasukan2MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tabelPemasukan2);

        detailDataTabel.setEditable(false);
        detailDataTabel.setBackground(new java.awt.Color(186, 220, 255));
        detailDataTabel.setColumns(20);
        detailDataTabel.setLineWrap(true);
        detailDataTabel.setRows(5);
        jScrollPane12.setViewportView(detailDataTabel);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Tabel Data Pengeluaran");

        tabelPengeluaran2.setBackground(new java.awt.Color(255, 255, 204));
        tabelPengeluaran2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"
            }
        ));
        tabelPengeluaran2.setShowGrid(true);
        tabelPengeluaran2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPengeluaran2MouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tabelPengeluaran2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addGap(203, 203, 203))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabPengaturan.addTab("Home", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(0, 51, 51));

        jLabel28.setFont(new java.awt.Font("Constantia", 3, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 102));
        jLabel28.setText(" Halaman Atur Data Keuangan (Pemasukan & Pengeluaran)");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addContainerGap(278, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("Nama Data");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setText("Tanggal (hh/bb/tttt)      :");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 255, 255));
        jLabel15.setText("Nominal (Rp.)");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 255, 255));
        jLabel4.setText("Input Keterangan :");

        inputKeterangan.setColumns(20);
        inputKeterangan.setRows(5);
        jScrollPane1.setViewportView(inputKeterangan);

        resetForm.setBackground(new java.awt.Color(255, 255, 153));
        resetForm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        resetForm.setText("Reset Form");
        resetForm.setToolTipText("Klik untuk Hapus semua data di inputan");
        resetForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFormActionPerformed(evt);
            }
        });

        simpan.setBackground(new java.awt.Color(153, 204, 255));
        simpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        simpan.setText("Simpan");
        simpan.setToolTipText("Klik Untuk Simpan Data");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 255, 255));
        jLabel16.setText("Pilih Inputan");

        pilihInputan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Input Pemasukan", "Input Pengeluaran" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 255, 255));
        jLabel11.setText("Form Atur Data");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(inputNama))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pilihInputan, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(70, 70, 70))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resetForm))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(InputNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(inputTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(pilihInputan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetForm)
                    .addComponent(simpan))
                .addGap(27, 27, 27))
        );

        HapusPemasukan.setBackground(new java.awt.Color(153, 0, 0));
        HapusPemasukan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HapusPemasukan.setForeground(new java.awt.Color(255, 255, 204));
        HapusPemasukan.setText("Hapus");
        HapusPemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusPemasukanActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Tabel Data Pengeluaran");

        tabelPengeluaran.setBackground(new java.awt.Color(255, 255, 204));
        tabelPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"
            }
        ));
        tabelPengeluaran.setShowGrid(true);
        tabelPengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPengeluaranMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelPengeluaran);

        HapusPengeluaran.setBackground(new java.awt.Color(153, 0, 0));
        HapusPengeluaran.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HapusPengeluaran.setForeground(new java.awt.Color(255, 255, 255));
        HapusPengeluaran.setText("Hapus");
        HapusPengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusPengeluaranActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tabel Data Pemasukan");

        tabelPemasukan.setBackground(new java.awt.Color(204, 255, 204));
        tabelPemasukan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"
            }
        ));
        tabelPemasukan.setShowGrid(true);
        tabelPemasukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPemasukanMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelPemasukan);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(HapusPemasukan))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(HapusPengeluaran))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HapusPemasukan)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HapusPengeluaran)))
                .addGap(0, 135, Short.MAX_VALUE))
        );

        tabPengaturan.addTab("Atur Data Keuangan", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(0, 51, 51));

        jLabel29.setFont(new java.awt.Font("Constantia", 3, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 102));
        jLabel29.setText(" Halaman Kelola Tagihan");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 255, 255));
        jLabel5.setText("Nama Tagihan:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 255, 255));
        jLabel6.setText("Tanggal Tenggat (hh/bb/tttt)      :");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(204, 255, 255));
        jLabel32.setText("Nominal (Rp.)");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 255, 255));
        jLabel7.setText("Input Keterangan :");

        inputKeteranganTagihan.setBackground(new java.awt.Color(204, 255, 255));
        inputKeteranganTagihan.setColumns(20);
        inputKeteranganTagihan.setRows(5);
        jScrollPane3.setViewportView(inputKeteranganTagihan);

        resetFormTagihan.setBackground(new java.awt.Color(255, 255, 153));
        resetFormTagihan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        resetFormTagihan.setText("Reset Form");
        resetFormTagihan.setToolTipText("Klik untuk Hapus semua data di inputan");
        resetFormTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFormTagihanActionPerformed(evt);
            }
        });

        simpanTagihan.setBackground(new java.awt.Color(204, 255, 255));
        simpanTagihan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        simpanTagihan.setText("Simpan");
        simpanTagihan.setToolTipText("Klik Untuk Simpan Data");
        simpanTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanTagihanActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 255, 255));
        jLabel10.setText("Form Tagihan");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 255, 255));
        jLabel12.setText("Status Tagihan");

        belumDibayar.setForeground(new java.awt.Color(204, 255, 255));
        belumDibayar.setText("Belum Dibayar");

        sudahDibayar.setForeground(new java.awt.Color(204, 255, 255));
        sudahDibayar.setText("Sudah Dibayar");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel7))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel6)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(belumDibayar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sudahDibayar)
                .addGap(25, 25, 25))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(simpanTagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetFormTagihan))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputNamaTagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel12))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InputNominalTagihan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inputTenggatTagihan))))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputNamaTagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputTenggatTagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputNominalTagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(belumDibayar)
                    .addComponent(sudahDibayar))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetFormTagihan)
                    .addComponent(simpanTagihan))
                .addGap(24, 24, 24))
        );

        TADetailTagihan.setEditable(false);
        TADetailTagihan.setColumns(20);
        TADetailTagihan.setRows(5);
        jScrollPane11.setViewportView(TADetailTagihan);

        jLabel13.setText("Detail Tagihan");

        HapusDataTagihan1.setBackground(new java.awt.Color(102, 0, 0));
        HapusDataTagihan1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HapusDataTagihan1.setForeground(new java.awt.Color(255, 255, 153));
        HapusDataTagihan1.setText("Hapus");
        HapusDataTagihan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusDataTagihan1ActionPerformed(evt);
            }
        });

        tabelTagihan.setBackground(new java.awt.Color(204, 255, 255));
        tabelTagihan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Tagihan", "Tenggat", "Nominal (Rp)", "Keterangan", "Status"
            }
        ));
        tabelTagihan.setShowGrid(true);
        tabelTagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTagihanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelTagihan);

        HapusDataTagihan.setBackground(new java.awt.Color(102, 0, 0));
        HapusDataTagihan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HapusDataTagihan.setForeground(new java.awt.Color(255, 255, 153));
        HapusDataTagihan.setText("Hapus");
        HapusDataTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusDataTagihanActionPerformed(evt);
            }
        });

        bayarTagihan.setBackground(new java.awt.Color(204, 255, 204));
        bayarTagihan.setText("Bayar Tagihan");
        bayarTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarTagihanActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Tabel Data Tagihan yang sudah dibayar");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Tabel Data Tagihan");

        tabelBayarTagihan.setBackground(new java.awt.Color(204, 255, 255));
        tabelBayarTagihan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Tagihan", "Tenggat", "Nominal (Rp)", "Keterangan", "Status"
            }
        ));
        tabelBayarTagihan.setShowGrid(true);
        tabelBayarTagihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBayarTagihanMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tabelBayarTagihan);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addGap(168, 168, 168))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(HapusDataTagihan1)
                                        .addGap(15, 15, 15))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(bayarTagihan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(HapusDataTagihan))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(12, 12, 12))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33)
                        .addGap(109, 109, 109))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bayarTagihan)
                            .addComponent(HapusDataTagihan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HapusDataTagihan1)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 76, Short.MAX_VALUE))
        );

        tabPengaturan.addTab("Kelola Tagihan", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(0, 51, 51));

        jLabel30.setFont(new java.awt.Font("Constantia", 3, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 102));
        jLabel30.setText(" Halaman Pengaturan");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(221, 255, 255));

        jLabel19.setFont(new java.awt.Font("Segoe UI Black", 3, 12)); // NOI18N
        jLabel19.setText("Laman Profil");

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tugaspemvisfr/gambar/profil.png"))); // NOI18N

        jLabel39.setText("Nama Anda :");

        jLabel40.setText("Password Anda :");

        ubahNamaPassword.setBackground(new java.awt.Color(255, 255, 153));
        ubahNamaPassword.setText("Ubah Nama Dan Password");
        ubahNamaPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahNamaPasswordActionPerformed(evt);
            }
        });

        jPanel17.setBackground(new java.awt.Color(0, 51, 51));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(204, 255, 255));
        jLabel41.setText("Saldo Anda:");

        Saldo1.setEditable(false);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 255, 255));
        jLabel42.setText("Jumlah Pendapatan :");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(204, 255, 255));
        jLabel43.setText("Jumlah Pengeluaran:");

        jumlahPendapatan1.setEditable(false);

        jumlahPengeluaran1.setEditable(false);

        jScrollPane14.setViewportView(listTagihan1);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(204, 255, 255));
        jLabel44.setText("List Tagihan Harus Dibayar");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(204, 255, 255));
        jLabel45.setText("Data Ringkasan Keuangan Anda");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(86, 86, 86))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel42)
                            .addComponent(jLabel43))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jumlahPengeluaran1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlahPendapatan1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Saldo1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(Saldo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jumlahPendapatan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jumlahPengeluaran1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(12, 12, 12)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(namaAkun, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(passwordAkun)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(ubahNamaPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel38)))
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel38)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(namaAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(passwordAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addComponent(ubahNamaPassword))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        resetSemua.setBackground(new java.awt.Color(204, 0, 0));
        resetSemua.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        resetSemua.setForeground(new java.awt.Color(255, 255, 102));
        resetSemua.setText("Reset Semua Data");
        resetSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSemuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(resetSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(resetSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 152, Short.MAX_VALUE))
        );

        tabPengaturan.addTab("Pengaturan", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPengaturan)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelPemasukan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPemasukan2MouseClicked
        // Dapatkan model tabel dan nomor baris yang dipilih
        int row = tabelPemasukan2.getSelectedRow();
        if (row == -1) {
        // Tidak ada baris yang dipilih
        return;
    }
        DefaultTableModel model = (DefaultTableModel) tabelPemasukan2.getModel();

        // Dapatkan data dari baris yang dipilih
        String no = model.getValueAt(row, 0).toString();
        String namaData = model.getValueAt(row, 1).toString();
        String tanggal = model.getValueAt(row, 2).toString();
        String nominal = model.getValueAt(row, 3).toString();
        String keteranganData = model.getValueAt(row, 4).toString();

        // Atur teks untuk detailDataTabel
        detailDataTabel.setText(
            "Data Pemasukan\n" +
            "No. : " + no + "\n" +
            "Nama Data : " + namaData + "\n" +
            "Tanggal : " + tanggal + "\n" +
            "Nominal : " + nominal + "\n" +
            "Keterangan Data : " + keteranganData
        );
        
    }//GEN-LAST:event_tabelPemasukan2MouseClicked

    private void tabelPengeluaran2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPengeluaran2MouseClicked
    // Dapatkan model tabel dan nomor baris yang dipilih
    int row = tabelPengeluaran2.getSelectedRow();
    if (row == -1) {
        // Tidak ada baris yang dipilih
        return;
    }
    DefaultTableModel model = (DefaultTableModel) tabelPengeluaran2.getModel();

    // Dapatkan data dari baris yang dipilih
    String no = model.getValueAt(row, 0).toString();
    String namaData = model.getValueAt(row, 1).toString();
    String tanggal = model.getValueAt(row, 2).toString();
    String nominal = model.getValueAt(row, 3).toString();
    String keteranganData = model.getValueAt(row, 4).toString();

    // Atur teks untuk detailDataTabel
    detailDataTabel.setText(
        "Data Pengeluaran\n" +
        "No. : " + no + "\n" +
        "Nama Data : " + namaData + "\n" +
        "Tanggal : " + tanggal + "\n" +
        "Nominal : " + nominal + "\n" +
        "Keterangan Data : " + keteranganData
    );

    }//GEN-LAST:event_tabelPengeluaran2MouseClicked

    private void resetFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFormActionPerformed
        resetForm();
    }//GEN-LAST:event_resetFormActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
    String nama = inputNama.getText();
    String tanggalText = inputTanggal.getText();
    String nominal = InputNominal.getText();
    String keterangan = inputKeterangan.getText();
    String pilihanInputan = pilihInputan.getSelectedItem().toString();

    if (nama.isEmpty() || tanggalText.isEmpty() || nominal.isEmpty() || keterangan.isEmpty() || pilihanInputan.equals("Pilih")) {
        JOptionPane.showMessageDialog(this, "Lengkapi semua form");
        return;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    Date inputDate;
    try {
        inputDate = dateFormat.parse(tanggalText);
        Date currentDate = new Date();
        if (inputDate.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Tanggal tidak boleh sebelum hari ini");
            return;
        }
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(this, "Format tanggal harus dd/MM/yyyy");
        return;
    }
    
    // Parse the nominal to a number and handle any parsing errors
    double nominalValue;
    try {
        nominalValue = Double.parseDouble(nominal);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Nominal harus berupa angka");
        return;
    }

    double updatedSaldo = pilihanInputan.equals("Input Pemasukan") ? saldo + nominalValue : saldo - nominalValue;
    if (updatedSaldo < 0) {
        JOptionPane.showMessageDialog(this, "Saldo Anda tidak mencukupi untuk transaksi ini.", "Saldo Tidak Mencukupi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Check if in edit mode and update existing row
    if (isEditMode && editingModel != null && editingRow != -1) {
        // Update the existing row
        double nominalLama = Double.parseDouble(editingModel.getValueAt(editingRow, 3).toString().replace(",", "."));
        
        // Hitung saldo sementara setelah edit
        double saldoSementara = pilihanInputan.equals("Input Pemasukan") 
            ? saldo - nominalLama + nominalValue 
            : saldo;

        // Cek jika saldo sementara menjadi negatif
        if (saldoSementara < 0) {
            JOptionPane.showMessageDialog(this, "Data tidak bisa diedit karena mengakibatkan saldo minus", "Edit Gagal", JOptionPane.ERROR_MESSAGE);
            return; // Jangan lanjutkan proses edit
        }
        
        editingModel.setValueAt(nama, editingRow, 1);
        editingModel.setValueAt(tanggalText, editingRow, 2);
        editingModel.setValueAt(String.format("%.2f", nominalValue), editingRow, 3);
        editingModel.setValueAt(keterangan, editingRow, 4);
      
        // Calculate the difference
        double perbedaan = nominalValue - nominalLama;

        // Update global variables
        if (pilihanInputan.equals("Input Pemasukan")) {
            // Adjust the total pendapatan and saldo
            totalPendapatan += perbedaan;
            saldo += perbedaan;
        } else if (pilihanInputan.equals("Input Pengeluaran")) {
            // Update pengeluaran and saldo
            totalPengeluaran += perbedaan;
            saldo -= perbedaan;
        }
        
        // Sync the home table with the updated input table
        if (pilihanInputan.equals("Input Pemasukan")) {
            syncHomeTableWithData((DefaultTableModel) tabelPemasukan.getModel(), (DefaultTableModel) tabelPemasukan2.getModel());
        } else if (pilihanInputan.equals("Input Pengeluaran")) {
            syncHomeTableWithData((DefaultTableModel) tabelPengeluaran.getModel(), (DefaultTableModel) tabelPengeluaran2.getModel());
        }

        updateSaldo();
        resetForm(); // Reset the form after editing
    } else {
        DefaultTableModel modelInput;
        DefaultTableModel modelHome;
        
        if (pilihanInputan.equals("Input Pemasukan")) {
            modelInput = (DefaultTableModel) tabelPemasukan.getModel();
            modelHome = (DefaultTableModel) tabelPemasukan2.getModel();
        } else if (pilihanInputan.equals("Input Pengeluaran")) {
            modelInput = (DefaultTableModel) tabelPengeluaran.getModel();
            modelHome = (DefaultTableModel) tabelPengeluaran2.getModel();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih jenis inputan yang benar");
            return;
        }
        
        // Add the new row to the input table
        Object[] rowData = {modelInput.getRowCount() + 1, nama, tanggalText, String.format("%.2f", nominalValue), keterangan};
        modelInput.addRow(rowData);
        
        // Add the new row to the home table
        modelHome.addRow(rowData);

        // Sync the home table with the updated input table
        if (pilihanInputan.equals("Input Pemasukan")) {
            syncHomeTableWithData((DefaultTableModel) tabelPemasukan.getModel(), (DefaultTableModel) tabelPemasukan2.getModel());
        } else if (pilihanInputan.equals("Input Pengeluaran")) {
            syncHomeTableWithData((DefaultTableModel) tabelPengeluaran.getModel(), (DefaultTableModel) tabelPengeluaran2.getModel());
        }

           // Update the global variables based on the input type
        if (pilihanInputan.equals("Input Pemasukan")) {
            totalPendapatan += nominalValue;
            saldo += nominalValue;
        } else {
            totalPengeluaran += nominalValue;
            saldo -= nominalValue;
        }

        // Update the global saldo with the new value
        updateSaldo();
        resetForm();
    }

    }//GEN-LAST:event_simpanActionPerformed

    private void HapusPemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusPemasukanActionPerformed
        int selectedRow = tabelPemasukan.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel modelInput = (DefaultTableModel) tabelPemasukan.getModel();
            DefaultTableModel modelHome = (DefaultTableModel) tabelPemasukan2.getModel();

            // Get data to remove
            String namaData = modelInput.getValueAt(selectedRow, 1).toString();
            String tanggal = modelInput.getValueAt(selectedRow, 2).toString();
            String nominal = modelInput.getValueAt(selectedRow, 3).toString();
            String keterangan = modelInput.getValueAt(selectedRow, 4).toString();
            double nominalValue = Double.parseDouble(modelInput.getValueAt(selectedRow, 3).toString());
            double tempSaldo = saldo - nominalValue; // Periksa saldo sementara setelah penghapusan
            
            
            if (tempSaldo < 0) {
                // Jika saldo sementara menjadi negatif, tampilkan pesan peringatan dan jangan hapus data
                JOptionPane.showMessageDialog(this, "Data tidak bisa dihapus karena mengakibatkan saldo minus", "Saldo Tidak Mencukupi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus data: " + namaData
                + "\n tanggal : " + tanggal
                + "\n dengan nominal : " + nominal
                + "\n Keterangan : " + keterangan,"Konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Salin data sebelum dihapus dari modelCurrent
                modelInput.removeRow(selectedRow);
                updateTableNumbers(modelInput);

                // Remove from home table
                for (int i = 0; i < modelHome.getRowCount(); i++) {
                if (modelHome.getValueAt(i, 1).toString().equals(namaData) &&
                    modelHome.getValueAt(i, 2).toString().equals(tanggal) &&
                    modelHome.getValueAt(i, 3).toString().equals(nominal) &&
                    modelHome.getValueAt(i, 4).toString().equals(keterangan)) {
                    modelHome.removeRow(i);
                    break;
                }
            
                    updateTableNumbers(modelHome); // Update nomor baris setelah penghapusan
                    
                    // Update the global saldo
                    saldo -= nominalValue;
                    totalPendapatan -= nominalValue;

                    updateSaldo();
                    resetForm();
                     JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                    }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih satu data yang akan dihapus");
            }
        }
        
    }//GEN-LAST:event_HapusPemasukanActionPerformed

    private void tabelPengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPengeluaranMouseClicked
          int row = tabelPengeluaran.getSelectedRow();
    if (row >= 0) {
        // Ambil data dari tabel pengeluaran
        String namaData = tabelPengeluaran.getValueAt(row, 1).toString();
        String tanggal = tabelPengeluaran.getValueAt(row, 2).toString();
        String nominal = tabelPengeluaran.getValueAt(row, 3).toString();
        String keterangan = tabelPengeluaran.getValueAt(row, 4).toString();
        
        // Set edit mode dan isi kembali form dengan data yang ada
        setEditMode(true, row, (DefaultTableModel) tabelPengeluaran.getModel());
        inputNama.setText(namaData);
        inputTanggal.setText(tanggal);
        InputNominal.setText(nominal);
        inputKeterangan.setText(keterangan);
        pilihInputan.setSelectedItem("Input Pengeluaran");
        
    }
    }//GEN-LAST:event_tabelPengeluaranMouseClicked

    private void HapusPengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusPengeluaranActionPerformed
        int selectedRow = tabelPengeluaran.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel modelInput = (DefaultTableModel) tabelPengeluaran.getModel();
            DefaultTableModel modelHome = (DefaultTableModel) tabelPengeluaran2.getModel();

            // Get data to remove
            String namaData = modelInput.getValueAt(selectedRow, 1).toString();
            String tanggal = modelInput.getValueAt(selectedRow, 2).toString();
            String nominal = modelInput.getValueAt(selectedRow, 3).toString();
            String keterangan = modelInput.getValueAt(selectedRow, 4).toString();
            double nominalValue = Double.parseDouble(modelInput.getValueAt(selectedRow, 3).toString());

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus data: " + namaData
                + "\n tanggal : " + tanggal
                + "\n dengan nominal : " + nominal
                + "\n Keterangan : " + keterangan,"Konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Remove from input table
                modelInput.removeRow(selectedRow);
                updateTableNumbers(modelInput); // Update row numbers for the input table

                // Remove from home table
                for (int i = 0; i < modelHome.getRowCount(); i++) {
                    if (modelHome.getValueAt(i, 1).toString().equals(namaData) &&
                        modelHome.getValueAt(i, 2).toString().equals(tanggal) &&
                        modelHome.getValueAt(i, 3).toString().equals(nominal)&&
                        modelHome.getValueAt(i, 4).toString().equals(keterangan)) {
                        modelHome.removeRow(i);
                        updateTableNumbers(modelHome); // Update row numbers for the home table
                        break;
                    }
                }

                // Update the global saldo and pengeluaran
                saldo += nominalValue;
                totalPengeluaran -= nominalValue;
                updateSaldo();
                resetForm();
             JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih satu data yang akan dihapus");
        }
    }//GEN-LAST:event_HapusPengeluaranActionPerformed

    private void tabelPemasukanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPemasukanMouseClicked
        // Dapatkan model tabel dan nomor baris yang dipilih
        int row = tabelPemasukan.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tabelPemasukan.getModel();

        // Dapatkan data dari baris yang dipilih
        String no = model.getValueAt(row, 0).toString();
        String namaData = model.getValueAt(row, 1).toString();
        String tanggal = model.getValueAt(row, 2).toString();
        String nominal = model.getValueAt(row, 3).toString();
        String keteranganData = model.getValueAt(row, 4).toString();
        
        setEditMode(true, row, (DefaultTableModel) tabelPemasukan.getModel());
        // Populate form fields with selected data
        inputNama.setText(namaData);
        inputTanggal.setText(tanggal);
        InputNominal.setText(nominal);
        inputKeterangan.setText(keteranganData);
        pilihInputan.setSelectedItem("Input Pemasukan"); // or "Input Pengeluaran" based on which table is clicked
    
    }//GEN-LAST:event_tabelPemasukanMouseClicked

    private void resetFormTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFormTagihanActionPerformed
        resetTagihan();
    }//GEN-LAST:event_resetFormTagihanActionPerformed

    private void simpanTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanTagihanActionPerformed
   String namaTagihan = inputNamaTagihan.getText();
    String tenggat = inputTenggatTagihan.getText();
    String nominalTagihan = InputNominalTagihan.getText();
    String keteranganTagihan = inputKeteranganTagihan.getText();
    boolean statusBayarBaru = sudahDibayar.isSelected();

    // Validasi Form
    if (namaTagihan.isEmpty() || tenggat.isEmpty() || nominalTagihan.isEmpty() || keteranganTagihan.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Lengkapi semua form");
        return;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    Date inputDate;
    try {
        inputDate = dateFormat.parse(tenggat);
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(this, "Format tanggal harus dd/MM/yyyy");
        return;
    }

    double nominalValue;
    try {
        nominalValue = Double.parseDouble(nominalTagihan);
        if (nominalValue < 0) {
            JOptionPane.showMessageDialog(this, "Nominal tidak boleh negatif");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Nominal harus berupa angka");
        return;
    }
    boolean isTenggatTerlewat = inputDate.before(new Date());

   if (isEditMode && editingModel != null && editingRow != -1) {
        // Update data di tabel belum dibayar
        editingModel.setValueAt(namaTagihan, editingRow, 1);
        editingModel.setValueAt(tenggat, editingRow, 2);
        editingModel.setValueAt(String.format("%.2f", nominalValue), editingRow, 3);
        editingModel.setValueAt(keteranganTagihan, editingRow, 4);
        editingModel.setValueAt(statusBayarBaru ? "Sudah Dibayar" : "Belum Dibayar", editingRow, 5);

   // Perbarui data dalam list model tagihan jika belum dibayar
        if (!statusBayarBaru) {
            String tagihanText = namaTagihan + " - " + String.format("%.2f", nominalValue) + " - " + tenggat;
            int listIndex = listModelTagihan.indexOf(tagihanText);
            if (listIndex != -1) {
                listModelTagihan.setElementAt(tagihanText + (isTenggatTerlewat ? "\n !!!TENGGAT TAGIHAN TERLEWAT!!!" : ""), listIndex);
            }
        }

        // Reset form
        setEditMode(false, -1, null);
        resetTagihan();
    } else {
        // Tambahkan data baru
         DefaultTableModel modelTagihan = (DefaultTableModel) (statusBayarBaru ? tabelBayarTagihan.getModel() : tabelTagihan.getModel());
    
        // Check for duplicate entries
        if (checkForDuplicate(namaTagihan, tenggat)) {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Data yang anda masukkan sudah ada. Yakin ingin menambahkan lagi?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (dialogResult != JOptionPane.YES_OPTION) {
                return;
            }
        }
        // Determine the keterangan text based on the radio button selection
        String keterangan = keteranganTagihan;
        if (!statusBayarBaru && isTenggatTerlewat) {
            keterangan += " \n !!!TENGGAT TAGIHAN TERLEWAT!!!";
        }
         // Tambahkan ke model tabel
    Object[] rowData = new Object[]{
        modelTagihan.getRowCount() + 1,
        namaTagihan,
        tenggat,
        String.format("%.2f", nominalValue),
        keterangan, // Keep the original keterangan value
        statusBayarBaru ? "Sudah Dibayar" : "Belum Dibayar"
    };
    modelTagihan.addRow(rowData);
    
   // Tambahkan ke list tagihan jika belum dibayar
     if (!statusBayarBaru && !isEditMode) {
            String tagihanText = namaTagihan + " - " + String.format("%.2f", nominalValue) + " - " + tenggat;
            if (listModelTagihan.indexOf(tagihanText) == -1) {
                listModelTagihan.addElement(tagihanText + (isTenggatTerlewat ? "\n !!!TENGGAT TAGIHAN TERLEWAT!!!" : ""));
            }
            // Reset form
            resetTagihan();
        }
    }
    // Reset form
    resetTagihan();
    // Cek tenggat tagihan untuk update UI
    cekTenggatTagihan();
    }//GEN-LAST:event_simpanTagihanActionPerformed

    private void HapusDataTagihan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusDataTagihan1ActionPerformed
    int selectedRow = tabelBayarTagihan.getSelectedRow();
    if (selectedRow >= 0) {
        DefaultTableModel modelTagihan = (DefaultTableModel) tabelBayarTagihan.getModel();
        DefaultTableModel modelPengeluaran = (DefaultTableModel) tabelPengeluaran2.getModel();

        // Retrieve the linking attribute(s) from the selected row
        String namaTagihan = modelTagihan.getValueAt(selectedRow, 1).toString();
        String tanggalTagihan = modelTagihan.getValueAt(selectedRow, 2).toString();

        // Show confirmation dialog before removing the data
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus data: " + namaTagihan
            + "\n tanggal Tenggat : " + tanggalTagihan, 
            "Konfirmasi Hapus Data", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Remove the entry from the "Pembayaran Tagihan" table
            modelTagihan.removeRow(selectedRow);

            // Iterate through the "Pengeluaran" table to find and remove the corresponding entry
            for (int i = 0; i < modelPengeluaran.getRowCount(); i++) {
                String namaPengeluaran = modelPengeluaran.getValueAt(i, 1).toString();
                String tanggalPengeluaran = modelPengeluaran.getValueAt(i, 2).toString();

                if (namaTagihan.equals(namaPengeluaran) && tanggalTagihan.equals(tanggalPengeluaran)) {
                    modelPengeluaran.removeRow(i);
                    break; // Assuming there's only one matching entry
                }
            }

            // Update global variables and GUI components
            updateSaldo();
            updateTableNumbers(modelTagihan);
            updateTableNumbers(modelPengeluaran);

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Pilih satu data yang akan dihapus");
    }                                              
    }//GEN-LAST:event_HapusDataTagihan1ActionPerformed

    private void tabelTagihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTagihanMouseClicked
        int row = tabelTagihan.getSelectedRow();
    if (row >= 0) {
        String namaTagihan = tabelTagihan.getValueAt(row, 1).toString();
        String tenggat = tabelTagihan.getValueAt(row, 2).toString();
        String nominal = tabelTagihan.getValueAt(row, 3).toString();
        String keterangan = tabelTagihan.getValueAt(row, 4).toString();
        String status = tabelTagihan.getValueAt(row, 5).toString();

        TADetailTagihan.setText(
            "Nama Tagihan: " + namaTagihan + "\n" +
            "Tenggat: " + tenggat + "\n" +
            "Nominal: " + nominal + "\n" +
            "Keterangan: " + keterangan + "\n"+
             "Status: " + status + "\n"
        );
    }

    if (row >= 0) {
        // Populate form fields with selected data
        inputNamaTagihan.setText(tabelTagihan.getValueAt(row, 1).toString());
        inputTenggatTagihan.setText(tabelTagihan.getValueAt(row, 2).toString());
        InputNominalTagihan.setText(tabelTagihan.getValueAt(row, 3).toString());
        inputKeteranganTagihan.setText(tabelTagihan.getValueAt(row, 4).toString());
        String status = tabelTagihan.getValueAt(row, 5).toString();
        belumDibayar.setSelected(status.equals("BELUM DIBAYAR"));
        sudahDibayar.setSelected(status.equals("SUDAH DIBAYAR"));

        // Set form to edit mode for tagihan
        setTagihanEditMode(true, row, (DefaultTableModel) tabelTagihan.getModel());
    }
    }//GEN-LAST:event_tabelTagihanMouseClicked

    private void HapusDataTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusDataTagihanActionPerformed
    int selectedRow = tabelTagihan.getSelectedRow();
    if (selectedRow >= 0) {
        DefaultTableModel modelInput = (DefaultTableModel) tabelTagihan.getModel();

        // Get data to remove
        String namaData = modelInput.getValueAt(selectedRow, 1).toString();
        String tenggat = modelInput.getValueAt(selectedRow, 2).toString();
        String nominal = modelInput.getValueAt(selectedRow, 3).toString();
        String keterangan = modelInput.getValueAt(selectedRow, 4).toString();
        String status = modelInput.getValueAt(selectedRow, 5).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus data: " + namaData
                + "\n tanggal Tenggat : " + tenggat
                + "\n dengan nominal : " + nominal
                + "\n Keterangan : " + keterangan
                + "\n Status : " + status, "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Remove from input table
            modelInput.removeRow(selectedRow);
            updateTableNumbers(modelInput); // Update row numbers for the input table

            // Remove the corresponding item from the listModelTagihan
            listModelTagihan.remove(selectedRow);

            resetTagihan();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Pilih satu data yang akan dihapus");
    }
                     
    }//GEN-LAST:event_HapusDataTagihanActionPerformed

    private void bayarTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarTagihanActionPerformed
         int selectedRow = tabelTagihan.getSelectedRow();
    if (selectedRow >= 0)  {
        DefaultTableModel unpaidModel = (DefaultTableModel) tabelTagihan.getModel();
        DefaultTableModel paidModel = (DefaultTableModel) tabelBayarTagihan.getModel();
        DefaultTableModel pengeluaranModel = (DefaultTableModel) tabelPengeluaran2.getModel();

        // Extract information from the selected row in tabelTagihan
        String namaTagihan = unpaidModel.getValueAt(selectedRow, 1).toString();
        String tenggat = unpaidModel.getValueAt(selectedRow, 2).toString();
        String nominalStr = unpaidModel.getValueAt(selectedRow, 3).toString();
        String keterangan = unpaidModel.getValueAt(selectedRow, 4).toString();

        // Check if saldo is sufficient
        double nominal = Double.parseDouble(nominalStr);
        if (saldo < nominal) {
            JOptionPane.showMessageDialog(this, "Saldo tidak mencukupi untuk membayar tagihan.", "Saldo Kurang", JOptionPane.WARNING_MESSAGE);
            return; // Don't proceed if saldo is not sufficient
        }

        // Confirm the payment with a JOptionPane
        int option = JOptionPane.showConfirmDialog(this, "Bayar tagihan?\n\nNama Tagihan: " + namaTagihan + "\nTenggat: " + tenggat + "\nNominal: " + nominalStr + "\nKeterangan: " + keterangan, "Konfirmasi Pembayaran", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Reduce saldo
            saldo -= nominal;
            updateSaldo();

            // Add data to tabelPengeluaran
            pengeluaranModel.addRow(new Object[]{
                pengeluaranModel.getRowCount() + 1,
                namaTagihan,
                tenggat,
                nominal,
                keterangan
                
            });

            // Add data to tabelBayarTagihan
            paidModel.addRow(new Object[]{
                paidModel.getRowCount() + 1,
                namaTagihan,
                tenggat,
                nominal,
                keterangan,
                "Sudah Dibayar"
            });

            // Update total pengeluaran
            totalPengeluaran += nominal;
            updateSaldo();

            // Remove the row from tabelTagihan
            unpaidModel.removeRow(selectedRow);
            listModelTagihan.remove(selectedRow);
        }
    }
    
    }//GEN-LAST:event_bayarTagihanActionPerformed

    private void tabelBayarTagihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBayarTagihanMouseClicked
    int row = tabelBayarTagihan.getSelectedRow();
    if (row >= 0) {
        String namaTagihan = tabelBayarTagihan.getValueAt(row, 1).toString();
        String tenggat = tabelBayarTagihan.getValueAt(row, 2).toString();
        String nominal = tabelBayarTagihan.getValueAt(row, 3).toString();
        String keterangan = tabelBayarTagihan.getValueAt(row, 4).toString();
        String status = tabelBayarTagihan.getValueAt(row, 5).toString();
        
        TADetailTagihan.setText(
            "Status: " + status + "\n" +
            "Nama Tagihan: " + namaTagihan + "\n" +
            "Tenggat: " + tenggat + "\n" +
            "Nominal: " + nominal + "\n" +
            "Keterangan: " + keterangan + "\n" 
        );
    }
    if (row >= 0) {
        // Populate form fields with selected data
        inputNamaTagihan.setText(tabelBayarTagihan.getValueAt(row, 1).toString());
        inputTenggatTagihan.setText(tabelBayarTagihan.getValueAt(row, 2).toString());
        InputNominalTagihan.setText(tabelBayarTagihan.getValueAt(row, 3).toString());
        inputKeteranganTagihan.setText(tabelBayarTagihan.getValueAt(row, 4).toString());
        String status = tabelBayarTagihan.getValueAt(row, 5).toString();
        belumDibayar.setSelected(status.equals("Belum Dibayar"));
        sudahDibayar.setSelected(status.equals("Sudah Dibayar"));
        
        // Set form to edit mode for tagihan
        setTagihanEditMode(true, row, (DefaultTableModel) tabelBayarTagihan.getModel());
    }
    }//GEN-LAST:event_tabelBayarTagihanMouseClicked

    private void ubahNamaPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahNamaPasswordActionPerformed
        // Dapatkan nama pengguna dan kata sandi dari text field
      String newUsername = namaAkun.getText();
      String newPassword = passwordAkun.getText();

      // Validasi input
      if (newUsername.isEmpty() || newPassword.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Nama pengguna dan kata sandi tidak boleh kosong!");
          return;
      }

      // Konfirmasi keinginan untuk mengubah nama pengguna dan kata sandi
      int confirm = JOptionPane.showConfirmDialog(
          this,
          "Apakah Anda yakin ingin mengubah nama pengguna dan kata sandi?",
          "Konfirmasi Perubahan",
          JOptionPane.YES_NO_OPTION
      );

      // Jika pengguna memilih "Yes"
      if (confirm == JOptionPane.YES_OPTION) {
          // Update nama pengguna dan kata sandi di kelas login
          loginfr.storedUsername = newUsername;
          loginfr.storedPassword = newPassword;

          // Beri konfirmasi ke pengguna
          JOptionPane.showMessageDialog(this, "Nama pengguna dan kata sandi telah berhasil diperbarui!");
      } 
    }//GEN-LAST:event_ubahNamaPasswordActionPerformed

    private void resetSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSemuaActionPerformed
        // Tampilkan konfirmasi sebelum mereset
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin mereset/menghapus semua data?\nData yang sudah direset tidak bisa dibackup kembali.", 
            "Konfirmasi Reset Data", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
    
    // Jika pengguna memilih Ya (YES_OPTION), lanjutkan untuk mereset
    if (confirm == JOptionPane.YES_OPTION) {
        // Reset tabel pemasukan, pengeluaran, dan tagihan
        tabelPemasukan.setModel(new DefaultTableModel(null, new String[]{"No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"}));
        tabelPemasukan2.setModel(new DefaultTableModel(null, new String[]{"No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"}));
        tabelPengeluaran.setModel(new DefaultTableModel(null, new String[]{"No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"}));
        tabelPengeluaran2.setModel(new DefaultTableModel(null, new String[]{"No.", "Nama Data", "Tanggal", "Nominal (Rp)", "Keterangan Data"}));        
        tabelTagihan.setModel(new DefaultTableModel(null, new String[]{"No.", "Nama Tagihan", "Tenggat", "Nominal (Rp)", "Keterangan", "Status"}));
        // Dan seterusnya untuk tabel-tabel lainnya...

        // Reset variabel saldo, pendapatan, dan pengeluaran
        saldo = 0.0;
        totalPendapatan = 0.0;
        totalPengeluaran = 0.0;
        updateSaldo();

        // Hapus daftar tagihan
        listModelTagihan.clear();

        // Tampilkan konfirmasi bahwa data telah direset
        JOptionPane.showMessageDialog(this, "Semua data telah direset.");
    }
    }//GEN-LAST:event_resetSemuaActionPerformed

    private void listTagihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTagihanMouseClicked
     
    }//GEN-LAST:event_listTagihanMouseClicked

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
            java.util.logging.Logger.getLogger(homeFixxx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homeFixxx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homeFixxx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homeFixxx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homeFixxx().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton HapusDataTagihan;
    private javax.swing.JButton HapusDataTagihan1;
    private javax.swing.JButton HapusPemasukan;
    private javax.swing.JButton HapusPengeluaran;
    private javax.swing.JTextField InputNominal;
    private javax.swing.JTextField InputNominalTagihan;
    private javax.swing.JTextField Saldo;
    private javax.swing.JTextField Saldo1;
    private javax.swing.JTextArea TADetailTagihan;
    private javax.swing.JButton bayarTagihan;
    private javax.swing.JRadioButton belumDibayar;
    private javax.swing.JTextArea detailDataTabel;
    private javax.swing.JTextArea inputKeterangan;
    private javax.swing.JTextArea inputKeteranganTagihan;
    private javax.swing.JTextField inputNama;
    private javax.swing.JTextField inputNamaTagihan;
    private javax.swing.JTextField inputTanggal;
    private javax.swing.JTextField inputTenggatTagihan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jumlahPendapatan;
    private javax.swing.JTextField jumlahPendapatan1;
    private javax.swing.JTextField jumlahPengeluaran;
    private javax.swing.JTextField jumlahPengeluaran1;
    private javax.swing.JList<String> listTagihan;
    private javax.swing.JList<String> listTagihan1;
    private javax.swing.JTextField namaAkun;
    private javax.swing.JTextField passwordAkun;
    private javax.swing.JComboBox<String> pilihInputan;
    private javax.swing.JButton resetForm;
    private javax.swing.JButton resetFormTagihan;
    private javax.swing.JButton resetSemua;
    private javax.swing.JButton simpan;
    private javax.swing.JButton simpanTagihan;
    private javax.swing.JRadioButton sudahDibayar;
    private javax.swing.JTabbedPane tabPengaturan;
    private javax.swing.JTable tabelBayarTagihan;
    private javax.swing.JTable tabelPemasukan;
    private javax.swing.JTable tabelPemasukan2;
    private javax.swing.JTable tabelPengeluaran;
    private javax.swing.JTable tabelPengeluaran2;
    private javax.swing.JTable tabelTagihan;
    private javax.swing.JButton ubahNamaPassword;
    // End of variables declaration//GEN-END:variables
}
