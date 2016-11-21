package controllers;

import models.Person;
import play.*;
import play.data.Form;
import play.db.jpa.*;
import play.mvc.*;
import views.html.*;
import play.libs.Json;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.List;
import java.io.IOException;

import static play.libs.Json.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    @Transactional
    public Result addPerson() {
        Person person = Form.form(Person.class).bindFromRequest().get();
        JPA.em().persist(person);
        return redirect(routes.Application.index());
    }

    @Transactional(readOnly = true)
    public Result getPersons() {
        List<Person> persons = (List<Person>) JPA.em().createQuery("select p from Person p").getResultList();
        return ok(toJson(persons));
    }

    @Transactional(readOnly = true)
    public Result getName() {
        return ok("saurabh");
    }

//    @Transactional(readOnly = true)
//    public Result getNameJSON() {
//        return ok({
//                name: 'saurabh'
//        });
//    }

    public Result getNameJSON() {
        ObjectNode result = Json.newObject();
        result.put("fname", "saurabh");
        result.put("lname", "prakash");
        return ok(result);
    }

    public Result scrape() throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/Boston").get();
        Element contentDiv = doc.select("div[id=content]").first();
        return ok(contentDiv.toString());
    }
}
