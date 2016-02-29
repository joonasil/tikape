
package tikape.tikapeforum.database.taulut;

import java.sql.Timestamp;

public class Viesti {
    
    private String sisalto;
    private String nimiMerkki;
    private int keskusteluId;
    private Timestamp aika;
    
    public Viesti(String sisalto, String nimiMerkki, int keskusteluId, Timestamp aika) {
        this.sisalto = sisalto;
        this.nimiMerkki = nimiMerkki;
        this.keskusteluId = keskusteluId;
        this.aika = aika;
    }
    
    public String getSisalto() {
        return this.sisalto;
    }
    
    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }
    
    public String getNimim() {
        return this.nimiMerkki;
    }
    
    public void setNimiMerkki(String nimim) {
        this.nimiMerkki = nimim;
    }
    
    public int getKeskusteluId() {
        return this.keskusteluId;
    }
    
    public Timestamp getAika() {
        return this.aika;
    }
    
    
}