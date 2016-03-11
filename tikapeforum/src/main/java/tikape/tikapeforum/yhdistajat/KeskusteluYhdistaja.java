package tikape.tikapeforum.yhdistajat;

import java.util.List;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Viesti;

public class KeskusteluYhdistaja {

    private List<Viesti> viestit;
    private List<Keskustelu> keskustelut;

    public KeskusteluYhdistaja(List<Keskustelu> keskustelut, List<Viesti> viestit) {
        this.viestit = viestit;
        this.keskustelut = keskustelut;
    }

    public void yhdista() {
        for (Keskustelu keskustelu : keskustelut) {
            keskustelu.clear();
        }
        for (Viesti viesti : viestit) {
            for (Keskustelu keskustelu : keskustelut) {
                if (keskustelu.getId() == viesti.getKeskusteluId()) {
                    keskustelu.lisaaViesti(viesti);
                }
            }
        }
    }

    public void setKeskustelut(List<Keskustelu> keskustelut) {
        this.keskustelut = keskustelut;
    }
    
}
