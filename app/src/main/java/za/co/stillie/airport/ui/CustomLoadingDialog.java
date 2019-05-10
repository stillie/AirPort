package za.co.stillie.airport.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import za.co.stillie.airport.R;

public class CustomLoadingDialog {

    private final AppCompatImageView mPlaneImage;
    private final Dialog mDialog;
    private final Activity mActivity;

    RotateAnimation rotate;

    public CustomLoadingDialog(Context context) {
        rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setDuration(800);
        mDialog = new Dialog(context);
        Window window = mDialog.getWindow();
        mActivity = (Activity) context;

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_progress);
        mDialog.setCancelable(false);

        mPlaneImage = mDialog.findViewById(R.id.planeImage);
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

    }

    public void dismissProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void showProgress() {
        if (mDialog != null && !mDialog.isShowing() && !mActivity.isDestroyed() && !mActivity.isFinishing()) {
            mPlaneImage.startAnimation(rotate);
            mDialog.show();
        }
    }


}
