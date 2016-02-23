
package tikape.tikapeforum.database.taulut;

import java.util.ArrayList;
import java.util.List;

public class Keskustelu {
    
    private String otsikko;
    private List<Viesti> viestit;
    
    public Keskustelu(String otsikko) {
        this.otsikko = otsikko;
        this.viestit = new ArrayList();
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
    
}
