
package tikape.tikapeforum.database.taulut;

import java.sql.Timestamp;
import java.util.*;

public class Keskustelualue {
    
    private int id;
    private String nimi;
    private List<Keskustelu> keskustelut;
    private int viestienMaara;
    private Timestamp viimeisinViesti;

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
    
    public int getViestienMaara() {
        return this.viestienMaara;
    }
    
    public Timestamp getViimeisinViesti() {
        return this.viimeisinViesti;
    }
    
    public void selvitaViimeisinViesti() {
        
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
        
        this.viimeisinViesti = viimeisin;
    }
    
    public void selvitaViestienMaara() {
        int maara = 0;
        for (Keskustelu k : this.keskustelut) {
            maara += k.getViestit().size();
        }
        this.viestienMaara = maara;
    }
    
}
