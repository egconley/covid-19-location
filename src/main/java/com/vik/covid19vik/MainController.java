package com.vik.covid19vik;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedList;

// ================ render templates ================= //
@Controller
class MainController {

    // consider adding to database:
    // country global data
    // uiflookup data

    // hashmap of already looked up countries
    HashMap<String, Integer> lookedUpCountries = new HashMap<>();

    @GetMapping("/")
    String getIndex(Model model, HttpServletRequest req) {

        LinkedList<String> countryDropdown = Dropdowns.createCountryDropdown(req);

        model.addAttribute("countryNames", countryDropdown);
        return "index";
    }

    // post request with user's country name search
    @PostMapping("/results/country")
    RedirectView submitCountrySearch(String searchedCountry) {

        System.out.println("Dropdown selected = " + searchedCountry);

        return new RedirectView("/results/country?sc=" + searchedCountry);
    }

//    @PostMapping("/results/country/province")
//    public RedirectView submitProvinceSearch(String searchedProvince, String countryForProvince) {
//        return rv;
//    }

    @GetMapping("/results/country")
    String resultsForCountry(@RequestParam(name = "sc") String sc, Model model, HttpServletRequest req) throws IOException {

        System.out.println("searched country = " + sc);

        // set up time series data to pass in

        // [
        // [confirmed new],
        // [confirmed total],
        // [deaths new],
        // [deaths total],
        // [recov new],
        // [recov total]
        // ]

        Integer[][] caseInfoForCountry = new Integer[6][1];

        System.out.println("getmapping = " + req.getRequestURL().toString());
        LinkedList<String> countryDropdown = Dropdowns.createCountryDropdown(req);

        model.addAttribute("caseInfoForCountry", caseInfoForCountry);
        model.addAttribute("searchedCountry", sc);
        model.addAttribute("countryNames", countryDropdown);
        return "countryResults";
    }

    @GetMapping("/results/country/province")
    String resultsForProvince(Model model, HttpServletRequest req) {

        LinkedList<String> countryDropdown = Dropdowns.createCountryDropdown(req);

        model.addAttribute("countryNames", countryDropdown);
        return "provinceResults";
    }

    // error for slug
    @GetMapping("/error/404/{searchedCountry}")
    String error404(Model model, HttpServletRequest req) {

        LinkedList<String> countryDropdown = Dropdowns.createCountryDropdown(req);
        model.addAttribute("countryNames", countryDropdown);

        return "error404";
    }

    // fallback
    @GetMapping("*")
    String fallback(Model model, HttpServletRequest req) {

        LinkedList<String> countryDropdown = Dropdowns.createCountryDropdown(req);
        model.addAttribute("countryNames", countryDropdown);

        return "error";
    }
}
