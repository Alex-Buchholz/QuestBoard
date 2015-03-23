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
 * QuestFragmentN Fragment - This fragment is used to represent the nth quest posted. Each page
 * is a different quest for the related Kingdom.
 */
public class QuestFragmentN extends Fragment {

    private String quest_Name, quest_Desc, giver_Name, imageUrl;

    //Set arguments to display the correct instance of a quest.
    public static QuestFragmentN newInstance(String qname, String qdesc,
                                             String gname, String url) {

        QuestFragmentN f1 = new QuestFragmentN();
        Bundle args = new Bundle();
        args.putString("QName", qname);
        args.putString("QDesc", qdesc);
        args.putString("GName", gname);
        args.putString("Url", url);
        f1.setArguments(args);

        return f1;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Initialize variables
        quest_Name = getArguments().getString("QName");
        quest_Desc = getArguments().getString("QDesc");
        giver_Name = getArguments().getString("GName");
        imageUrl = getArguments().getString("Url");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize TextViews and ImageViews.
        View view = inflater.inflate(R.layout.fragment_quest_view_n, container, false);
        TextView questName = (TextView) view.findViewById(R.id.textViewQuestName);
        TextView questDesc = (TextView) view.findViewById(R.id.textViewQuestDesc);
        TextView giverName = (TextView) view.findViewById(R.id.textViewQuestGiver);
        ImageView image = (ImageView) view.findViewById(R.id.imageViewGiver);

        //Populate TextViews and ImageViews.
        questName.setText(quest_Name);
        questDesc.setText(quest_Desc);
        giverName.setText(giver_Name);
        PicassoLoad.loadImage(image.getContext(), imageUrl, image);

        return view;
    }

}