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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;

public class PlaylistEditing extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JFrame frmRetrievedTable;
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private JTable table;
	private Connection conn;
	private String DBurl = "jdbc:sqlserver://LENOVO_DANIEL:1433;DatabaseName=BasedMusic;encryp=true;trustServerCertificate=true;";
	private String Username = "sa";
	private String Password = "BigDan!";
	private String UserID;
	private Statement insertStatement;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlaylistEditing frame = new PlaylistEditing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public PlaylistEditing() throws ClassNotFoundException, SQLException {
		conn = DriverManager.getConnection(DBurl, Username, Password);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Playlists");

		JLabel lblNewLabel = new JLabel("Enter User ID");
		lblNewLabel.setBounds(91, 20, 105, 13);
		contentPane.add(lblNewLabel);

		textField_1 = new JTextField();
		textField_1.setBounds(32, 94, 194, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

//		JButton btnNewButton = new JButton("Update");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//                    // Get text from the text field and parse it to an integer
//                    String userInputPlaylist = textField_1.getText();
//                    JOptionPane.showMessageDialog(null, "You entered: " + UserID + "      " + userInputPlaylist);
//                } catch (NumberFormatException ex) {
//                    // Handle case where input is not a valid integer
//                    JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
//                }
//			}
//		});
//		btnNewButton.setBounds(78, 123, 85, 21);
//		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Enter Playlist Name");
		lblNewLabel_1.setBounds(78, 71, 121, 13);
		contentPane.add(lblNewLabel_1);

		JButton userPlaylistsButton = new JButton("View User's Playlists");
		userPlaylistsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Query = ("Select * FROM Playlist Where UserID = " + UserID);
				try {
					TableRenderer.renderTable(Query);
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		userPlaylistsButton.setBounds(277, 207, 159, 21);
		contentPane.add(userPlaylistsButton);

		JButton allPlaylistButton = new JButton("View All Playlists");
		allPlaylistButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableRenderer.renderTable("Select * From Playlist");
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		allPlaylistButton.setBounds(277, 238, 159, 21);
		contentPane.add(allPlaylistButton);

		
		JButton renameButton = new JButton("Rename Playlist");
		renameButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Code that may throw SQLException
				// For example:
				// Connection connection = DriverManager.getConnection(DBurl, username, password);
				// ...
				RenamePlaylistPage page = null;
				try {
					page = new RenamePlaylistPage(UserID);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					page.setVisible(true);
		    }
		});
		renameButton.setBounds(32, 238, 165, 21);
		contentPane.add(renameButton);

		
		JButton createButton = new JButton("Create Playlist");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String userInputPlaylist = textField_1.getText();
					ResultSetTableModel play = new ResultSetTableModel(driver, conn, "Select * From Playlist");
					String[] myListPID = new String[play.getRowCount()];
					for (int i = 0; i < play.getRowCount(); i++) {
						myListPID[i] = play.getValueAt(i, 1).toString();
					}
					int newPID = ((int) (Math.random() * (999 - 100) + 100));
					String newPIDS = String.valueOf(newPID);
					while (true) {
						for (int i = 0; i < myListPID.length; i++) {
							if (newPIDS.equals(myListPID[i])) {
								newPID = ((int) (Math.random() * (999 - 100) + 100));
								newPIDS = String.valueOf(newPID);
								i = -1;
							} else {
								continue;
							}
						}
						break;
					}

					insertStatement = conn.createStatement();
					insertStatement.executeUpdate("Insert into Playlist(PlaylistID, Name, SongCount, UserId)Values("
							+ newPID + ",'" + userInputPlaylist + "',0," + UserID + ");");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		createButton.setBounds(32, 179, 164, 21);
		contentPane.add(createButton);

		JButton deleteButton = new JButton("Delete Playlist");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String userInputPlaylist = textField_1.getText();
					insertStatement = conn.createStatement();
					insertStatement.executeUpdate("DELETE FROM Playlist WHERE UserID = " + UserID + " AND Name = '"
							+ userInputPlaylist + "';");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		deleteButton.setBounds(32, 207, 164, 21);
		contentPane.add(deleteButton);

		
		//User dropdown
		ResultSetTableModel all = new ResultSetTableModel(driver, conn, "Select * From Users");
		String[] myList = new String[all.getRowCount()];
		for (int i = 0; i < all.getRowCount(); i++) {
			myList[i] = all.getValueAt(i, 0).toString();
		}
		final JComboBox comboBox = new JComboBox(myList);
		comboBox.setBounds(32, 43, 175, 21);
		contentPane.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox cb = (JComboBox) e.getSource();
				UserID = (String) comboBox.getSelectedItem(); // index
			}

		});

	}
}
