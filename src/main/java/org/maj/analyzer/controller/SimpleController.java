package org.maj.analyzer.controller;

import org.maj.analyzer.model.Symbol;
import org.maj.analyzer.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public Symbol decide(@PathVariable String symbol){
        return analyzeService.takeADecision(symbol);
    }
}
