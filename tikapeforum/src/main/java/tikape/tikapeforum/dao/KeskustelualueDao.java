package tikape.tikapeforum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.tikapeforum.database.Database;
import tikape.tikapeforum.database.taulut.Keskustelualue;
import tikape.tikapeforum.database.taulut.Viesti;

public class KeskustelualueDao implements Dao<Keskustelualue, String> {

    private Database database;

    public KeskustelualueDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelualue findOne(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE nimi = ?");
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
    public void delete(String key) throws SQLException {
        //ei toteutettu vielä
    }

    @Override
    public int insert(String... nimi) throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt
                = connection.prepareStatement("INSERT INTO Keskustelualue(nimi)"
                        + "VALUES(?)");

        stmt.setObject(1, nimi[0]);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
        
        return 0;
    }

}
