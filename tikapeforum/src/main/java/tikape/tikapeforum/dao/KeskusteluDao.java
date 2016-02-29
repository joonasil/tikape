
package tikape.tikapeforum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.tikapeforum.database.*;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Keskustelualue;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;
    
    public KeskusteluDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE keskusteluId = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String otsikko = rs.getString("otsikko");
        int id = rs.getInt("keskusteluId");
        int alueId = rs.getInt("alueId");

        Keskustelu k = new Keskustelu(otsikko, id, alueId);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");
        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelu = new ArrayList();
        
        while(rs.next()) {
            
            Integer id = rs.getInt("keskusteluId");
            String otsikko = rs.getString("otsikko");
            Integer alueId = rs.getInt("alueId");
            
            keskustelu.add(new Keskustelu(otsikko, id, alueId));
        }
        
        rs.close();
        stmt.close();
        connection.close();

        return keskustelu;
    }

    @Override
    public void delete(Integer key) throws SQLException {
    }
    
    @Override
    public void insert(Integer key1, Integer key2) throws SQLException {
    }
    
}
