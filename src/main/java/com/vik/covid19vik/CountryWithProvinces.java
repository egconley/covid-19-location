package com.vik.covid19vik;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.LinkedList;

class CountryWithProvinces {
    private String country;
    private LinkedList<String> allProvinces;

    // getters and setters
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public LinkedList<String> getAllProvinces() {
        return allProvinces;
    }
    public void setAllProvinces(LinkedList<String> allProvinces) {
        this.allProvinces = allProvinces;
    }

    protected static LinkedList<CountryWithProvinces> createCountriesWithProvinces(HttpServletRequest req) {
        HashSet<String> countriesSeen = new HashSet<>();
        CountryUIFLookup[] countries = ApiMethods.getUIFLookup(req);
        if (countries != null) {

            // get index of start of US data
            int k = 0;
            for (CountryUIFLookup country : countries) {
                if (country.getCountryOrRegion().equals("US")) {
                    break;
                }
                k++;
            }
            // get index of end of state US data
            int l = k;
            for (CountryUIFLookup country : countries) {
                if (country.getCountryOrRegion().equals("US") && !country.getCounty().equals("")) {
                    break;
                }
                l++;
            }

            // instantiate countries with lists of their provinces
            LinkedList<CountryWithProvinces> countriesWithProvinces = new LinkedList<>();
            for (int i = 0; i < countries.length; i++) {
                CountryUIFLookup country = countries[i];
                CountryWithProvinces countryWithProvinces = new CountryWithProvinces();
                countryWithProvinces.allProvinces = new LinkedList<>();

                // if country not already seen, province is not empty, and it's not the US, find all provinces and add
                if (!country.getProvinceOrState().equals("") && !countriesSeen.contains(country.getCountryOrRegion()) && !country.getCountryOrRegion().equals("US")) {
                    countryWithProvinces.setCountry(country.getCountryOrRegion());
                    for (int j = i; j < k; j++) {
                        CountryUIFLookup next = countries[j];
                        if (next.getCountryOrRegion().equals(country.getCountryOrRegion())) {
                            countryWithProvinces.allProvinces.add(next.getProvinceOrState());
                        }
                    }
                    countriesSeen.add(country.getCountryOrRegion());
                    countriesWithProvinces.add(countryWithProvinces);
                }

                // if the country is the US and county is empty
                else if (country.getCounty().equals("") && country.getCountryOrRegion().equals("US")) {
                    countryWithProvinces.setCountry("US");
                    while (i < l) {
                        CountryUIFLookup next = countries[i];
                        if (next.getCounty().equals("") && !next.getProvinceOrState().equals("")) {
                            countryWithProvinces.allProvinces.add(next.getProvinceOrState());
                        }
                        i++;
                    }
                    countriesWithProvinces.add(countryWithProvinces);
                    break;
                }
            }
            return countriesWithProvinces;
        } else {
            throw new NullPointerException("Could not pull UIF Lookup information");
        }
    }

    protected static LinkedList<String> getProvincesForCountry(String searchedCountry, HttpServletRequest req) {
        LinkedList<CountryWithProvinces> countriesWithProvinces = createCountriesWithProvinces(req);
        for (CountryWithProvinces country : countriesWithProvinces) {
            if (country.getCountry().equals(searchedCountry)) {
                return country.getAllProvinces();
            }
        }
        return null;
    }
}