package comalex_buchholz.httpgithub.questboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*
 * Created by Alex Buchholz on 3/17/2015.
 *
 * QuestView Activity - This activity handles the fragments used for ViewPager.
 * Retrofit requests data from the specified web service and creates the first page as the
 * Kingdom page with related information. Each swipe on the ViewPager after the first page
 * displays each quest for the Kingdom up to n quests.
 *
 */
public class QuestView extends ActionBarActivity implements ViewPager.OnPageChangeListener {

    ViewPager vPager = null;
    int pageID;
    AreaInformation area;
    Toolbar toolbar;
    public static final String ENDPOINT = "https://challenge2015.myriadapps.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_view);

        //Set up toolbar.
        toolbar = (Toolbar) findViewById(R.id.kingdom_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logodrawable);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Get data from previous activity.
        pageID = getIntent().getIntExtra("pageID", 0);
        requestData(pageID);
    }

    //Update method to set up ViewPager.
    private void update() {
        vPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        vPager.setAdapter(new QuestAdapter(fragmentManager, area));
        vPager.setOnPageChangeListener(this);
        toolbar.setTitle(area.name);
    }

    private void requestData(int id) {

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();

        KingdomAPI api = adapter.create(KingdomAPI.class);

        api.getKingdomDetails(id, new Callback<AreaInformation>() {
            @Override
            public void success(AreaInformation arg0, Response response) {
                area = (arg0);
                update();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(QuestView.this, "Connection Error. Make sure your data is enabled " +
                        "or you are connected to Wi-Fi", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                toolbar.setTitle(area.name);
                return;
            default:
                toolbar.setTitle("Quest #" + (position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

class QuestAdapter extends FragmentPagerAdapter {
    private AreaInformation area;

    public QuestAdapter(FragmentManager fm, AreaInformation area) {
        super(fm);
        this.area = area;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return QuestFragmentTitle.newInstance(area.name, area.climate, area.population,
                        area.image, area.quests.size());
            default:
                return QuestFragmentN.newInstance(area.quests.get(position-1).name,
                        area.quests.get(position-1).description,
                        area.quests.get(position-1).giver.name,
                        area.quests.get(position-1).giver.image);
        }
    }

    @Override
    public int getCount() {
        return area.quests.size()+1;
    }


}