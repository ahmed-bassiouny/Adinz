package ntamtech.adinz.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

/**
 * Created by bassiouny on 24/04/18.
 */

public class MyGlideApp {

    public static void setImage(Context context, ImageView image, String url, RequestListener requestListener) {
        if (url.isEmpty())
            return;
        Glide.with(context).load(url)
                .listener(requestListener).into(image);
    }
}
