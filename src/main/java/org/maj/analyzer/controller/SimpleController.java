package org.maj.analyzer.controller;

import org.maj.analyzer.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shamik.majumdar
 */
@RestController
public class SimpleController {
    @Autowired
    private AnalyzeService analyzeService;

    @RequestMapping("/")
    public String hello(){
        return "Hello World";
    }

    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    public String decide(@PathVariable String symbol){
        return analyzeService.takeADecision(symbol).name();
    }
}
