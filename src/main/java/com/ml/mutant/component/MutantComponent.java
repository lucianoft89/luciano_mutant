package com.ml.mutant.component;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class MutantComponent {
    public static int countMutant = 0; //Inicializamos el contador, utilizado para la contabilidad de coincidencias encontrada.
    public static char matrizB [][] = null; //Inicializamos la matriz bidimensional vacia.

    //generadorMutantArray - Toma el json y los transforma en array, para luego pasarlo a checkDNA;
    public String [] generadorMutantArray (JSONObject body) throws JSONException {
        JSONArray arr = body.getJSONArray("dna");
        return toStringArray(arr);
    }
    //controlarDNA - Toma el Json y lo transforma en un array para luego checkDNA
    public boolean controlarDNA (JSONObject body) throws JSONException {
        JSONArray arr = body.getJSONArray("dna");
        return checkDNA(arr);
    }

    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return null;
        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }

    //CheckDNA - Toma el json y realice unas pruebas, que determinan si cumple con las especificaciones Dimension: NxN, Unicamente Caracteres: 'A’, ‘C’,’G’ y ’T'
    public static boolean checkDNA (JSONArray array){
        boolean respuesta = true;
        String[] arr = new String[array.length()];
        //control_A, Detecta que la DNA no este vacia y tenga mas de una fila,
        if (arr.length<=4){
            System.out.println("Magneto: La estructura del ADN ingresado no cumple las dimensiones minimas");
            respuesta = false;
            return respuesta;
        }
        //control_B, Detecta que la DNA tiene la estructura NxN,
        if (arr.length<=4){
            for (int i = 0; i < arr.length; i++){
                if (arr[i].length()!=arr.length){
                    System.out.println("Magneto: La estructura del ADN ingresado varia no es un NxN");
                    respuesta = false;
                    return respuesta;
                }
            }
        }
        /*//control_C, Detecta que la DNA tenga solamente los caracteres A’, ‘C’,’G’ y ’T
        for (int i = 0; i < arr.length; i++) {
            for (int k = 0; k < arr[i].length(); k++){
                char letra = arr[i].charAt(k);
                if (letra != 'A' || letra != 'C'|| letra != 'G'|| letra != 'T' ) {
                    System.out.println("Magneto: La estructura del ADN tiene nuevos caracteres . ¿Puede ser una nueva especie?");
                    respuesta = false;
                    return respuesta;
                }
            }
        }*/
        return respuesta;
    }

    //consMatriz, toma el DNA y lo convierte en una matriz bidimensional[][]
    public void consMatriz(String[] array) {
        int n = array.length;
        this.matrizB = new char[n][n];
        for (int x=0; x<n; x++){
            for(int y=0; y<n; y++) {
                this.matrizB[x][y]= array[x].charAt(y);
            }
        }
    }

    //h_mutant, cuenta la cantidad de secuencias encontradas en la filas de DNA (utilizamos el DNA y no la matriz en este caso, es mas practico)
    public boolean h_Mutant(String[] array) {
        int count=0;
        for (int x=0; x < array.length; x++){
            countMutant += iFoundYouMutant(array[x], count);
            if (countMutant>1)
                return true;
        }
        return false;
    }

    //v_mutant, cuenta la cantidad de secuencias encontradas en la columnas de DNA (utilizamos la matriz creada)
    public boolean v_Mutant() {
        String result;
        int count=0;
        for (int columna = 0; columna < matrizB.length; columna++){
            result = "";
            for (int fila = 0; fila < matrizB.length; fila++){
                result += matrizB[fila][columna];
            }
            countMutant += iFoundYouMutant(result, count);
            if (countMutant>1)
                return true;
        }
        return false;
    }

    //x_mutant, cuenta la cantidad de secuencias encontradas en las diagonales de DNA (utilizamos la matriz creada)
    public boolean x_Mutant() {
        String result;
        int count = 0;

        //Diagonal (0,0) -> (n, n) Superior
        for(int columna = 0; columna < matrizB.length; columna++){
            result = "";
            for (int fila = 0; fila < (matrizB.length - columna); fila++){
                result += matrizB[fila][fila+columna];
            }

            if (result.length()<4 )
                break;

            countMutant += iFoundYouMutant(result, count);
        }

        if (countMutant>1)
            return true;

        //Diagonal (1,0) -> (n, n-1) Inferior
        for(int fila = 1; fila < matrizB.length; fila++){
            result = "";
            for (int columna = 0; columna < (matrizB.length - fila); columna++){
                result+=matrizB[fila+columna][columna];
            }

            if (result.length()<4 )
                break;

            countMutant = iFoundYouMutant(result, count);
        }
        if (countMutant>1)
            return true;

        //Diagonal (n, 0) -> (0, n) Superior
        for(int fila = matrizB.length-1; fila > 0 ; fila--){
            result = "";
            for (int columna = 0; columna <= (matrizB.length- (matrizB.length - fila)); columna++){
                result+=matrizB[fila-columna][columna];
            }

            if (result.length()<4 )
                break;

            countMutant = iFoundYouMutant(result, count);
        }
        if (countMutant>1)
            return true;

        //Diagonal (n, 1) -> (1, n) Inferior
        for(int columna = 1; columna < matrizB.length ; columna++){
            result = "";
            int sum = 0;
            for (int fila = matrizB.length-1; fila >= columna; fila--, sum++){
                result+=matrizB[fila][columna+sum];
            }

            if (result.length()<4 )
                break;
            countMutant = iFoundYouMutant(result, count);
        }

        if (countMutant>1)
            return true;

        return false;
    }

    //iFoundYouMutant, esta funcion se llama en cada busqueda (vertical, diagonal , horizontal) se encarga de contar las secuencias encontradas
    private int iFoundYouMutant(String array, int count){
        count += StringUtils.countMatches(array, "AAAA");
        count += StringUtils.countMatches(array, "TTTT");
        count += StringUtils.countMatches(array, "CCCC");
        count += StringUtils.countMatches(array, "GGGG");

        return count;
    }

    //countMutant - Recupera el contador de coincidencias encontradas
    public int getCountMutant() {
        return countMutant;
    }
    //resetCountMutant, resetea el contador a 0;
    public void resetCountMutant(){
        this.countMutant = 0;
    }

}

