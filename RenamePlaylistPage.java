package cscorner;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class RenamePlaylistPage extends JFrame {

	private JPanel contentPane;
	private JTextField newPlaylistTextField;
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private Connection conn;
	private String DBurl = "jdbc:sqlserver://LENOVO_DANIEL:1433;DatabaseName=BasedMusic;encryp=true;trustServerCertificate=true;";
	private String Username = "sa";
	private String Password = "BigDan!";
	private static String UserID;
	private Statement changeStatement;
	private String playlistName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RenamePlaylistPage frame = new RenamePlaylistPage(UserID);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public RenamePlaylistPage(final String UserID) throws ClassNotFoundException, SQLException {
		conn = DriverManager.getConnection(DBurl, Username, Password);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		newPlaylistTextField = new JTextField();
		newPlaylistTextField.setBounds(222, 81, 159, 19);
		contentPane.add(newPlaylistTextField);
		newPlaylistTextField.setColumns(10);
		
		
		//SELECTION OF USER'S Playlists
		ResultSetTableModel usersPlaylists = new ResultSetTableModel(driver, conn, "Select * From Playlist Where UserID = '" + UserID + "'");
		String[] myList = new String[usersPlaylists.getRowCount()];
		for (int i = 0; i < usersPlaylists.getRowCount(); i++) {
			myList[i] = usersPlaylists.getValueAt(i, 1).toString();
		}
		final JComboBox oldPlaylistComboBox = new JComboBox(myList);
		oldPlaylistComboBox.setBounds(32, 80, 159, 21);
		contentPane.add(oldPlaylistComboBox);
		oldPlaylistComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox cb = (JComboBox) e.getSource();
				playlistName = (String) oldPlaylistComboBox.getSelectedItem(); // index
			}

		});
		
		
		//LABELS
		JLabel lblNewLabel = new JLabel("Old Playlist Name");
		lblNewLabel.setBounds(59, 57, 132, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New Playlist Name");
		lblNewLabel_1.setBounds(255, 58, 99, 13);
		contentPane.add(lblNewLabel_1);
		
		//UPDATE BUTTON
		JButton updaterButton = new JButton("Update");
		updaterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String userInputPlaylist = newPlaylistTextField.getText();
				try {
					changeStatement = conn.createStatement();
					JOptionPane.showMessageDialog(null, "EXEC spPlaylist_Rename @NewPlaylistName = '"+ userInputPlaylist + "', @PlaylistName = '" + playlistName + "';");
					changeStatement.executeUpdate("EXEC spPlaylist_Rename @NewPlaylistName = '"+ userInputPlaylist + "', @PlaylistName = '" + playlistName + "';");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		updaterButton.setBounds(158, 149, 85, 21);
		contentPane.add(updaterButton);
	}
}
