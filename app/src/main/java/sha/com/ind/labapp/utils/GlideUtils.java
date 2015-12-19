package sha.com.ind.labapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by sreepolavarapu on 18/12/15.
 */
public class GlideUtils {

//    public static void loadWithGlideWithHeader(Context context, String url, ImageView image, String token, int placeHolderResid)
//    {
//        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
//                .addHeader(Constants.AUTHORIZATION, Constants.BEARER + token).build());
//
//        Glide.with(context)
//                .load(glideUrl)
//                .placeholder(placeHolderResid)
//                .into(image);
//    }
//
//    public static void loadWithGlideWithHeader(Context context, String url, RoundedImageView image, String token, int placeHolderResid)
//    {
//        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
//                .addHeader(Constants.AUTHORIZATION, Constants.BEARER + token).build());
//
//        Glide.with(context)
//                .load(glideUrl)
//                .asBitmap()
//                .placeholder(placeHolderResid)
//                .into(image);
//    }
//
//    public static void loadWithGlideWithHeader(Context context, String url, BitmapImageViewTarget target, String token, int placeHolderResid)
//    {
//        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
//                .addHeader(Constants.AUTHORIZATION, Constants.BEARER + token).build());
//
//        Glide.with(context)
//                .load(glideUrl)
//                .asBitmap()
//                .placeholder(placeHolderResid)
//                .into(target);
//    }
//
//    public static void loadWithGlideWithHeader(Context context, String url, SimpleTarget target, String token, int placeHolderResid)
//    {
//        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
//                .addHeader(Constants.AUTHORIZATION, Constants.BEARER + token).build());
//
//        Glide.with(context)
//                .load(glideUrl)
//                .asBitmap()
//                .placeholder(placeHolderResid)
//                .into(target);
//    }
//
//    public static void loadWithGlideWithHeader(Context context, String url, RoundedImageView image, String token, int placeHolderResid, Transformation transformation)
//    {
//        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
//                .addHeader(Constants.AUTHORIZATION, Constants.BEARER + token).build());
//
//        Glide.with(context)
//                .load(glideUrl)
//                .asBitmap()
//                .transform(transformation)
//                .placeholder(placeHolderResid)
//                .into(image);
//    }



    public static void loadWithGlide(Context context, String url, ImageView image, int placeHolderResid)
    {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(placeHolderResid)
                .into(image);
    }



    public static void loadWithGlide(Context context, String url, ImageView image, Drawable errorDrawable, Drawable placeHolderResid)
    {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(placeHolderResid)
                .error(errorDrawable)
                .into(image);
    }

    public static void loadWithGlide(Context context, String url, ImageView imageView, Drawable placeholder) {
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    //  -----   CENTER CROP ------  //
    public static void loadWithGlideCenterCrop(Context context, String url, ImageView image, int placeHolderResid)
    {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(placeHolderResid)
                .centerCrop()
                .into(image);
    }

    public static void loadWithGlideCenterCrop(Context context, String url, ImageView image, Drawable errorDrawable, Drawable placeHolderResid)
    {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(placeHolderResid)
                .error(errorDrawable)
                .centerCrop()
                .into(image);
    }

    //  ------ WITH TRANSFORMATIONS ----- //

    public static void loadWithGlide(Context context, ImageView image, int placeHolderResid, Transformation transformation)
    {
        Glide.with(context)
                .load(placeHolderResid)
                .asBitmap()
                .transform(transformation)
                .into(image);
    }

    public static void loadWithGlide(Context context, Uri uri, ImageView image, int placeHolderResid, Transformation transformation)
    {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .placeholder(placeHolderResid)
                .transform(transformation)
                .into(image);
    }

    public static void loadWithGlide(Context context, String url, ImageView image, int placeHolderResid, Transformation transformation)
    {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .transform(transformation)
                .placeholder(placeHolderResid)
                .into(image);
    }
}
