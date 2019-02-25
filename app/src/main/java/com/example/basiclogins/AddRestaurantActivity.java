package com.example.basiclogins;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.BackendlessSerializer;
import com.backendless.servercode.BackendlessConfig;
import com.backendless.servercode.BackendlessService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText name;
    private EditText cuisine;
    private EditText websiteLink;
    private EditText address;
    private RatingBar rating;
    private NumberPicker setPrice;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        wireWidgets();

        setPrice.setMinValue(1);
        setPrice.setMaxValue(5);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant contact = new Restaurant();
                contact.setName(name.getText().toString());
                contact.setCuisine(cuisine.getText().toString());
                contact.setWebsiteLink(websiteLink.getText().toString());
                contact.setAddress(address.getText().toString());
                contact.setRating(rating.getRating());
                contact.setPrice(setPrice.getValue());
                //contact.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());

                // save object asynchronously
                Backendless.Persistence.save( contact, new AsyncCallback<Restaurant>() {
                    public void handleResponse( Restaurant response )
                    {
                        // new Contact instance has been saved
                        Toast.makeText(AddRestaurantActivity.this, name.getText() + " added!", Toast.LENGTH_SHORT).show();
                        Intent goback = new Intent(AddRestaurantActivity.this, RestaurantsActivity.class);
                        startActivity(goback);
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Toast.makeText(AddRestaurantActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void wireWidgets() {
        name = findViewById(R.id.editText_add_name);
        cuisine = findViewById(R.id.editText_add_cuisine);
        websiteLink = findViewById(R.id.editText_add_websiteLink);
        address = findViewById(R.id.editText_add_address);
        rating = findViewById(R.id.ratingBar);
        setPrice = findViewById(R.id.numberPicker);
        add = findViewById(R.id.button);
    }
}


