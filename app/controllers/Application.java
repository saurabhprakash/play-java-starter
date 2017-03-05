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

    //
    //    Returns sample JSON response + experiment with return types
    //
    public Result getNameJSON() {
        ObjectNode result = Json.newObject();
        result.put("fname", "saurabh");
        result.put("lname", "prakash");

        //return notFound();
        //return notFound("<h1>Page not found</h1>").as("text/html");
        //return badRequest(views.html.form.render(formWithErrors));
        //return internalServerError("Oops");
        //return status(488, "Strange response type");
        //return redirect("/persons");
        return ok(result);

    }

    //
    //    Returns sample web scraped data
    //
    public Result scrape() throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/Boston").get();
        Element contentDiv = doc.select("div[id=content]").first();
        return ok(contentDiv.toString());
    }

    //http://localhost:9000/check-call-params/saurabh
    public Result checkCallParams(String page) {
        //String content = Page.getContentOf(page);
        response().setContentType("text/html");
        return ok(page);
    }

    // http://localhost:9000/check1?page=1
    public Result checkCallParams2(Integer page) {
        //String content = Page.getContentOf(page);
        response().setContentType("text/html");
        return ok(page.toString());
    }
    
    //http://localhost:9000/response-test
    //Test html response
    public Result resposeTest() {
        return ok("<h1>Hello World!</h1>").as("text/html");
    }
}
