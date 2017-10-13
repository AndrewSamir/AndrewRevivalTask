package com.samir.andrew.andrewsamirrevivaltask.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

    private ViewPager viepager;
    private TextView tvViewPagerName;
    private TextView tvViewPagerPlaceId;
    private ImageView imgViewPagerName;

    public ViewPagerAdapter(Context context, ArrayList<Results> intro, ViewPager viepager) {
        this.context = context;
        this.intro = intro;
        this.viepager = viepager;
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
        tvViewPagerPlaceId = (TextView) inner.findViewById(R.id.tvViewPagerPlaceID);

        tvViewPagerName.setText(intro.get(position).getName());

        tvViewPagerPlaceId.setText(context.getString(R.string.place_id) + intro.get(position).getPlace_id());
        String imageUri = "no image";

        // get image uri
        // using try and catch because not all places have photo param
        try {
            imageUri = Constant.baseUrl + "maps/api/place/photo?maxwidth=400&photoreference=" + intro.get(position).getPhotos().get(0).getPhoto_reference() + "&sensor=false&key=" + Constant.googleAPIKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

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