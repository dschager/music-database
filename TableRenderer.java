package cscorner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableRenderer {
	private JPanel contentPane;
	private JFrame frmRetrievedTable;
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private JTable table;
    private static Connection conn;
	private static String DBurl = "jdbc:sqlserver://LENOVO_DANIEL:1433;DatabaseName=BasedMusic;encryp=true;trustServerCertificate=true;";
	private static String Username = "sa";
	private static String Password = "BigDan!";
	
	
	public static void renderTable(String query)  throws ClassNotFoundException, SQLException {
        try {
        	conn = DriverManager.getConnection(DBurl, Username, Password);
            ResultSetTableModel rs = new ResultSetTableModel(driver, conn, query);
            JTable table = new JTable();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setModel(rs);
            table.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(200, 200, 636, 423);

            JFrame frame = new JFrame("Retrieved Table");
            frame.getContentPane().add(scrollPane);
            frame.pack();
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
        	e.printStackTrace();
        }
    }
	
	public static String[][] obtainData(String query) throws ClassNotFoundException, SQLException {
        String[][] data = null;
        try {
            conn = DriverManager.getConnection(DBurl, Username, Password);
            ResultSetTableModel rs = new ResultSetTableModel(driver, conn, query);
            int rowCount = rs.getRowCount();
            int columnCount = rs.getColumnCount();
            
            // Initialize data array
            data = new String[rowCount][columnCount];
            
            // Populate data array with values from ResultSet
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    data[i][j] = rs.getValueAt(i, j).toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public static void setData(String query) throws ClassNotFoundException, SQLException {
        String[][] data = null;
        try {
        	conn = DriverManager.getConnection(DBurl, Username, Password);
            ResultSetTableModel rs = new ResultSetTableModel(driver, conn, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
    }
}
