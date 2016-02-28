
package tikape.tikapeforum.database.taulut;

import java.util.*;

public class Keskustelualue {
    
    private Integer alueId;
    private String nimi;
    private List<Keskustelu> keskustelut;

    public Keskustelualue(Integer alueId, String nimi) {
        this.alueId = alueId;
        this.nimi = nimi;
        this.keskustelut = new ArrayList();
    }

    public Integer getAlueId() {
        return alueId;
    }

    public String getNimi() {
        return nimi;
    }

    public void setAlueId(Integer alueId) {
        this.alueId = alueId;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void lisaaKeskustelu(Keskustelu keskustelu){
        this.keskustelut.add(keskustelu);
    }
    
}
