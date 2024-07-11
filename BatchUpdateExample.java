package M06_assignment2;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.SQLException; 

public class BatchUpdateExample { 
    private static final String DB_URL = "jdbc:mysql://localhost/javabook"; 
    private static final String USER = "JohnDoe"; 
    private static final String PASS = "YourPassword"; 

    public static void main(String[] args) { 
        try { 
            // Load MySQL JDBC Driver 
            Class.forName("com.mysql.jdbc.Driver"); 

            // Establish connection 
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 

            // Batch update 
            long batchStartTime = System.nanoTime(); 
            batchUpdate(conn); 
            long batchEndTime = System.nanoTime(); 
            System.out.println("Batch update completed. The elapsed time is " + (batchEndTime - batchStartTime)); 

            // Non-batch update 
            long nonBatchStartTime = System.nanoTime(); 
            nonBatchUpdate(conn); 
            long nonBatchEndTime = System.nanoTime(); 
            System.out.println("Non-batch update completed. The elapsed time is " + (nonBatchEndTime - nonBatchStartTime)); 

            // Close connection 
            conn.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
    
    private static void batchUpdate(Connection conn) throws SQLException { 
        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)"; 
        PreparedStatement pstmt = conn.prepareStatement(sql); 

        for (int i = 0; i < 1000; i++) { 
            pstmt.setDouble(1, Math.random()); 
            pstmt.setDouble(2, Math.random()); 
            pstmt.setDouble(3, Math.random()); 
            pstmt.addBatch(); 
        } 
        
        pstmt.executeBatch(); 
        pstmt.close(); 
    } 
    
    private static void nonBatchUpdate(Connection conn) throws SQLException { 
        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)"; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        for (int i = 0; i < 1000; i++) { 
            pstmt.setDouble(1, Math.random()); 
            pstmt.setDouble(2, Math.random()); 
            pstmt.setDouble(3, Math.random()); 
            pstmt.executeUpdate(); 
        } 

        pstmt.close(); 
    } 
}
