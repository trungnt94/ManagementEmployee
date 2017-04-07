/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication10;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author hoang
 */
public class Management extends JFrame {

    private JLabel lblID, lblName, lblGender, lblDate, lblEmail, lblPhone, lblAddress;
    private JTextField txtID, txtName, txtEmail, txtPhone, txtDate;
    private JRadioButton radMale, radFemale;
    private JTextArea txtArea;
    private JButton btnSave, btnDelete, btnSearch;
    private JPanel panel;
    private String gender;

    public Management() {
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý Nhân Viên");

        this.lblID = new JLabel();
        this.lblID.setBounds(50, 25, 80, 20);
        this.lblID.setText("Mã Nhân Viên");

        this.txtID = new JTextField();
        this.txtID.setBounds(150, 25, 150, 20);

        this.btnSearch = new JButton();
        this.btnSearch.setBounds(330, 25, 100, 20);
        this.btnSearch.setText("Tìm kiếm");
        this.btnSearch.addActionListener((ActionEvent e) -> {
            try {
                panel.setVisible(true);
                Search();
            } catch (SQLException ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.panel = new JPanel();
        this.panel.setBounds(0, 65, 500, 350);
        this.panel.setBorder(BorderFactory.createBevelBorder(1));

        this.lblName = new JLabel();
        this.lblName.setBounds(50, 25, 80, 20);
        this.lblName.setText("Tên");

        this.txtName = new JTextField();
        this.txtName.setBounds(150, 25, 150, 20);

        this.lblGender = new JLabel();
        this.lblGender.setBounds(50, 65, 80, 20);
        this.lblGender.setText("Giới tính");

        this.radMale = new JRadioButton("Male");
        this.radFemale = new JRadioButton("Female");
        this.radMale.setBounds(150, 65, 80, 20);
        this.radFemale.setBounds(250, 65, 80, 20);
        ButtonGroup btnGr = new ButtonGroup();
        btnGr.add(radMale);
        btnGr.add(radFemale);

        this.lblDate = new JLabel();
        this.lblDate.setBounds(50, 105, 80, 20);
        this.lblDate.setText("Ngày sinh");

        this.txtDate = new JTextField();
        this.txtDate.setBounds(150, 105, 150, 20);

        this.lblEmail = new JLabel();
        this.lblEmail.setBounds(50, 145, 80, 20);
        this.lblEmail.setText("Email");

        this.txtEmail = new JTextField();
        this.txtEmail.setBounds(150, 145, 150, 20);

        this.lblPhone = new JLabel();
        this.lblPhone.setBounds(50, 185, 80, 20);
        this.lblPhone.setText("Điện thoại");

        this.txtPhone = new JTextField();
        this.txtPhone.setBounds(150, 185, 150, 20);

        this.lblAddress = new JLabel();
        this.lblAddress.setBounds(50, 225, 80, 20);
        this.lblAddress.setText("Địa chỉ");

        this.txtArea = new JTextArea();
        this.txtArea.setBounds(50, 265, 400, 90);

        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblGender);
        panel.add(radMale);
        panel.add(radFemale);
        panel.add(lblDate);
        panel.add(txtDate);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblPhone);
        panel.add(txtPhone);
        panel.add(lblAddress);
        panel.add(txtArea);
        panel.setLayout(null);
        panel.setVisible(false);

        this.btnSave = new JButton("Lưu");
        this.btnSave.setBounds(125, 450, 100, 20);
        this.btnSave.addActionListener((ActionEvent e) -> {
            try {
                EditInfo();
            } catch (SQLException ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.btnDelete = new JButton("Xóa");
        this.btnDelete.setBounds(245, 450, 100, 20);
        this.btnDelete.addActionListener((ActionEvent e) -> {
            try {
                Delete();
            } catch (SQLException ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        add(lblID);
        add(txtID);
        add(btnSearch);
        add(btnSave);
        add(btnDelete);
        add(panel);
        setLayout(null);
    }

    public void Search() throws SQLException {
        String ID = txtID.getText();
        String search = "SELECT * FROM employees WHERE Status = 1 AND ID = " + ID;
        Connection connect = JavaDBConnection.getConnection();
        Statement stt = connect.createStatement();
        ResultSet rs = stt.executeQuery(search); 
        if(rs.isBeforeFirst()){
        while (rs.next()) {                   
                this.txtName.setText(rs.getString("Name"));             
                this.txtEmail.setText(rs.getString("Email"));
                this.txtPhone.setText(rs.getString("Phone"));
                this.txtArea.setText(rs.getString("Address"));
                this.txtDate.setText(rs.getString("Birthday"));            
                if("Male".equals(rs.getString("Gender"))){                
                    this.radMale.setSelected(true);
                } else if("Female".equals(rs.getString("Gender"))){                
                    this.radFemale.setSelected(true);
                }            
        }
        } else {
            this.txtName.setText("");
            this.txtEmail.setText("");
            this.txtPhone.setText("");
            this.txtArea.setText("");
            this.txtDate.setText("");
            JOptionPane.showMessageDialog(null, "Nhân viên không tồn tại hoặc đã bị xóa");
        }
    }

    public void EditInfo() throws SQLException {
        if (txtID.getText() == null) {
            JOptionPane.showConfirmDialog(null, "Error");
        } else {
            if(radMale.isSelected()) 
                gender="Male";
            else if(radFemale.isSelected()) 
                gender="Female";
            String update = "UPDATE nhanvien SET Name = '"
                    + txtName.getText()  + "', Email = '"
                    + txtEmail.getText() + "', Phone = "
                    + txtPhone.getText() + ", Address = '"
                    + txtArea.getText()  + "', Birthday = '"
                    + txtDate.getText()  + "',Gender = '"
                    + gender             + "' WHERE ID = " + txtID.getText();
            System.out.println(txtID.getText());
            Connection connect = JavaDBConnection.getConnection();
            Statement stt = connect.createStatement();
            int rs = stt.executeUpdate(update);
        }
    }

    public void Delete() throws SQLException{
        if (txtID.getText() == null) {
            JOptionPane.showConfirmDialog(null, "Error");
        } else {
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có chắc muốn xóa?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){ 
                String update = "UPDATE nhanvien SET Status = 0 WHERE ID = " + txtID.getText();
                Connection connect = JavaDBConnection.getConnection();
                Statement stt = connect.createStatement();
                int rs = stt.executeUpdate(update);
                this.txtName.setText("");
                this.txtEmail.setText("");
                this.txtPhone.setText("");
                this.txtArea.setText("");
                this.txtDate.setText("");
                JOptionPane.showMessageDialog(new JFrame(), "Thông tin đã được xóa.");
            }
        }
    }
    public static void main(String[] args) {
        Management je = new Management();
        je.setVisible(true);
    }
    

    }
