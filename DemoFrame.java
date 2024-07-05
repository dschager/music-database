package cscorner;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DemoFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DemoFrame frame = new DemoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DemoFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Main Menu");
		
	//new button
		JButton btnNewButton = new JButton("Add User");
		btnNewButton.setBounds(49, 50, 187, 21);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		            // Code that may throw SQLException
		            // For example:
		            // Connection connection = DriverManager.getConnection(DBurl, username, password);
		            // ...
		            UserEditing userPage = null;
					userPage = new UserEditing();
		            userPage.setVisible(true);
		    }
		});
		
		
		JButton btnNewButton_2 = new JButton("Add/Delete Playlist");
		btnNewButton_2.setBounds(49, 100, 187, 21);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		            // Code that may throw SQLException
		            // For example:
		            // Connection connection = DriverManager.getConnection(DBurl, username, password);
		            // ...
		            PlaylistEditing page = null;
					try {
						page = new PlaylistEditing();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            page.setVisible(true);
		            dispose();
		    }
		});
		
		
		JButton btnNewButton_3 = new JButton("Add/Delete Songs");
		btnNewButton_3.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	songAdder page2 = null;
				try {
					page2 = new songAdder();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            page2.setVisible(true);
		    }
		});
		btnNewButton_3.setBounds(49, 150, 187, 21);
		contentPane.add(btnNewButton_3);
		
		
		
	}
}
