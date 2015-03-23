package comalex_buchholz.httpgithub.questboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alex Buchholz on 3/16/2015.
 * <p/>
 * Kingdom Adapter Class - This class is used as the adapter for the Recyclerview on the
 * Kingdom Activity. Creates a new row in the view for each kingdom in the list.
 */
public class KingdomAdapter extends RecyclerView.Adapter<KingdomAdapter.KingdomViewHolder> {

    private LayoutInflater inflater;
    List<KingdomInformation> info = Collections.emptyList();
    private Context context;

    public KingdomAdapter(Context context, List<KingdomInformation> info) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.info = info;
    }

    @Override
    public KingdomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_kingdom_row, viewGroup, false);
        KingdomViewHolder holder = new KingdomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(KingdomViewHolder viewHolder, int i) {

        KingdomInformation current = info.get(i);
        viewHolder.kingdomName.setText(current.name);
        Context context = viewHolder.icon.getContext();
        PicassoLoad.loadImage(context, current.image, viewHolder.icon);

    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public class KingdomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView kingdomName;
        ImageView icon;

        public KingdomViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            kingdomName = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View view) {

            //Navigate to the QuestView activity.
            Intent i = new Intent(context, QuestView.class);
            KingdomInformation current = info.get(getLayoutPosition());

            //Send ID to next activity
            i.putExtra("pageID", current.id);

            context.startActivity(i);
        }
    }


}


