package org.maj.analyzer.controller;

import org.maj.analyzer.model.Symbol;
import org.maj.analyzer.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shamik.majumdar
 */
@RestController
@RequestMapping("/rest")
public class SimpleController {
    @Autowired
    private AnalyzeService analyzeService;

    @RequestMapping(value = "/symbol/{symbol}", method = RequestMethod.GET)
    @ResponseBody
    public Symbol decide(@PathVariable String symbol){
        return analyzeService.takeADecision(symbol);
    }
}
