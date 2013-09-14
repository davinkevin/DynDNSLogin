package lan.dk.dyndnschecker;

import com.beust.jcommander.Parameter;

import java.net.MalformedURLException;
import java.net.URL;

public class CliParameter {

    @Parameter(names = "-login", description = "the login to use")
    String login = "your_login";

    @Parameter(names = "-password", description = "the password to use")
    String password = "your_password";

    @Parameter(names = "-formurl", description = "Path to the default form URL")
    String formURL = "https://account.dyn.com/entrance/";

    public String getFormURL() {
        return formURL;
    }

    public URL getFormURLasURL() throws MalformedURLException {
        return new URL(this.getFormURL());
    }
    public void setFormURL(String formURL) {
        this.formURL = formURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
