package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Coffee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CoffeeController {

    @GetMapping("/coffee")
    public String coffeeInfo() {
        return "views-lecture/coffee";
    }

//    @GetMapping("/coffee/{roast}")
//    public String roastSelection(@PathVariable String roast, Model model) {
//        model.addAttribute("roast", roast);
//        boolean choseDark = false;
//        if (roast.equals("dark")) {
//            choseDark = true;
//        }
//        model.addAttribute("choseDark", choseDark);
//        return "views-lecture/coffee";
//    }

    @GetMapping("/coffee/{roast}")
    public String roastSelection(@PathVariable String roast, Model model) {
        Coffee selection = new Coffee(roast, "Cool Beans");
        Coffee selectionTwo = new Coffee(roast, "Jolting Joe");

        selection.setRoast(roast);

        if (roast.equals("dark")) {
            selection.setOrigin("Colombia");
            selectionTwo.setOrigin("Brazil");
        } else if (roast.equals("medium")) {
            selection.setOrigin("New Guinea");
            selectionTwo.setOrigin("Sumatra");
        } else {
            selection.setOrigin("Kenya");
            selectionTwo.setOrigin("Ethiopia");
        }

        List<Coffee> selections = new ArrayList<>();

        selections.add(selection);
        selections.add(selectionTwo);

        model.addAttribute("roast", roast);
        model.addAttribute("selections", selections);

        return "views-lecture/coffee";
    }

}
