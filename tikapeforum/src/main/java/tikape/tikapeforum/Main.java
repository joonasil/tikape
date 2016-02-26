
package tikape.tikapeforum;

import java.util.List;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Viesti;
import tikape.tikapeforum.database.Database;
import tikape.tikapeforum.dao.Dao;
import tikape.tikapeforum.dao.KeskusteluDao;
import tikape.tikapeforum.dao.ViestiDao;

import static spark.Spark.*;

public class Main {
    
    public static void main(String[] args) throws Exception{
        
        Database database = new Database("jdbc:sqlite:forum.db");
        Dao<Keskustelu, Integer> keskusteluDao = new KeskusteluDao(database);
        Dao<Viesti, String> viestiDao = new ViestiDao(database, keskusteluDao);

        List<Viesti> viestit = viestiDao.findAll();      
        
        get("/viestit", (req, res) -> {
            
            String viestiJono = "";
            for (Viesti viesti : viestit) {
                viestiJono += viesti.getSisalto() + " t. " + viesti.getNimim() + "<br/>";
            }
            
            return "<h2>Tietokannassa olevat viestit:</h2>"
                   + viestiJono
                   + "<br/>"
                   + "<form method=\"POST\" action=\"/viestit\">\n"
                   + "<p>Kirjoita viesti:</p>"
                   + "<input type=\"text\" name=\"viesti\">\n"
                   + "<p>Anna nimimerkki:</p>"
                   + "<input type=\"text\" name=\"nimimerkki\">"
                   + "<br/><br/>"
                   + "<input type=\"submit\" value=\"Lisää viesti\">"
                   + "<form>";
        });
        
        post("/viestit", (req, res) -> {
            
            String viesti = req.queryParams("viesti");
            String nimim = req.queryParams("nimimerkki");
            
            viestiDao.insert(viesti, nimim);
            viestit.add(new Viesti(viesti, nimim));
            
            return "Viesti lähetetty!<br/><br/>"
                    + "Palaa <a href=\"http://localhost:4567/viestit\">tietokannan viesteihin</a>";
            
        });
        
    }
    
}
