
package tikape.tikapeforum;

import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Viesti;
import tikape.tikapeforum.database.Database;
import tikape.tikapeforum.dao.Dao;
import tikape.tikapeforum.dao.KeskusteluDao;
import tikape.tikapeforum.dao.ViestiDao;

import static spark.Spark.*;

public class Main {
    
    public static void main(String[] args) throws Exception{
        
        Database database = new Database("jdbc:sqlite:tikapeForum.db");
        Dao<Keskustelu, Integer> keskusteluDao = new KeskusteluDao(database);
        Dao<Viesti, String> viestiDao = new ViestiDao(database, keskusteluDao);

        Viesti viesti = viestiDao.findOne("Juho");      
        
        get("/viesti", (req, res) -> {
            return viesti.getSisalto() + " " + viesti.getNimim();
        });
    }
    
}
