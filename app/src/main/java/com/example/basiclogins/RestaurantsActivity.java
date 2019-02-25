package com.example.basiclogins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.Fault;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

import static com.backendless.servercode.services.codegen.ServiceCodeFormat.as;

public class RestaurantsActivity extends AppCompatActivity {

    private ListView listviewRestaurant;
    private List<Restaurant> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(RestaurantsActivity.this, AddRestaurantActivity.class);
                startActivity(add);
            }
        });

        listviewRestaurant = findViewById(R.id.listView_restaurantList);
        populateListView();
        registerForContextMenu(listviewRestaurant);

        listviewRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo: finish info page
                Intent infopls = new Intent(RestaurantsActivity.this, RestaurantInfoActivity.class);
                startActivity(infopls);
            }
        });

        Button logout = findViewById(R.id.button_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Backendless.UserService.logout( new AsyncCallback<Void>()
                    {
                        public void handleResponse( Void response )
                        {
                            // user has been logged out.
                            Intent logOut = new Intent(RestaurantsActivity.this, LoginActivity.class);
                            startActivity(logOut);
                        }

                        public void handleFault( BackendlessFault fault )
                        {
                            // something went wrong and logout failed, to get the error code call fault.getCode()
                            Toast.makeText(RestaurantsActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        View view = info.targetView;

        if(item.getItemId()==R.id.edit){
            Intent editpls = new Intent(RestaurantsActivity.this, RestaurantEditActivity.class);
            startActivity(editpls);
        }
        else if(item.getItemId()==R.id.delete){
            Restaurant deleteRestaurant = list.get(index);
            //Toast.makeText(this, deleteRestaurant.getName(), Toast.LENGTH_SHORT).show();
            Backendless.Persistence.of( Restaurant.class ).remove(deleteRestaurant,
                new AsyncCallback<Long>()
                {
                    public void handleResponse( Long response )
                    {
                        // Contact has been deleted. The response is the
                        // time in milliseconds when the object was deleted
                        Toast.makeText(RestaurantsActivity.this, list.get(index).getName() + " deleted!", Toast.LENGTH_SHORT).show();
                        populateListView();
                    }
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be
                        // retrieved with fault.getCode()
                        Toast.makeText(RestaurantsActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } );
        }else{
            return false;
        }
        return true;
    }

    private void populateListView() {
        // Find all restaurants whose ownerId matches the objectId

        String ownerID = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = '" + ownerID + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        Backendless.Data.of(Restaurant.class).find(queryBuilder, new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(List<Restaurant> restaurantList) {
                // All restaurant instances have been found
                list = restaurantList;
                RestaurantAdapter adapter = new RestaurantAdapter(
                        RestaurantsActivity.this,
                        R.layout.item_restaurantlist,
                        list);
                listviewRestaurant.setAdapter(adapter);

                //set the onclick listener to open infoActivity
                //take the clicked object and include it in the intent
                //in the infoActivity's onCreate, check if there's a parceable
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RestaurantsActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
