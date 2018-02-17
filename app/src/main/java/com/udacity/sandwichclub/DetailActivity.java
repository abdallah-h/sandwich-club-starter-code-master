package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView origin;
    TextView description;
    TextView ingredients;
    TextView alsoKnownAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        //TextView Intialization
        origin = (TextView) findViewById(R.id.origin_tv);
        description = (TextView) findViewById(R.id.description_tv);
        ingredients = (TextView) findViewById(R.id.ingredients_tv);
        alsoKnownAs = (TextView) findViewById(R.id.also_known_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        //display place of origin
        if(sandwich.getPlaceOfOrigin().isEmpty()) {
            origin.setText("Unknown");
        }else{
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        //display description
        description.setText(sandwich.getDescription());

        //display ingredients array
        List<String> ingredientsList = sandwich.getIngredients();
        ingredients.setText(ingredientsList.toString());


        //display known as  array
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if(alsoKnownAsList.isEmpty()){
            alsoKnownAs.setText("Unknown");
        }else {
            alsoKnownAs.setText(alsoKnownAsList.toString());
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
    
}
