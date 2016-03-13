package tikape.tikapeforum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tikape.tikapeforum.database.Database;
import tikape.tikapeforum.database.taulut.Keskustelualue;
import tikape.tikapeforum.database.taulut.Viesti;


public class KeskustelualueDao implements Dao<Keskustelualue, Integer> {

    private Database database;

    public KeskustelualueDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelualue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE alueId = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer alueId = rs.getInt("alueId");
        String nimi = rs.getString("nimi");

        Keskustelualue ka = new Keskustelualue(alueId, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return ka;
    }

    @Override
    public List<Keskustelualue> findAll() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");
        ResultSet rs = stmt.executeQuery();
        List<Keskustelualue> alue = new ArrayList();
        while (rs.next()) {
            Integer id = rs.getInt("alueId");
            String nimi = rs.getString("nimi");

            alue.add(new Keskustelualue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alue;
    }

    @Override
    public int insert(String... nimi) throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt
                = connection.prepareStatement("INSERT INTO Keskustelualue(nimi)"
                        + "VALUES(?)");

        stmt.setObject(1, nimi[0]);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int uudenId = rs.getInt(1);

        stmt.close();
        connection.close();

        return uudenId;
    }

    @Override
    public String insert1(String... keys) throws SQLException {
        return null;
    }
    
    @Override
    public List<Keskustelualue> findTen(Integer key, int sivunro) throws SQLException {
        return null;
    }

    @Override
    public Viesti insert2(String... keys) throws SQLException {
        return null;
    }

}
