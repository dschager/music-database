package cscorner;

import java.awt.BorderLayout;
import cscorner.TableRenderer;
import java.awt.EventQueue;
import java.sql.SQLException;

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
import java.awt.event.ActionEvent;

public class UserEditing extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JFrame frmRetrievedTable;
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private JTable table;
    private Connection conn;
    private String DBurl = "jdbc:sqlserver://localhost:1433;databaseName=MusicDB;encrypt=true;trustServerCertificate=true;";
	private String Username = "sa";
	private String Password = "reallyStrongPwd123";


	//Class to add user, delete user, or check all user information
	
	public UserEditing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 300, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JLabel lblNewLabel = new JLabel("Enter Name");
		lblNewLabel.setBounds(90, 20, 120, 20);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(32, 40, 194, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				System.out.println(name);
			}
		});


		JLabel lblNewLabel_1 = new JLabel("Enter Username");
		lblNewLabel_1.setBounds(90, 80, 120, 20);
		contentPane.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setBounds(32, 100, 194, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField_1.getText();
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Enter Email");
		lblNewLabel_2.setBounds(90, 140, 120, 13);
		contentPane.add(lblNewLabel_2);

		textField_2 = new JTextField();
		textField_2.setBounds(32, 160, 194, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = textField_2.getText();
			}
		});
		
		JLabel lblNewLabel2 = new JLabel("Enter DOB (YYYY-MM-DD)");
		lblNewLabel2.setBounds(55, 180, 155, 20);
		contentPane.add(lblNewLabel2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(32, 200, 194, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		textField_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String DOB = textField_3.getText();
			}
		});

		JButton btnNewButton = new JButton("Add User");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    // Obtain the name, username, and email of the new user
					String name = textField.getText();
					String username = textField_1.getText();
					String email = textField_2.getText();
					String DOB = textField_3.getText();
					
					
					//Formats the usernames from DB into an array
					String[][] currentUsers = TableRenderer.obtainData("SELECT username FROM Users");
					int i = 0;
					
					//Iterates through the current usernames to ensure there isn't a duplicate
					while (i < currentUsers.length) {
					    // Check if the username matches the current row's username
					    if (currentUsers[i][0].equals(username)) {
					        throw new Exception("Username already exists!"); 
					    }
				
					    i++; // Move to next row
					}
					
					//Adds the data to the table
			    	String formattedNewUser = "'" + name + "', '" + email + "', '" + username + "', '" + DOB + "';";
			    	TableRenderer.setData("EXEC AddUser" + formattedNewUser);
			    	JOptionPane.showMessageDialog(null, "User Successfully Added.");
					
                   
                } catch (NumberFormatException ex) {
                    // Handle case where input is not a valid integer
                    JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
                } catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// Handles case where username is taken
					JOptionPane.showMessageDialog(null, "Username is already taken.");
				}
			
			}
		});
		btnNewButton.setBounds(90, 220, 85, 21);
		contentPane.add(btnNewButton);


		JButton userPlaylistsButton = new JButton("View All Users");
		userPlaylistsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Query = "Select* FROM Users";
				try
				{
					TableRenderer.renderTable(Query);
				}catch (ClassNotFoundException ex) {
					ex.printStackTrace(); // Handle ClassNotFoundException
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQLException
				}
			}
		});
		userPlaylistsButton.setBounds(52, 280, 159, 21);
		contentPane.add(userPlaylistsButton);

		
		JButton deleteUserButton = new JButton("Delete User");
		deleteUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    // Obtain the name, username, and email of the new user
					String name = textField.getText();
					String username = textField_1.getText();
					String email = textField_2.getText();
					String DOB = textField_2.getText();
					
					
					//Formats the usernames from DB into an array
					String[][] userInfo = TableRenderer.obtainData("SELECT username, email, name FROM Users");
					
					boolean found = false;

			    	for (int i = 0; i < userInfo.length; i++) {
		                // Check if the username, name, and email match
		                if (userInfo[i][0].equals(username) && userInfo[i][1].equals(email) && userInfo[i][2].equals(name)) {
		                    found = true;
		                    break;
		                }
		            }
		            if (!found) {
		                throw new Exception("User with provided information not found.");
		            }
					
					//Adds the data to the table
			    	String formattedUsername = "'" + username + "';";
			    	TableRenderer.setData("DELETE FROM Users WHERE Username = " + formattedUsername);
			    	JOptionPane.showMessageDialog(null, "User Successfully Deleted.");
			    	

                } catch (NumberFormatException ex) {
                    // Handle case where input is not a valid integer
                    JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
                } catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// Handles case where username is taken
					JOptionPane.showMessageDialog(null, "Incorrect Information.");
				}
			
			}
		});
		deleteUserButton.setBounds(90, 240, 85, 21);
		contentPane.add(deleteUserButton);
		
	}
	
	//Getters for the text fields
	public JTextField getTextField() {
        return textField;
    }
    
    public JTextField getTextField_1() {
        return textField_1;
    }
    
    public JTextField getTextField_2() {
        return textField_2;
    }
    public JTextField getTextField_3() {
        return textField_3;
    }
    
}