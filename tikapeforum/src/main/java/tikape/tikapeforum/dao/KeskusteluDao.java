
package tikape.tikapeforum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tikape.tikapeforum.database.*;
import tikape.tikapeforum.database.taulut.Keskustelu;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database db;
    
    public KeskusteluDao(Database db) {
        this.db = db;
    }
    
    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE keskusteluId = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String otsikko = rs.getString("otsikko");

        Keskustelu k = new Keskustelu(otsikko);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
    }
    
    @Override
    public void insert(Integer key1, Integer key2) throws SQLException {
    }
    
}
