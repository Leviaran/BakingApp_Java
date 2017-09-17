package com.singletondev.bakingapp.network;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;

/**
 * Created by Randy Arba on 9/17/17.
 * This apps contains BakingApp_Java
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class VideoRequestHandler extends RequestHandler {
    public String SCHEME_VIDEO="video";
    @Override
    public boolean canHandleRequest(Request data)
    {
        String scheme = data.uri.getScheme();
        Log.e("scheme",scheme);
        return (SCHEME_VIDEO.equals(scheme));
    }

    @Override
    public Result load(Request data, int arg1) throws IOException
    {
        Log.e("data",data.uri.getPath());
        Bitmap bm = ThumbnailUtils.createVideoThumbnail(data.uri.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        return new Result(bm, Picasso.LoadedFrom.DISK);
    }
}
