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

    public static void paivita(KeskusteluYhdistaja k, AlueYhdistaja a) {
        k.yhdista();
        a.yhdista();

    }

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:forum.db");

        Dao<Keskustelualue, Integer> alueDao = new KeskustelualueDao(database);
        Dao<Keskustelu, Integer> keskusteluDao = new KeskusteluDao(database, alueDao);
        Dao<Viesti, String> viestiDao = new ViestiDao(database, keskusteluDao);

        List<Keskustelualue> alueet = alueDao.findAll();
        List<Keskustelu> keskustelut = keskusteluDao.findAll();
        List<Viesti> viestit = viestiDao.findAll();

        KeskusteluYhdistaja k = new KeskusteluYhdistaja(keskustelut, viestit);
        AlueYhdistaja a = new AlueYhdistaja(alueet, keskustelut);
        paivita(k, a);

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
            int alueId = alueDao.insert(alueNimi);
            alueet.add(alueDao.findOne(alueId));
            paivita(k, a);
            res.redirect("/");
            return "";
        });

        // KESKUSTELUALUEELLA OLEVIEN KESKUSTELUJEN LISTAUS JA LISÄYS
        get("/alue/:id", (req, res) -> {

            HashMap map = new HashMap();
            List keskusteluLista = new ArrayList();

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
//            for(Keskustelu keskustelu : keskustelut) {
//                if (keskustelu.getAlueId()==alueId) {
//                    keskusteluLista.add(keskustelu);
//                }
//            }
            keskusteluLista = keskusteluDao.findTen(alueId, 1);
            a.setKeskustelut(keskusteluLista);
            k.setKeskustelut(keskusteluLista);
            paivita(k, a);
            // Laitetaan tiedot mappiin ja annetaan thymeleafille
            map.put("alueId", alueId);
            map.put("alue", alueNimi);
            map.put("keskustelut", keskusteluLista);

            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());;

        // lisätään alueelle keskustelu ja palataan keskustelualueen sivulle
        post("/alue/:id", (req, res) -> {

            int uudenId = keskusteluDao.insert(req.params(":id"), req.queryParams("otsikko"));
            keskustelut.add(keskusteluDao.findOne(uudenId));

            String uudenSisalto = viestiDao.insert1("" + uudenId, req.queryParams("sisalto"), req.queryParams("nimimerkki"));
            viestit.add(viestiDao.findOne(uudenSisalto));

            paivita(k, a);
            res.redirect("/alue/" + req.params(":id"));
            return "";
        });

        // KESKUSTELUSSA OLEVIEN VIESTIEN LISTAUS JA LISÄYS
        get("/keskustelu/:id", (req, res) -> {

            HashMap map = new HashMap();
            ArrayList viestiLista = new ArrayList();

            // Muodostetaan keskustelun nimi otsikoksi html-templateen
            String keskusteluOtsikko = "";
            int keskusteluId = Integer.parseInt(req.params(":id"));
            int alueId = 0;
            for (Keskustelu keskustelu : keskustelut) {
                if (keskustelu.getId() == keskusteluId) {
                    Keskustelualue alue = alueDao.findOne(keskustelu.getAlueId());
                    alueId = alue.getId();
                    keskusteluOtsikko += alue.getNimi() + " -> " + keskustelu.getOtsikko();
                }
            }

            // Valitaan näytettäviksi vain ne viestit,
            // joiden keskusteluId vastaa valittua keskustelua
            for (Viesti viesti : viestit) {
                if (viesti.getKeskusteluId() == keskusteluId) {
                    viesti.getKeskusteluId();
                    viesti.getSisalto();
                    viesti.getNimim();
                    viestiLista.add(viesti);
                }
            }

            // Laitetaan tiedot mappiin ja annetaan thymeleafille
            map.put("keskusteluId", keskusteluId);
            map.put("alueId", alueId);
            map.put("otsikko", keskusteluOtsikko);
            map.put("viestit", viestiLista);

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());;

        // lisätään keskusteluun viesti ja palataan keskustelun sivulle
        post("/keskustelu/:id", (req, res) -> {

            String uudenSisalto = viestiDao.insert1(req.params(":id"), req.queryParams("sisalto"), req.queryParams("nimimerkki"));
            viestit.add(viestiDao.findOne(uudenSisalto));
            paivita(k, a);
            res.redirect("/keskustelu/" + req.params(":id"));
            return "";
        });

    }
}
