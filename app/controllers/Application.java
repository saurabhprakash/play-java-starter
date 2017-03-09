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

        // Set a test session value
        session("connected", "user@gmail.com");

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

    public Result sessionTest() {
        String user = session("connected");
        if(user != null) {
            return ok("Hello " + user);
        } else {
            return unauthorized("Oops, you are not connected");
        }
    }
    
    //http://localhost:9000/response-test
    //Test html response
    public Result resposeTest() {
        //Setting HTTP response headers
        response().setHeader(CACHE_CONTROL, "max-age=3600");
        response().setHeader(ETAG, "xxx");
        
        //add a Cookie to the HTTP response
        response().setCookie("theme", "blue");
        response().setCookie(
            "theme",        // name
            "blue",         // value
            3600,           // maximum age
            "/some/path",   // path
            ".example.com", // domain
            false,          // secure
            true            // http only
        );
        
        //discard a Cookie
        response().discardCookie("theme");
        
        //Specifying the character encoding for text results
        return ok("<h1>Hello World!</h1>", "iso-8859-1").as("text/html; charset=iso-8859-1");
        
        //html response
        //return ok("<h1>Hello World!</h1>").as("text/html");
    }
}
