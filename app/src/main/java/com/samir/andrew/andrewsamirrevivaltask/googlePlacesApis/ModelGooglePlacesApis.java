package com.samir.andrew.andrewsamirrevivaltask.googlePlacesApis;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ksi on 10-Oct-17.
 */

public class ModelGooglePlacesApis {


    @SerializedName("html_attributions")
    private List<Html_attributions> html_attributions;
    @SerializedName("results")
    private List<Results> results;
    @SerializedName("status")
    private String status;

    public List<Html_attributions> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<Html_attributions> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
