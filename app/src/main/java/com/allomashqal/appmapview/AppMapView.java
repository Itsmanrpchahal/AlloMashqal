package com.allomashqal.appmapview;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnSuccessListener;

public class AppMapView extends MapView {

    public AppMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
               System.out.println("unlocked");
               this.getParent().requestDisallowInterceptTouchEvent(false);
               break;

            case MotionEvent.ACTION_DOWN:
               System.out.println("locked");
               this.getParent().requestDisallowInterceptTouchEvent(true);
               break;
       }
       return super.dispatchTouchEvent(ev);
   }

    public void getMapAsync(OnSuccessListener<Location> locationOnSuccessListener) {
    }
}