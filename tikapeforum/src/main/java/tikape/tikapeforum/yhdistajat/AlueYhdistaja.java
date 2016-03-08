package tikape.tikapeforum.yhdistajat;

import java.util.ArrayList;
import java.util.List;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Keskustelualue;

public class AlueYhdistaja {

    private List<Keskustelualue> alueet;
    private List<Keskustelu> keskustelut;

    public AlueYhdistaja(List<Keskustelualue> alueet, List<Keskustelu> keskustelut) {
        this.alueet = alueet;
        this.keskustelut = keskustelut;
    }

    public void yhdista() {
        for (Keskustelualue alue : alueet) {
            alue.clear();
        }
        for (Keskustelu keskustelu : keskustelut) {
            for (Keskustelualue alue : alueet) {
                if (keskustelu.getAlueId() == alue.getId()) {
                    alue.lisaaKeskustelu(keskustelu);
                }
            }
        }
    }

}
