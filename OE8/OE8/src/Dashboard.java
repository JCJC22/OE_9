import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.SystemColor;

public class Dashboard extends JFrame {

	private JPanel contentPane;
	JLabel lbluserT;
	private JTextField txtStudentNo;
	private JTextField txtStudentName;
	private JTextField txtAddress;
	private JTextField textField_3;
	private JTable tableView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 * @return 
	 */
	public Dashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 943, 501);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbluserT = new JLabel();
		lbluserT.setBounds(776, 25, 151, 29);
		lbluserT.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 16));
		contentPane.add(lbluserT);
		
		JLabel lblNewLabel = new JLabel("Student Record System");
		lblNewLabel.setBounds(155, 25, 345, 42);
		lblNewLabel.setFont(new Font("Barlow Condensed", Font.PLAIN, 32));
		contentPane.add(lblNewLabel);
		
		txtStudentNo = new JTextField();
		txtStudentNo.setBounds(149, 119, 197, 34);
		contentPane.add(txtStudentNo);
		txtStudentNo.setColumns(10);
		
		txtStudentName = new JTextField();
		txtStudentName.setBounds(149, 181, 197, 34);
		txtStudentName.setColumns(10);
		contentPane.add(txtStudentName);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(149, 246, 197, 34);
		txtAddress.setColumns(10);
		contentPane.add(txtAddress);
		
		JLabel lblNewLabel_1 = new JLabel("Student No.");
		lblNewLabel_1.setBounds(41, 129, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Student Name");
		lblNewLabel_1_1.setBounds(41, 191, 86, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Student Address");
		lblNewLabel_1_2.setBounds(41, 256, 108, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JButton btnNewButton = new JButton("UPDATE");
		btnNewButton.setBounds(715, 381, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton);
		
		JButton btnView = new JButton("VIEW");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewRecords();
			}
		});
		btnView.setBounds(257, 291, 89, 23);
		contentPane.add(btnView);
		
		JButton btnNewButton_2 = new JButton("ADD");
		btnNewButton_2.setBounds(149, 291, 89, 23);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRecord();
			}
		});
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("DELETE");
		btnNewButton_1.setBounds(814, 381, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("CLEAR");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTextfield();
			}
		});
		btnNewButton_3.setBounds(203, 325, 89, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("EXIT");
		btnNewButton_4.setBounds(10, 428, 89, 23);
		contentPane.add(btnNewButton_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(555, 67, 197, 34);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		tableView = new JTable();
		tableView.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tableView.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tableView.setBackground(SystemColor.info);
		tableView.setBounds(397, 119, 506, 246);
		contentPane.add(tableView);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("E:\\ACAD\\OOP\\Eclipse\\OE8\\OE8\\src\\image\\ico.png"));
		lblNewLabel_2.setBounds(85, 11, 64, 73);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton_5 = new JButton("Search");
		btnNewButton_5.setBounds(776, 73, 89, 23);
		contentPane.add(btnNewButton_5);
	}
	

	//db connection
		static Connection connect() {
			try {
				String myDriver = "com.mysql.cj.jdbc.Driver";
				//connection string
				String url = "jdbc:mysql://localhost:3307/copl_db";
				Class.forName(myDriver);
				return (Connection)DriverManager.getConnection(url,"root","");
			}catch(Exception e) {
				System.out.print("Cannot connect to the database");
			}
			return null;
		}
		//add record
		public void addRecord() {
			Connection con = connect();
			Calendar date = Calendar.getInstance();
			java.sql.Date datecreated = new java.sql.Date(date.getTime().getTime());
			
			try {
				String sql = "INSERT INTO student_tbl (student_no, student_name,address,date_created) VALUES (?,?,?,NOW())";
				PreparedStatement ps =(PreparedStatement) con.prepareStatement(sql);
				
				ps.setString(1,txtStudentNo.getText());
				ps.setString(2,txtStudentName.getText());
				ps.setString(3,txtAddress.getText());
				//ps.setDate(4, datecreated);
				ps.execute();
				
				JOptionPane.showMessageDialog(null,"Record successfully added...");
				clearTextfield();
			}catch(Exception e) {
				System.out.print("Error..." + e);
			}
		}
		//View Records
		public void viewRecords() {
			// TODO Auto-generated method stub
			Connection con = connect();
			DefaultTableModel mod = new DefaultTableModel();
			mod.addColumn("Number");
			mod.addColumn("Student No");
			mod.addColumn("Student Name");
			mod.addColumn("Address");
			mod.addColumn("Date of Registration");
			
			try {
				String sql = "SELECT * from student_tbl";
				Statement st = (Statement) con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while(rs.next()) {
					mod.addRow(new Object[] {
						rs.getInt("id"),
						rs.getString("student_no"),	
						rs.getString("student_name"),
						rs.getString("address"),
						rs.getString("date_created")
					});
				}
				rs.close();
				st.close();
				con.close();
				
				tableView.setModel(mod);
				//tableView.setAutoResizeMode(0);
				tableView.getColumnModel().getColumn(0).setPreferredWidth(20);
				tableView.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableView.getColumnModel().getColumn(2).setPreferredWidth(30);
				tableView.getColumnModel().getColumn(3).setPreferredWidth(30);
				tableView.getColumnModel().getColumn(4).setPreferredWidth(30);
			}catch(Exception ex) {
				System.out.print("Error..." + ex);
			}
			
		}
		
		private void clearTextfield() {
			// TODO Auto-generated method stub
			txtStudentNo.setText("");
			txtStudentName.setText("");
			txtAddress.setText("");
			//JOptionPane.showMessageDialog(null, "Clear successful");
		}
}
