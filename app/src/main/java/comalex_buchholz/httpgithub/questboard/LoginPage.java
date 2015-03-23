package comalex_buchholz.httpgithub.questboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*
 * Created by Alex Buchholz on 3/16/2015.
 *
 * LoginPage Activity - This activity is the root activity of the application. If no information
 * is stored, the page will be shown asking for the user's name and email. The email (if valid)
 * will be posted to the specified web service. If the post is successful or if information is
 * found locally, the user will be forwarded to the Kingdom activity.
 */
public class LoginPage extends ActionBarActivity {

    protected EditText userName;
    protected EditText userEmail;
    protected Button submit;
    public static String filename = "QuestBoardData";
    public static final String ENDPOINT = "https://challenge2015.myriadapps.com";
    Toolbar toolbar;
    boolean posted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Initialize Fields.
        userName = (EditText) findViewById(R.id.editTextName);
        userEmail = (EditText) findViewById(R.id.editTextEmail);
        submit = (Button) findViewById(R.id.button_submit);

        //Set up toolbar.
        toolbar = (Toolbar) findViewById(R.id.kingdom_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logodrawable);


        //Enter saved information and continue to the Kingdoms page.
        SharedPreferences sp = getSharedPreferences(filename, 0);
        String savedName = sp.getString("Name", null);
        String savedEmail = sp.getString("E-Mail", null);
        if (savedName != null && savedEmail != null) {
            userName.setText(sp.getString("Name", null));
            userEmail.setText(sp.getString("E-Mail", null));
            gotoKingdom();
        }

        //Submit button click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEmailValid(userEmail.getText().toString())) {
                    postData(userEmail.getText().toString());
                } else {
                    Toast.makeText(LoginPage.this, "Invalid Email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Email validation method.
    public static boolean isEmailValid(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    //Go to Kingdoms page method.
    public void gotoKingdom() {

        Intent intent = new Intent(this, Kingdom.class);
        startActivity(intent);
    }

    //Retrofit API call to post Email.
    private void postData(String email) {

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

        KingdomAPI api = adapter.create(KingdomAPI.class);

        api.postEmail(new User(email), new Callback<UserResponse>() {
            @Override
            public void success(UserResponse response, Response response2) {
                //Put information into local storage.
                SharedPreferences sp = getSharedPreferences(filename, 0);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("Name", userName.getText().toString());
                ed.putString("E-Mail", userEmail.getText().toString());
                ed.commit();

                Toast.makeText(LoginPage.this, response.message, Toast.LENGTH_LONG).show();
                //Navigate after confirmation.
                gotoKingdom();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(LoginPage.this, "Connection Error. Make sure your data is enabled " +
                        "or you are connected to Wi-Fi", Toast.LENGTH_LONG).show();
                posted = false;

            }
        });
    }
}
