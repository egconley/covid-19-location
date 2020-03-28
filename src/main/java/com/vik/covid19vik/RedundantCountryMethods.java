package com.vik.covid19vik;

import java.util.*;

public class RedundantCountryMethods {

    // used to get a country name and get order of slugs that represents corrected order of data
    // NOTE: to obtain most relevant data for a country, use the slug at the end of the array instance for a given country. If there seem to be "typos" in country names, those
    // are intentional and actually account for typos in the JHU CSSE data.
    public static HashMap<String, String[]> getRedundantCountriesWithSlugs() {
        // redundant country hashmap: country name as key and array of possible slugs as values
        HashMap<String, String[]> redundantCountriesWithSlugs = new HashMap<>();
        redundantCountriesWithSlugs.put(" Azerbaijan", new String[]{"-azerbaijan", "azerbaijan"});
        redundantCountriesWithSlugs.put("Bahamas", new String[]{"the-bahamas", "bahamas-the", "bahamas"});
        redundantCountriesWithSlugs.put("Gambia", new String[]{"the-gambia", "gambia-the", "gambia"});
        redundantCountriesWithSlugs.put("Iran", new String[]{"iran", "iran-(islamic-republic-of)", "iran"});
        redundantCountriesWithSlugs.put("Russia", new String[]{"russia", "russian-federation", "russia"});
        redundantCountriesWithSlugs.put("Korea, South", new String[]{"south-korea", "republic-of-korea", "korea-south"});
        redundantCountriesWithSlugs.put("Taiwan", new String[]{"taiwan", "taipei-and-environs", "taiwan"});
        redundantCountriesWithSlugs.put("UK", new String[]{"uk", "united-kingdom"});
        redundantCountriesWithSlugs.put("Vietnam", new String[]{"vietnam", "viet-nam", "vietnam"});
        return redundantCountriesWithSlugs;
    }

    // used to check if country dropdown was previously populated with an already existing country
    public static HashMap<String[], Boolean> getRedundantSlugsCheck() {
        // redundant slug check hashmap: array of associated slugs as key and incrementer as value
        HashMap<String[], Boolean> redundantSlugsCheck = new HashMap<>();
        redundantSlugsCheck.put(new String[]{"-azerbaijan", "azerbaijan"}, false);
        redundantSlugsCheck.put(new String[]{"the-bahamas", "bahamas-the", "bahamas"}, false);
        redundantSlugsCheck.put(new String[]{"the-gambia", "gambia-the", "gambia"}, false);
        redundantSlugsCheck.put(new String[]{"iran", "iran-(islamic-republic-of)", "iran"}, false);
        redundantSlugsCheck.put(new String[]{"russia", "russian-federation", "russia"}, false);
        redundantSlugsCheck.put(new String[]{"south-korea", "republic-of-korea", "korea-south"}, false);
        redundantSlugsCheck.put(new String[]{"taiwan", "taipei-and-environs", "taiwan"}, false);
        redundantSlugsCheck.put(new String[]{"uk", "united-kingdom"}, false);
        redundantSlugsCheck.put(new String[]{"vietnam", "viet-nam", "vietnam"}, false);
        return redundantSlugsCheck;
    }

    // list of all possible slugs for redundant countries
    public static HashSet<String> getRedundantCountries() {
        // hashset with all possible slugs for all redundant countries
        HashSet<String> redundantCountries = new HashSet<>();

        HashMap<String, String[]> countriesWithSlugs = getRedundantCountriesWithSlugs();
        // iterate over hashmap, add every slug to this hashset
        for (Map.Entry<String, String[]> entry : countriesWithSlugs.entrySet()) {
            redundantCountries.addAll(Arrays.asList(entry.getValue()));
        }
        return redundantCountries;
    }

    // get all associated slugs for a given slug
    public static HashMap<String, String[]> getRedundantSlugs() {
        HashMap<String, String[]> redundantSlugs = new HashMap<>();

        // slug set with booleans to check if country already added to dropdown menu
        HashMap<String[], Boolean> allSlugsWithBooleans = RedundantCountryMethods.getRedundantSlugsCheck();
        Set<String[]> allSlugsSet = allSlugsWithBooleans.keySet();
        for (String[] slugSet : allSlugsSet) {
            for (String slug : slugSet) {
                redundantSlugs.put(slug, slugSet);
            }
        }

        return redundantSlugs;
    }

    // final filter of redundant countries
    public static LinkedList<String> filterRedundantCountries(Country[] countriesArray) {
        LinkedList<String> countryNames = new LinkedList<>();
        // add country names to a country dropdown list:
        // iterate over raw countries array
        // if a country is redundant, don't add it to the dropdown list:
        // get the slug of ith country
        // if that slug is contained in a list of redundant countries, get the slug's associated redundancies and check if it was already added (true/false flag)

        HashMap<String, String[]> redundantSlugs = new HashMap<>();

        // slug set with booleans to check if country already added to dropdown menu
        HashMap<String[], Boolean> allSlugsWithBooleans = RedundantCountryMethods.getRedundantSlugsCheck();
        Set<String[]> allSlugsSet = allSlugsWithBooleans.keySet();
        for (String[] slugSet : allSlugsSet) {
            for (String slug : slugSet) {
                redundantSlugs.put(slug, slugSet);
            }
        }

        for (Country country : countriesArray) {
            String slug = country.getSlug();
            HashSet<String> redundantCountryList = RedundantCountryMethods.getRedundantCountries();
            if (redundantCountryList.contains(slug)) {
                String[] redundanciesForACountry = redundantSlugs.get(slug);
                // if slug's country hasn't been added to dropdown yet (flag == false), add it and switch flag to true,
                if (allSlugsWithBooleans.containsKey(redundanciesForACountry) && !allSlugsWithBooleans.get(redundanciesForACountry))
                    countryNames.add(country.getCountry());
                allSlugsWithBooleans.put(redundanciesForACountry, true);
                // else don't add it
            } else {
                countryNames.add(country.getCountry());
            }
        }

        return countryNames;
    }
}
