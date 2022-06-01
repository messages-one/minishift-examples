package org.melsoft.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class GreeterController {

	Logger log = Logger.getLogger("greeterLogger");
	 
    @Value("${greeter.message}")
    private String greeterMessageFormat; 

    @GetMapping("/greet/{user}")
    public String greet(@PathVariable("user") String user) {
        String prefix = System.getenv().getOrDefault("GREETING_PREFIX", "Hi");
        log.info( prefix + " " + user);
        if (prefix == null) {
            prefix = "Hello!";
        }
        return String.format(greeterMessageFormat, prefix, user);
    }
}
