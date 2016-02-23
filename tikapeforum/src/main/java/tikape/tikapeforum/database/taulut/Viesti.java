
package tikape.tikapeforum.database.taulut;

public class Viesti {
    
    private String sisalto;
    private String nimiMerkki;
    private Keskustelu keskustelu;
    
    public Viesti(String sisalto, String nimiMerkki) {
        this.sisalto = sisalto;
        this.nimiMerkki = nimiMerkki;
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
    
    public void lisaaKeskusteluun(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }
    
}