package com.vik.covid19vik;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AWSDynamoDBMethods {
    static void addRequestToDB(String snsAdd, String countryCheck, String provinceCheck, String countyCheck) {
        System.out.println(snsAdd);
        System.out.format("%s %s %s", countryCheck, provinceCheck, countyCheck);
    }

    // check URL for redundant parameters
    static String checkURLRedundant(String currentURL) {
        if (currentURL.contains("&checks=")) {
            int index = currentURL.indexOf("&checks=");
            currentURL = currentURL.substring(0, index);
        }
        return currentURL;
    }
}
