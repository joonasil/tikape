
package tikape.tikapeforum.database.taulut;

import java.sql.Timestamp;
import java.util.*;

public class Keskustelualue {
    
    private Integer id;
    private String nimi;
    private List<Keskustelu> keskustelut;

    public Keskustelualue(Integer alueId, String nimi) {
        this.id = alueId;
        this.nimi = nimi;
        this.keskustelut = new ArrayList();
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setAlueId(Integer alueId) {
        this.id = alueId;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void lisaaKeskustelu(Keskustelu keskustelu){
        this.keskustelut.add(keskustelu);
    }
    
    public List<Keskustelu> getKeskustelut() {
        return this.keskustelut;
    }
    
    public Timestamp getViimeisinViesti() {
        
        Timestamp viimeisin = null;
        for (Keskustelu k : this.keskustelut) {
            
            if (viimeisin == null) {
                viimeisin = k.getViimeisin();
                continue;
            }
            
            if(k.getViimeisin() != null) {
                if (viimeisin.before(k.getViimeisin())) {
                    viimeisin = k.getViimeisin();
                }
            }
            
        }
        
        return viimeisin;
    }
    
}
