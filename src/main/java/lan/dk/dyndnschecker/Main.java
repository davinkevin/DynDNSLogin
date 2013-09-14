package lan.dk.dyndnschecker;

import com.beust.jcommander.JCommander;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Lancement");

        Map parametersToPostMap = new HashMap<String, String>();
        Connection.Response res = null;

        CliParameter cliParameter = new CliParameter();
        new JCommander(cliParameter, args);

        Document doc = null;
        try {
            res = Jsoup.connect(cliParameter.getFormURL()).execute();
            doc = res.parse();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            logger.error(e.getMessage());
            System.exit(0);
        }

        Elements forms = doc.select("form[action=/entrance/]").first().select("input[type!=submit]");
        //Elements forms = doc.select("input");
        for (Element element : forms) {
            logger.debug(element.attr("name"));
            if(element.attr("name").equalsIgnoreCase("username")) {
              parametersToPostMap.put(element.attr("name"), cliParameter.getLogin());
            } else if (element.attr("name").equalsIgnoreCase("password")) {
                parametersToPostMap.put(element.attr("name"), cliParameter.getPassword());
            } else {
                parametersToPostMap.put(element.attr("name"), element.val());
            }
        }

        try {
            logger.info("Connection à : " + cliParameter.getFormURLasURL().getProtocol() + "://" + cliParameter.getFormURLasURL().getHost());
            Document doc2 = Jsoup.connect(cliParameter.getFormURLasURL().getProtocol() + "://" + cliParameter.getFormURLasURL().getHost())
                    .cookies(res.cookies())
                    .get();
            logger.debug(doc2.select("title").first().text());
            logger.info("Connection effectuée");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } 


    }

}
