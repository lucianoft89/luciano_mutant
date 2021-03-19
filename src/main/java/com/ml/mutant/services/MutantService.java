package com.ml.mutant.services;

import com.ml.mutant.component.MutantComponent;
import com.ml.mutant.model.MutantModel;
import com.ml.mutant.repositories.MutantRepositories;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class MutantService implements MutantServicesCall {

    @Autowired
    MutantComponent mutantComponent;

    @Autowired
    private MutantRepositories mutantRepositories;


    @Override
    public boolean isMutant(JSONObject body) throws JSONException {
        mutantComponent.resetCountMutant();
        boolean respuesta = false;
        String[] dnaAr = mutantComponent.generadorMutantArray(body);
        mutantComponent.consMatriz(dnaAr);
        mutantComponent.h_Mutant(dnaAr);
        if (mutantComponent.getCountMutant()<2)
            mutantComponent.v_Mutant();
        if (mutantComponent.getCountMutant()<2)
            mutantComponent.x_Mutant();
        if (mutantComponent.getCountMutant()>1)
            respuesta = true;
        MutantModel mutantmodel= new MutantModel(0,body.toString(),respuesta);
        mutantRepositories.save(mutantmodel);
        return respuesta;
    }

    @Override
    public boolean validarDNA(JSONObject body) throws JSONException{
        boolean respuesta = mutantComponent.controlarDNA(body);
        return respuesta;
    }

    @Override
    public String stats() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("count_mutant_dna", mutantRepositories.findMutantCount());
        response.put("count_human_dna", mutantRepositories.findHmanCount());
        if(mutantRepositories.findHmanCount()!=0) {
            float ratio = (float) mutantRepositories.findMutantCount() / (float) mutantRepositories.findHmanCount();
            DecimalFormat formatTwoDecimal = new DecimalFormat("#.##");
            formatTwoDecimal.format(ratio);
            response.put("ratio", ratio);
        }
        else{
            String humanCero = "Santo Cielo! No ha venido ningún humano.";
            response.put("ratio",humanCero);
        }


        return response.toString();
    }

}
