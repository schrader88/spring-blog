package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiceRollController {

    @GetMapping("/roll-dice")
    public String diceRollIndex() {
        return "roll-dice";
    }
}
