package tikape.tikapeforum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import tikape.tikapeforum.database.taulut.Keskustelu;
import tikape.tikapeforum.database.taulut.Viesti;
import tikape.tikapeforum.database.Database;
import tikape.tikapeforum.dao.Dao;
import tikape.tikapeforum.dao.KeskusteluDao;
import tikape.tikapeforum.dao.ViestiDao;

import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.tikapeforum.dao.KeskustelualueDao;
import tikape.tikapeforum.database.taulut.Keskustelualue;
import tikape.tikapeforum.yhdistajat.AlueYhdistaja;
import tikape.tikapeforum.yhdistajat.KeskusteluYhdistaja;

public class Main {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:forum.db");

        Dao<Keskustelualue, String> alueDao = new KeskustelualueDao(database);
        Dao<Keskustelu, Integer> keskusteluDao = new KeskusteluDao(database);
        Dao<Viesti, String> viestiDao = new ViestiDao(database, keskusteluDao);

        List<Keskustelualue> alueet = alueDao.findAll();
        List<Keskustelu> keskustelut = keskusteluDao.findAll();
        List<Viesti> viestit = viestiDao.findAll();

        KeskusteluYhdistaja k = new KeskusteluYhdistaja(keskustelut, viestit);
        k.yhdista();

        AlueYhdistaja a = new AlueYhdistaja(alueet, keskustelut);
        a.yhdista();
        
        
        // KESKUSTELUALUEIDEN LISTAUS JA LISÄYS
        get("/", (req, res) -> {

            HashMap map = new HashMap();
            ArrayList alueLista = new ArrayList();

            // Valitaan näytettäviksi kaikki keskustelualueet
            for (Keskustelualue alue : alueet) {
                alue.selvitaViestienMaara();
                alue.selvitaViimeisinViesti();
                alueLista.add(alue);
            }
            
            // Laitetaan tiedot mappiin ja annetaan thymeleafille
            map.put("alueet", alueLista);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());;
        
        // Lisätään annettujen tietojen mukainen keskustelualue ja palataan
        // foorumin pääsivulle
        post("/", (req, res) -> {

            String alueNimi = req.queryParams("alue");
            alueDao.insert(alueNimi);
            alueet.add(alueDao.findOne(alueNimi));
            
            res.redirect("/");
            return "";
        });
        
        
        // KESKUSTELUALUEELLA OLEVIEN KESKUSTELUJEN LISTAUS JA LISÄYS
        get("/alue/:id", (req, res) -> {
            
            HashMap map = new HashMap();
            ArrayList keskusteluLista = new ArrayList();

            // Muodostetaan keskustelualueen nimi otsikoksi html-templateen
            String alueNimi = "Keskustelualue: ";
            int alueId = Integer.parseInt(req.params(":id"));
            for (Keskustelualue alue : alueet) {
                if (alue.getId() == alueId) {
                    alueNimi += alue.getNimi();
                }
            }
            
            // Valitaan näytettäviksi vain ne keskustelut,
            // joiden alueId vastaa valittua aluetta
            for (Keskustelu keskustelu : keskustelut) {
                if (keskustelu.getAlueId() == alueId) {
                    keskustelu.getViestit().size();
                    keskustelu.getViimeisin();
                    keskusteluLista.add(keskustelu); 
                }
            }
            
            // Laitetaan tiedot mappiin ja annetaan thymeleafille
            map.put("alue", alueNimi);
            map.put("keskustelut", keskusteluLista);

            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());;
        
        // Tämä ei tee vielä mitään muuta kuin ohjaa takaisin keskustelualueen sivulle
        post("/alue/:id", (req, res) -> {
           
            res.redirect("/alue/:id");
            return ""; 
        });

    }

}
