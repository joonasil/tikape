
package tikape.tikapeforum.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.tikapeforum.database.taulut.Keskustelualue;

public class KeskustelualueKeraaja implements Collector<Keskustelualue>{
    
    @Override
    public Keskustelualue collect(ResultSet rs) throws SQLException {
        
        Integer alueId=rs.getInt("aalueId");
        String nimi = rs.getString("nimi");
        
        return new Keskustelualue(alueId, nimi);
    }
    
    
}
