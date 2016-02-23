
package tikape.tikapeforum.database;

import tikape.tikapeforum.collectors.Collector;
import java.sql.*;
import java.util.*;

public class Database<T> {
    
    String address;
    
    public Database(String address) throws Exception {
        this.address = address;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }
    
}
