package cscorner;

//Creating window imports
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

//Creating table and query imports
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class demoTester extends JFrame {

	private JPanel contentPane;
	private JFrame frmRetrievedTable;
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private JTable table;
    private Connection conn;
	private String DBurl = "jdbc:sqlserver://LENOVO_DANIEL:1433;DatabaseName=BasedMusic;encryp=true;trustServerCertificate=true;";
	private String Username = "sa";
	private String Password = "BigDan!";
//	private Connection connection = DriverManager.getConnection(DBurl, Username, Password);

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public demoTester(String query) throws ClassNotFoundException, SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(150, 150, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
		
		
		frmRetrievedTable = new JFrame();
		frmRetrievedTable.setTitle("Retrieved Table");
		frmRetrievedTable.setResizable(false);
		frmRetrievedTable.setBounds(100, 100, 670, 480);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRetrievedTable.getContentPane().setLayout(null);
		
																//connection?
        conn = DriverManager.getConnection(DBurl, Username, Password);
		

        //Making the call to the sever with the query
		ResultSetTableModel rs = new ResultSetTableModel(driver,conn,query);
	
		table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(rs);
        table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 636, 423);
		frmRetrievedTable.getContentPane().add(scrollPane);
		
		frmRetrievedTable.setVisible(true);
		
	}

}
