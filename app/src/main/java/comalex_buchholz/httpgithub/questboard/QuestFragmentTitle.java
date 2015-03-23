package comalex_buchholz.httpgithub.questboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alex Buchholz on 3/17/2015.
 * <p/>
 * QuestFragmentTitle Fragment - This fragment is used to represent the first page which
 * displays the selected Kingdom with related information about that Kingdom.
 */
public class QuestFragmentTitle extends Fragment {

    private String kingdomName, kingdomClimate, kingdomPop, imageUrl;
    private int numOfQuests;

    //Set arguments to display the correct instance of Kingdom.
    public static QuestFragmentTitle newInstance(String name, String climate,
                                                 String population, String url, int numQuests) {

        QuestFragmentTitle f1 = new QuestFragmentTitle();
        Bundle args = new Bundle();
        args.putString("Kingdom", name);
        args.putString("Climate", climate);
        args.putString("Population", population);
        args.putString("Url", url);
        args.putInt("NumQuests", numQuests);
        f1.setArguments(args);

        return f1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        kingdomName = getArguments().getString("Kingdom");
        kingdomClimate = getArguments().getString("Climate");
        kingdomPop = getArguments().getString("Population");
        imageUrl = getArguments().getString("Url");
        numOfQuests = getArguments().getInt("NumQuests");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest_view_title, container, false);
        TextView kingdomNameView = (TextView) view.findViewById(R.id.textViewKingdomName);
        TextView climate = (TextView) view.findViewById(R.id.textViewClimateVar);
        TextView population = (TextView) view.findViewById(R.id.textViewPopVar);
        ImageView image = (ImageView) view.findViewById(R.id.imageViewKingdom);
        TextView quests = (TextView) view.findViewById(R.id.textViewNumOfQuestsInt);

        //Set text fields.
        quests.setText(Integer.toString(numOfQuests));
        kingdomNameView.setText(kingdomName);
        climate.setText(kingdomClimate);
        population.setText(kingdomPop);
        PicassoLoad.loadImage(image.getContext(), imageUrl, image);
        return view;
    }

}
