package configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	 @Override
	    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
	        return builder.sources(Application.class);
	    }

	    public static void main (String[] args) {
	        SpringApplication sa = new SpringApplication(Application.class);
	        sa.setLogStartupInfo(false);
	        sa.run(args);
	    }

	    @Controller
	    public static class MyController {

	    	 @RequestMapping(value={"/","/home"})
	    	    public String showHome() {
	    	    /*	List<NewsConten> news = newsService.findAllNews();
	    	 		model.addAttribute("news", news);
	    	 		List<UserDocument> listdocuments = userDocumentService.findAll();
	    			model.addAttribute("listdocuments", listdocuments);*/
	    	        return "pages/menu_home";
	    	    }
	    }
}
