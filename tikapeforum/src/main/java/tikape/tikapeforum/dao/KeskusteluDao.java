package tikape.tikapeforum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tikape.tikapeforum.database.*;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Keskustelualue;
import tikape.tikapeforum.database.taulut.Viesti;


public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;
    private KeskustelualueDao alueDao;

    public KeskusteluDao(Database database, Dao<Keskustelualue, Integer> alueDao) {
        this.database = database;
        alueDao = alueDao;

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

        while (rs.next()) {

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
    public int insert(String... otsikko) throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt
                = connection.prepareStatement("INSERT INTO Keskustelu(alueId, otsikko)"
                        + "VALUES(?, ?)");

        stmt.setObject(1, Integer.parseInt(otsikko[0]));
        stmt.setObject(2, otsikko[1]);
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

//    @Override
//    public List<Keskustelu> liittyvatObjektit(Integer key) throws SQLException {
//        Connection connection = this.database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE alueId=?");
//        stmt.setObject(1, key);
//        ResultSet rs = stmt.executeQuery();
//        List<Keskustelu> alueenKeskustelut = new ArrayList();
//
//        while (rs.next()) {
//
//            Integer id = rs.getInt("keskusteluId");
//            String otsikko = rs.getString("otsikko");
//
//            alueenKeskustelut.add(new Keskustelu(otsikko, id, key));
//        }
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return alueenKeskustelut;
//    }
    @Override
    public List<Keskustelu> findTen(Integer key, int sivunro) throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu LEFT JOIN Viesti ON Keskustelu.keskusteluId=Viesti.keskusteluId WHERE alueId=? GROUP BY Keskustelu.keskusteluId ORDER BY MIN(Viesti.aika) DESC LIMIT 10 OFFSET ?");
        stmt.setObject(1,key);
        stmt.setObject(2, (sivunro-1)*10);
        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList();

        while (rs.next()) {

            Integer id = rs.getInt("keskusteluId");
            String otsikko = rs.getString("otsikko");

            keskustelut.add(new Keskustelu(otsikko, id, key));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }
}
