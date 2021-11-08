package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.repositories.AdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdController {

    private final AdRepository adRepository;

    public AdController(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @GetMapping("/ads")
    public String showAds(Model model) {
        model.addAttribute("ads", adRepository.findAll());
        return "ads/index";
    }

//    @GetMapping("/ads/{id}")
//    @ResponseBody
//    public String showSingleAd(@PathVariable long id) {
//        return "Showing ad: " + id;
//    }

//    @GetMapping("/ads/{title}")
//    @ResponseBody
//    public Ad getByTitle(@PathVariable String title) {
//        return adRepository.findByTitle(title);
//    }

    @GetMapping("/ads/{titlePart}")
    @ResponseBody
    public List<Ad> getByTitle(@PathVariable String titlePart) {
        return adRepository.findByTitleLike(titlePart);
    }

    @PostMapping("/ads")
    @ResponseBody
    public String createAd(@RequestBody Ad newAd) {
        adRepository.save(newAd);
        return String.format("Ad created with an ID of: %s", newAd.getId());
    }

    @GetMapping("/ads/create")
    public String showCreateAdsForm(Model model){
        model.addAttribute("ad", new Ad());
        return "ads/create";
    }
    @PostMapping("/ads/create")
    public String createAdWithForm(@ModelAttribute Ad ad){
        adRepository.save(ad);
        return "redirect:/ads";
    }

}
