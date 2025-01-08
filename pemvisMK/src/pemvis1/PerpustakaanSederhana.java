/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pemvis1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
/**
 *
 * @author verra
 */
public class PerpustakaanSederhana extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JList<String> listBuku;
    private DefaultListModel<String> listModel;
    private ArrayList<DataBuku> bukuList;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final JPanel bukuListPanel;
    private final JPanel detailBukuPanel;

    
      public PerpustakaanSederhana() {
        setTitle("Perpustakaan Sederhana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        bukuListPanel = createBukuListPanel();
        detailBukuPanel = createDetailBukuPanel();
        
        cardPanel.add(bukuListPanel, "BukuList");
        cardPanel.add(detailBukuPanel, "DetailBuku");
        
        add(cardPanel);
        
        setVisible(true);
    }
    
    private JPanel createBukuListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> bukuList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(bukuList);
        
        listModel.addElement("Buku 1");
        listModel.addElement("Buku 2");
        listModel.addElement("Buku 3");
        
        bukuList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cardLayout.show(cardPanel, "DetailBuku");
                }
            }
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createDetailBukuPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        
        JLabel titleLabel = new JLabel("Judul:");
        JTextField titleField = new JTextField();
        
        JLabel authorLabel = new JLabel("Penulis:");
        JTextField authorField = new JTextField();
        
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(authorLabel);
        panel.add(authorField);
        
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PerpustakaanSederhana();
        });
    }

    private static class DataBuku {

        public DataBuku() {
        }
    }
}

