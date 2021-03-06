package com.mjs.gossiper.controller;


import com.mjs.gossiper.business.StatsBo;
import com.mjs.gossiper.domain.BasicAccount;
import com.mjs.gossiper.domain.PlayerStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/stats")
public class StatsController {

    @Autowired
    private StatsBo statsBo;

    @RequestMapping(method = RequestMethod.GET, value = "/consolidate")
    public PlayerStat getConsolidate(BasicAccount basicAccount){
        return statsBo.getConsolidate(basicAccount);
    }
}