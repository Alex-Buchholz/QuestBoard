package comalex_buchholz.httpgithub.questboard;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Alex Buchholz on 3/18/2015.
 * <p/>
 * PicassoLoad class - contains methods to load images from the web.
 */
public class PicassoLoad {

    //Load image to fit ImageView.
    protected static void loadImage(Context context, String link, ImageView image) {

        Picasso.with(context).load(link)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });
    }
}
