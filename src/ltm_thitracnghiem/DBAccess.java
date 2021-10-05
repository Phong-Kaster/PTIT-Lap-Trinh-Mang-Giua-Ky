package ltm_thitracnghiem;

import java.sql.*;

public class DBAccess {

    private Connection con;
    private Statement stmt;
    SQLServer_Connection myCon;

    public DBAccess() {
        myCon = new SQLServer_Connection();
    }
    
    public void connect(String Url) throws SQLException{
        con = myCon.getConnection(Url);
        stmt = con.createStatement();
    }

    public int Update(String str) {
        try {
            System.out.println(str);
            int i = stmt.executeUpdate(str);
            return i;
        } catch (Exception e) {
            return -1;
        }
    }

    public ResultSet Query(String str) {
        try {
            ResultSet rs = stmt.executeQuery(str);
            return rs;
        } catch (Exception e) {
            return null;
        }
    }
}
