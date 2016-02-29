
package tikape.tikapeforum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Viesti;
import tikape.tikapeforum.database.Database;
import tikape.tikapeforum.dao.Dao;
import tikape.tikapeforum.dao.KeskusteluDao;
import tikape.tikapeforum.dao.ViestiDao;

import static spark.Spark.*;
import tikape.tikapeforum.dao.KeskustelualueDao;
import tikape.tikapeforum.database.taulut.Keskustelualue;
import tikape.tikapeforum.yhdistajat.AlueYhdistaja;
import tikape.tikapeforum.yhdistajat.KeskusteluYhdistaja;

public class Main {
    public static void main(String[] args) throws Exception{
        
        Database database = new Database("jdbc:sqlite:forum.db");
        Dao<Keskustelualue, String> alueDao = new KeskustelualueDao(database);
        Dao<Keskustelu, Integer> keskusteluDao = new KeskusteluDao(database);
        Dao<Viesti, String> viestiDao = new ViestiDao(database, keskusteluDao);

        List<Keskustelualue> alueet = alueDao.findAll();
        List<Keskustelu> keskustelut = keskusteluDao.findAll();
        List<Viesti> viestit = viestiDao.findAll();
        
        KeskusteluYhdistaja k = new KeskusteluYhdistaja(keskustelut, viestit);
        k.yhdista();
        
        AlueYhdistaja a= new AlueYhdistaja(alueet, keskustelut);
        a.yhdista();
                
        get("/", (req, res) -> {
            
            String alueJono = "";
            for (Keskustelualue alue : alueet) {
                
                int maara = 0;
                for (Keskustelu keskustelu : alue.getKeskustelut()) {
                    maara += keskustelu.getViestit().size();
                }
                
                if (alue.getViimeisinViesti() != null) {
                    Timestamp viimeisinViesti = alue.getViimeisinViesti();
                    alueJono += alue.getNimi() + " " + maara + " " + viimeisinViesti.toString() + "<br/>";
                } else {
                    alueJono += alue.getNimi() + " " + maara + " " + "<br/>";
                }

            }
            
            return "<h2>Keskustelualueet:</h2>"
                   + "Alue Viestejä yhteensä Viimeisin viesti<br/>"
                   + alueJono
                   + "<br/>"
                   + "<form method=\"POST\" action=\"/\">\n"
                   + "<h2>Keskustelualueen lisäys:</h2>"
                   + "<p>Anna alueen nimi:</p>"
                   + "<input type=\"text\" name=\"alue\">\n"
                   + "<br/><br/>"
                   + "<input type=\"submit\" value=\"Lisää alue\">"
                   + "<form>";
        });
        
        post("/", (req, res) -> {
            
            String alueNimi = req.queryParams("alue");
            alueDao.insert(alueNimi);
            alueet.add(alueDao.findOne(alueNimi));
            
            return "Alue lisätty!<br/><br/>"
                    + "Palaa <a href=\"http://localhost:4567/\">foorumiin</a>";
            
        });
        
    }
    
}
