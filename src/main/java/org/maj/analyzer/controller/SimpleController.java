package org.maj.analyzer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shamik.majumdar
 */
@RestController
public class SimpleController {
    @RequestMapping("/")
    public String hello(){
        return "Hello World";
    }
}
