
package tikape.tikapeforum.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.tikapeforum.database.taulut.Viesti;

public class ViestiKeraaja implements Collector<Viesti> {

    @Override
    public Viesti collect(ResultSet rs) throws SQLException {
        
        String sisalto = rs.getString("sisalto");
        String nimiMerkki = rs.getString("nimimerkki");
        
        return new Viesti(sisalto, nimiMerkki);        
    }
    
}
