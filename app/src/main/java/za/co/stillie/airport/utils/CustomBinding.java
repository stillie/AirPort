package za.co.stillie.airport.utils;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import za.co.stillie.airport.R;

public class CustomBinding {

    @BindingAdapter({"setFlightStatus"})
    public static void setFlightStatus(TextView aTextView, String status) {
        status = aTextView.getText().toString();
        Drawable drawable = ContextCompat.getDrawable(aTextView.getContext(), R.drawable.ic_flight_status);
        int color = "departed".equalsIgnoreCase(status) ? Color.RED : "scheduled".equalsIgnoreCase(status) ? Color.YELLOW : Color.GREEN;
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
        aTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        aTextView.setText(sentenceCase(aTextView.getText().toString()));
    }

    @BindingAdapter({"formatTime"})
    public static void formatTime(TextView aTextView, String receivedDateTime) {
        if (TextUtils.isEmpty(receivedDateTime)) return;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-dd-mm'T'HH:mm:ss", Locale.ENGLISH);
            Date date = originalFormat.parse(receivedDateTime);
            SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            aTextView.setText(newFormat.format(date));
        } catch (ParseException aE) {
            aTextView.setText(receivedDateTime);

        }
    }

    public static String sentenceCase(String myString) {
        return TextUtils.isEmpty(myString) ? "" : myString.substring(0, 1).toUpperCase() + myString.substring(1);
    }
}
