
package tikape.tikapeforum.database.taulut;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Keskustelu {
    
    private int id;
    private int alueId;
    private String otsikko;
    private List<Viesti> viestit;
    
    public Keskustelu(String otsikko, int id, int alueId) {
        this.otsikko = otsikko;
        this.viestit = new ArrayList();
        this.id = id;
        this.alueId = alueId;
    }
    
    public String getOtsikko() {
        return this.otsikko;
    }
    
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    public void lisaaViesti(Viesti viesti) {
        this.viestit.add(viesti);
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getAlueId() {
        return this.alueId;
    }
    
    public List<Viesti> getViestit() {
        return this.viestit;
    }
    
    public Timestamp getViimeisin() {
        
        Timestamp viimeisin = null;
        for (Viesti v : this.viestit) {
            if (viimeisin == null) {
                viimeisin = v.getAika();
                continue;
            }
            
            if (v.getAika() != null) {
                if (viimeisin.before(v.getAika())) {
                    viimeisin = v.getAika();
                }
            }
            
        }
        
        return viimeisin;
    }
    
}
