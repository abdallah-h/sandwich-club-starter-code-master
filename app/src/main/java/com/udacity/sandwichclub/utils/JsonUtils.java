package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws Exception {
        JSONObject obj = new JSONObject(json);
        // name Object
        JSONObject name = obj.getJSONObject("name");
        String mainName = name.getString("mainName");

        List<String> alsoKnownAsList = new ArrayList<String>();
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        if (alsoKnownAs != null)
            for (int i = 0; i < alsoKnownAs.length(); i++)
                alsoKnownAsList.add(alsoKnownAs.getString(i));

        // place of origin
        String placeOfOrigin = obj.getString("placeOfOrigin");

        //description
        String description = obj.getString("description");

        //image string
        String image = obj.getString("image");

        //ingredients array
        List<String> ingredientsList = new ArrayList<String>();
        JSONArray ingredients = obj.getJSONArray("ingredients");
        if(ingredients != null)
            for (int i = 0; i < ingredients.length(); i++)
                ingredientsList.add(ingredients.getString(i));


        return new Sandwich(mainName,alsoKnownAsList,placeOfOrigin,description,image,ingredientsList);
    }
}
