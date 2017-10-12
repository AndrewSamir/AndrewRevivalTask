package com.samir.andrew.andrewsamirrevivaltask.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samir.andrew.andrewsamirrevivaltask.R;
import com.samir.andrew.andrewsamirrevivaltask.googlePlacesApis.Results;
import com.samir.andrew.andrewsamirrevivaltask.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 *
 */
public class ViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<Results> intro;

    // private CircleImageView ivPager;
    private ViewPager viepager;
    private TextView tvViewPagerName;
    private ImageView imgViewPagerName;
    //private TextView tvPrice, tvSpecial;
    //  private TextView tvLoader;
    // private boolean isFeatured;
    // private String strPrice;

    public ViewPagerAdapter(Context context, ArrayList<Results> intro, ViewPager viepager) {
        this.context = context;
        this.intro = intro;
        this.viepager = viepager;
        //  this.isFeatured = isFutuered;
        //   this.strPrice = strPrice;
        //   Log.e("price",strPrice+"iii");
    }

    @Override
    public int getCount() {
        return intro.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inner = inflater.inflate(R.layout.xml_view_pager, null);

        tvViewPagerName = (TextView) inner.findViewById(R.id.tvViewPagerName);
        imgViewPagerName = (ImageView) inner.findViewById(R.id.imgViewPagerName);

        tvViewPagerName.setText(intro.get(position).getName());

          String imageUri = Constant.baseUrl + "maps/api/place/photo?photoreference=" + intro.get(position).getGeometry(). +
                "&sensor=false&key=" + "AIzaSyC2-JH7QqoaikPj8KWV1x4vMzoDrqPFFT0";

        String imageUri = "https://pbs.twimg.com/profile_images/378800000608859108/90a9d97e7bda84df9b7145d15b24c91b_400x400.jpeg";
        Log.d("image", imageUri);

        //  Picasso.with(context).load(imageUri).into(imgViewPagerName);

        Picasso.with(context)
                .load(imageUri)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgViewPagerName);


        //=======================


        ((ViewPager) container).addView(inner, 0);
        return inner;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public void onClick(View v) {

    }

}