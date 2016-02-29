
package tikape.tikapeforum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tikape.tikapeforum.database.Database;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Viesti;

public class ViestiDao implements Dao<Viesti, String> {
    
    private Database database;
    private Dao<Keskustelu, Integer> keskusteluDao;
    
    public ViestiDao(Database db, Dao<Keskustelu, Integer> kd) {
        this.database = db;
        this.keskusteluDao = kd;
    }

    @Override
    public Viesti findOne(String key) throws SQLException {
        
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE nimimerkki LIKE ?");
        
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        String sisalto = rs.getString("sisalto");
        String nimim = rs.getString("nimimerkki");
        int keskusteluId = rs.getInt("keskusteluId");
        Timestamp aika = rs.getTimestamp("aika");
        
        Viesti v = new Viesti(sisalto, nimim, keskusteluId, aika);
                
        rs.close();
        stmt.close();
        connection.close();
        
        return v;
    }
    
    @Override
    public List findAll() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");
        
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList();
        while(rs.next()) {
            String sisalto = rs.getString("sisalto");
            String nimim = rs.getString("nimimerkki");
            int keskusteluId = rs.getInt("keskusteluId");
            Timestamp aika = rs.getTimestamp("aika");
            
            viestit.add(new Viesti(sisalto, nimim, keskusteluId, aika));
        }
        
        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(String key) throws SQLException {
    }
    
    @Override
    public void insert(String viesti, String nimimerkki) throws SQLException {
        
        Date date= new Date();
        Timestamp aika = new Timestamp(date.getTime());
        
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = 
                connection.prepareStatement("INSERT INTO Viesti(keskusteluId, sisalto, nimimerkki, aika)"
                                            + "VALUES(1,?,?,\"" + aika +  "\")");
        
        stmt.setObject(1, viesti);
        stmt.setObject(2, nimimerkki);
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
}
