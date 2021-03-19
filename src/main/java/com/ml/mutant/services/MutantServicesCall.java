package com.ml.mutant.services;

import org.json.JSONException;
import org.json.JSONObject;

public interface MutantServicesCall {
    public  boolean isMutant(JSONObject body) throws JSONException;

    public String stats() throws JSONException;

    public boolean validarDNA(JSONObject body) throws JSONException;

}
