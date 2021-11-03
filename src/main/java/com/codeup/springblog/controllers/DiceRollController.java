package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DiceRollController {

    @GetMapping("/roll-dice")
    public String diceRollIndex() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{n}")
    public String diceRollResult(@PathVariable int n, Model model) {
        model.addAttribute("n", n);

        boolean equalsRandom = false;

        int min = 1;
        int max = 6;
        int random = (int) Math.floor(Math.random()*(max-min+1)+min);

        model.addAttribute("random", random);

        if (n == random) {
            equalsRandom = true;
        }

        model.addAttribute("equalsRandom", equalsRandom);

        return "roll-dice";
    }
}
