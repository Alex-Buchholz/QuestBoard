package comalex_buchholz.httpgithub.questboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*
 * Created by Alex Buchholz on 3/16/2015.
 *
 * Kingdom Activity - This activity uses Retrofit to request a list of Kingdoms from the
 * specified web service and creates a list of Kingdoms using Recyclerview. Each item listed
 * on Recyclerview is clickable and will navigate the user to the next activity which displays
 * more information about the selected Kingdom.
 *
 */

public class Kingdom extends ActionBarActivity {

    public static String filename = "QuestBoardData";
    private Toolbar toolbar;
    private RecyclerView rView;
    private KingdomAdapter kingdomAdapter;
    List<KingdomInformation> list;

    public static final String ENDPOINT = "https://challenge2015.myriadapps.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kingdom);

        //Set up toolbar.
        toolbar = (Toolbar) findViewById(R.id.kingdom_bar);
        setSupportActionBar(toolbar);
        SharedPreferences sp = getSharedPreferences(filename, 0);
        getSupportActionBar().setTitle(sp.getString("E-Mail", null));
        toolbar.setLogo(R.drawable.logodrawable);

        //Request data using Retrofit.
        requestData();
    }

    //Update method for RecyclerView.
    private void update() {
        rView = (RecyclerView) findViewById(R.id.kingdomList);
        kingdomAdapter = new KingdomAdapter(this, list);
        rView.setAdapter(kingdomAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));

    }

    //Method to get a list of kingdoms using Retrofit.
    private void requestData() {

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

        KingdomAPI api = adapter.create(KingdomAPI.class);

        api.getKingdoms(new Callback<List<KingdomInformation>>() {
            @Override
            public void success(List<KingdomInformation> arg0, Response response) {
                list = (arg0);
                update();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(Kingdom.this, "Connection Error. Make sure your data is enabled " +
                        "or you are connected to Wi-Fi", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Add back button functionality to close the app at this point.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kingdom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Log Out option selected.
        if (id == R.id.action_logout) {
            SharedPreferences sp = getSharedPreferences(filename, 0);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("Name", null);
            ed.putString("E-Mail", null);
            ed.commit();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
