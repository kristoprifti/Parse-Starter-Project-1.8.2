package com.parse.starter;

/**
 * Created by Kristi-PC on 3/9/2015.
 */
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("Hairdresser")
public class Hairdressers extends ParseObject {

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("geoPoint");
    }

    public void setLocation(ParseGeoPoint value) {
        put("geoPoint", value);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String value) {
        put("name", value);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String value) {
        put("description", value);
    }

    public ParseFile getLogoFile() {
        return getParseFile("logo");
    }

    public void setLogoFile(ParseFile value) {
        put("logo", value);
    }
}
