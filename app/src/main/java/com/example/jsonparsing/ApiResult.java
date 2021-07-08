package com.example.jsonparsing;

import java.util.ArrayList;

public class ApiResult {
    Integer total_count;                                   //these key names should be same as total_count in json file
    Boolean incomplete_results; //these key names should be same as incomplete_result in json file
    ArrayList<GithubUser> items; //these key names should be same as items in json file

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public Boolean getIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(Boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public ArrayList<GithubUser> getItems() {
        return items;
    }

    public void setItems(ArrayList<GithubUser> items) {
        this.items = items;
    }
}
