package at.htl.webuntis.business;

import at.htl.WebUntisService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

@ApplicationScoped
public class InitBean {
    public void init(@Observes @Initialized(ApplicationScoped.class)Object object){
        try {
            new WebUntisService().start();
        } catch (FileNotFoundException e) {
            System.out.println("config.yaml not found in root directory");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
