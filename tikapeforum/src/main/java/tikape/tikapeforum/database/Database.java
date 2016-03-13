
package tikape.tikapeforum.database;

import java.sql.*;
import java.util.*;
import java.net.*;

public class Database<T> {
    
    String address;
    
    public Database(String address) throws Exception {
        this.address = address;
        
         init();
    }
    private void init() {
        List<String> lauseet = null;
        if (this.address.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }



    public Connection getConnection() throws SQLException {
                if (this.address.contains("postgres")) {
            try {
                URI dbUri = new URI(address);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(address);
    }
    
    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjÃ¤rjestyksessÃ¤
//        lista.add("DROP TABLE Keskustelualue;");
//        lista.add("DROP TABLE Keskustelu;");
//        lista.add("DROP TABLE Viesti;");
        // heroku kÃ¤yttÃ¤Ã¤ SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Keskustelu (keskusteluId SERIAL PRIMARY KEY,alueId integer, otsikko varchar(100) NOT NULL, FOREIGN KEY(alueId) REFERENCES Keskustelualue(alueId)");
        lista.add("CREATE TABLE Keskustelualue (alueId SERIAL PRIMARY KEY, nimi varchar(50) NOT NULL");
        lista.add("CREATE TABLE Viesti (keskusteluId integer, sisalto varchar(1000) NOT NULL, nimimerkki varchar(25) NOT NULL, aika timestamp NOT NULL, FOREIGN KEY(keskusteluId) REFERENCES Keskustelu(keskusteluId)");

        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjÃ¤rjestyksessÃ¤
        lista.add("CREATE TABLE Keskustelu (keskusteluId integer PRIMARY KEY,alueId integer, otsikko varchar(100) NOT NULL, FOREIGN KEY(alueId) REFERENCES Keskustelualue(alueId)");
        lista.add("CREATE TABLE Keskustelualue (alueId integer PRIMARY KEY, nimi varchar(50) NOT NULL");
        lista.add("CREATE TABLE Viesti (keskusteluId integer, sisalto varchar(1000) NOT NULL, nimimerkki varchar(25) NOT NULL, aika timestamp NOT NULL, FOREIGN KEY(keskusteluId) REFERENCES Keskustelu(keskusteluId)");

        return lista;
    }
}
    

