package tikape.tikapeforum;

import java.sql.Timestamp;
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

        get("/", (req, res) -> {

            HashMap map = new HashMap();
            ArrayList alueLista = new ArrayList();

            for (Keskustelualue alue : alueet) {
                alue.selvitaViestienMaara();
                alue.selvitaViimeisinViesti();
                alueLista.add(alue);
            }

            map.put("alueet", alueLista);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());;

        get("/keskustelut", (req, res) -> {

            HashMap map = new HashMap();
            ArrayList keskusteluLista = new ArrayList();

            for (Keskustelu keskustelu : keskustelut) {
                keskustelu.getViestit().size();
                keskustelu.getViimeisin();
                keskusteluLista.add(keskustelu);
            }

            map.put("keskustelut", keskusteluLista);

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());;

        post("/", (req, res) -> {

            String alueNimi = req.queryParams("alue");
            alueDao.insert(alueNimi);
            alueet.add(alueDao.findOne(alueNimi));

            return "Alue lisätty!<br/><br/>"
                    + "Palaa <a href=\"http://localhost:4567/\">keskustelufoorumiin.</a>";

        });

    }

}
