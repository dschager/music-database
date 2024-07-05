package cscorner;

import java.awt.BorderLayout;
import cscorner.TableRenderer;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;



public class songAdder extends JFrame {

	private JPanel contentPane;
	private JFrame frmRetrievedTable;
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private JTable table;
	private Connection conn;
	private String DBurl = "jdbc:sqlserver://LENOVO_DANIEL:1433;DatabaseName=BasedMusic;encryp=true;trustServerCertificate=true;";
	private String Username = "sa";
	private String Password = "BigDan!";
	private String UserID;
	private String SongChoice;
	private String ArtistChoice;
	private String PlaylistChoice;
	private Statement insertStatement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					songAdder frame = new songAdder();
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
	public songAdder() throws SQLException, ClassNotFoundException {
		conn = DriverManager.getConnection(DBurl, Username, Password);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		ResultSetTableModel all = new ResultSetTableModel(driver, conn, "Select * From Users");
		final String[] myList = new String[all.getRowCount()];
		String[] myList1 = new String[all.getRowCount()];
		for (int i = 0; i < all.getRowCount(); i++) {
			myList[i] = all.getValueAt(i, 0).toString();
			myList1[i] = all.getValueAt(i, 1).toString();
		}
		final JComboBox userComboBox = new JComboBox(myList1);
		userComboBox.setBounds(20, 25, 135, 21);
		contentPane.add(userComboBox);
		userComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox cb = (JComboBox) e.getSource();
				UserID = myList[userComboBox.getSelectedIndex()]; // index
				JOptionPane.showMessageDialog(null, UserID);
			}

		});
		
		
		JLabel lblNewLabel = new JLabel("User ID");
		lblNewLabel.setBounds(20, 2, 135, 13);
		contentPane.add(lblNewLabel);
		
		final JComboBox playlistComboBox_1 = new JComboBox();
		playlistComboBox_1.setBounds(20, 71, 135, 21);
		contentPane.add(playlistComboBox_1);
		playlistComboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox cb = (JComboBox) e.getSource();
				PlaylistChoice = (String) playlistComboBox_1.getSelectedItem(); // index
				JOptionPane.showMessageDialog(null, PlaylistChoice);
			}

		});
		
		
		JLabel lblNewLabel_1 = new JLabel("Playlist Name");
		lblNewLabel_1.setBounds(20, 56, 115, 13);
		contentPane.add(lblNewLabel_1);
		
		
		final ResultSetTableModel allSong = new ResultSetTableModel(driver, conn, "Select * From Song Join Artist On Song.ArtistID = Artist.ArtistID;");
		final String[] myListSong = new String[allSong.getRowCount()];
		final String[] myListArtist = new String[allSong.getRowCount()];
		final String[] myListCombined = new String[allSong.getRowCount()];
		for (int i = 0; i < allSong.getRowCount(); i++) {
			myListSong[i] = (String) allSong.getValueAt(i, 2);
			myListArtist[i] = (String) allSong.getValueAt(i, 6);
			myListCombined[i] = myListSong[i] + " - " + myListArtist[i];
		}
		final JComboBox songComboBox_2 = new JComboBox(myListCombined);
		songComboBox_2.setBounds(191, 71, 245, 21);
		contentPane.add(songComboBox_2);
		songComboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox cb = (JComboBox) e.getSource();
				SongChoice = myListSong[songComboBox_2.getSelectedIndex()]; // index
				ArtistChoice = myListArtist[songComboBox_2.getSelectedIndex()];// index
			}

		});
		
		
		JLabel lblNewLabel_2 = new JLabel("Song Choice");
		lblNewLabel_2.setBounds(191, 56, 135, 13);
		contentPane.add(lblNewLabel_2);
		
		JButton addSongBTN = new JButton("Add Song To Playlist");
		addSongBTN.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SongChoice = myListSong[userComboBox.getSelectedIndex()];
				//ArtistChoice = myListArtist[userComboBox.getSelectedIndex()];
				//PlaylistChoice = (String) playlistComboBox_1.getSelectedItem();
				//UserID = myList[userComboBox.getSelectedIndex()];
				try {
					//Get Values From Areas
					
					insertStatement = conn.createStatement();
					JOptionPane.showMessageDialog(null, "EXEC addsongstoaplaylist @PlaylistName = '"+ PlaylistChoice + "', @Title = '" + SongChoice + "', @UserID = " + UserID + ", @Name = '" + ArtistChoice + "'");
					insertStatement.executeUpdate("EXEC addsongstoaplaylist @PlaylistName = '"+ PlaylistChoice + "', @Title = '" + SongChoice + "', @UserID = " + UserID + ", @Name = '" + ArtistChoice + "'");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		addSongBTN.setBounds(20, 123, 153, 21);
		contentPane.add(addSongBTN);
		
		
		JButton removeSongBTN = new JButton("Remove Song From Playlist");
		removeSongBTN.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SongChoice = myListSong[userComboBox.getSelectedIndex()];
				//ArtistChoice = myListArtist[userComboBox.getSelectedIndex()];
				//PlaylistChoice = (String) playlistComboBox_1.getSelectedItem();
				//UserID = myList[userComboBox.getSelectedIndex()];
				try {
					//Get Values From Areas
					
					insertStatement = conn.createStatement();
					JOptionPane.showMessageDialog(null, "EXEC deletesongfromaplaylist @PlaylistName = '"+ PlaylistChoice + "', @Title = '" + SongChoice + "', @UserID = " + UserID + ", @Name = '" + ArtistChoice + "'");
					insertStatement.executeUpdate("EXEC deletesongfromaplaylist @PlaylistName = '"+ PlaylistChoice + "', @Title = '" + SongChoice + "', @UserID = " + UserID + ", @Name = '" + ArtistChoice + "'");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		removeSongBTN.setBounds(212, 123, 166, 21);
		contentPane.add(removeSongBTN);
		
		
		JButton recSongBTN = new JButton("Song Recommendations To Playlist");
		recSongBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("EXEC spTop_artist");
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		recSongBTN.setBounds(92, 154, 234, 21);
		contentPane.add(recSongBTN);
		
		
		JButton userTopSongBTN = new JButton("User's Top Songs");
		userTopSongBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("EXEC spUserTopSong @UserId = " + UserID);
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		userTopSongBTN.setBounds(31, 185, 180, 21);
		contentPane.add(userTopSongBTN);
		
		
		JButton userTopArtistBTN = new JButton("User's Top Artists");
		userTopArtistBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("EXEC spUserTopArtist @UserId = " + UserID);
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		userTopArtistBTN.setBounds(31, 211, 180, 21);
		contentPane.add(userTopArtistBTN);
		
		JButton allTopSongBTN = new JButton("Top Songs In DB");
		allTopSongBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("EXEC spTop_song");
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		allTopSongBTN.setBounds(242, 185, 173, 21);
		contentPane.add(allTopSongBTN);
		
		
		JButton allTopArtistBTN = new JButton("Top Artists In DB");
		allTopArtistBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("EXEC spTop_artist");
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		allTopArtistBTN.setBounds(242, 211, 173, 21);
		contentPane.add(allTopArtistBTN);
		
		
		JButton userTopGenreBTN = new JButton("User's Top Genres");
		userTopGenreBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("EXEC spUserTopGenre @UserId = " + UserID);
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		userTopGenreBTN.setBounds(31, 242, 180, 21);
		contentPane.add(userTopGenreBTN);
		
		
		JButton allTopGenreBTN = new JButton("Top Genres in DB");
		allTopGenreBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("EXEC spTop_genre");
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		allTopGenreBTN.setBounds(242, 242, 173, 21);
		contentPane.add(allTopGenreBTN);
		
		
		JButton selectUserBTN = new JButton("Select User ID");
		selectUserBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSetTableModel usersPlaylists = null;
				try {
					usersPlaylists = new ResultSetTableModel(driver, conn, "Select * From Playlist Where UserID = '" + UserID + "'");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] mypList = new String[usersPlaylists.getRowCount()];
				for (int i = 0; i < usersPlaylists.getRowCount(); i++) {
					mypList[i] = usersPlaylists.getValueAt(i, 1).toString();
				}
				//final JComboBox playlistComboBox_1 = new JComboBox(mypList);
				playlistComboBox_1.setModel(new DefaultComboBoxModel(mypList));
			}
		});
		
		
		selectUserBTN.setBounds(165, 25, 153, 21);
		contentPane.add(selectUserBTN);
		
		JButton btnNewButton = new JButton("View");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "EXEC spViewPlaylist @PlaylistName = '" + PlaylistChoice + "', @UserID = '" + UserID + "'");
					TableRenderer.renderTable("EXEC spViewPlaylist @PlaylistName = '" + PlaylistChoice + "', @UserID = '" + UserID + "'");
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		btnNewButton.setBounds(45, 92, 65, 100);
		contentPane.add(btnNewButton);
		
		
		
		setTitle("Add/Delete Songs From Playlist");
		
		
	}
}
