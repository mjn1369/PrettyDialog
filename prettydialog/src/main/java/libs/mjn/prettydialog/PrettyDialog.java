package libs.mjn.prettydialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mJafarinejad on 8/15/2017.
 */

public class PrettyDialog extends AppCompatDialog {

    /*public enum BUTTON_TYPE{FILL,BORDER}*/
    Integer default_icon_tint = R.color.pdlg_color_blue;
    Resources resources;
    LinearLayout ll_content, ll_buttons;
    ImageView iv_icon;
    RotateAnimation close_rotation_animation;
    boolean icon_animation = true;
    TextView tv_title, tv_message;
    Typeface typeface;
    PrettyDialog thisDialog;
    Context context;

    public PrettyDialog(Context context) {
        super(context);
        this.context = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pdlg_layout);
        setCancelable(true);
        resources = context.getResources();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float pxWidth = displayMetrics.widthPixels;
        getWindow().setLayout((int)(pxWidth*0.75),ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().getAttributes().windowAnimations = R.style.pdlg_default_animation;
        thisDialog = this;
        setupViews_Base();
    }

    private void setupViews_Base(){
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_buttons = (LinearLayout) findViewById(R.id.ll_buttons);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, resources.getDimensionPixelSize(R.dimen.pdlg_icon_size)/2, 0, 0);
        ll_content.setLayoutParams(lp);
        ll_content.setPadding(0,(int)(1.25*resources.getDimensionPixelSize(R.dimen.pdlg_icon_size)/2),0,resources.getDimensionPixelSize(R.dimen.pdlg_space_1_0x));

        close_rotation_animation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        close_rotation_animation.setDuration(300);
        close_rotation_animation.setRepeatCount(Animation.ABSOLUTE);
        close_rotation_animation.setInterpolator(new DecelerateInterpolator());
        close_rotation_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                thisDialog.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        iv_icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.7f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        v.setAlpha(1.0f);
                        if(icon_animation) {
                            v.startAnimation(close_rotation_animation);
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_message.setVisibility(View.GONE);
    }

    public PrettyDialog setGravity(int gravity){
        getWindow().setGravity(gravity);
        return this;
    }

    public PrettyDialog addButton(String text, Integer textColor, Integer backgroundColor, /*BUTTON_TYPE type,*/ PrettyDialogCallback callback){
        PrettyDialogButton button = new PrettyDialogButton(context,text, textColor, backgroundColor, typeface, /*type,*/ callback);
        int margin = resources.getDimensionPixelSize(R.dimen.pdlg_space_1_0x);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(margin, margin, margin, 0);
        button.setLayoutParams(lp);
        ll_buttons.addView(button);
        return this;
    }

    public PrettyDialog setTitle(String text){
        if(text.trim().length()>0) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(text);
        }
        else {
            tv_title.setVisibility(View.GONE);
        }
        return this;
    }

    public PrettyDialog setTitleColor(Integer color){
        //tv_title.setTextColor(ContextCompat.getColor(context,color==null?R.color.pdlg_color_black : color));
        tv_title.setTextColor(context.getResources().getColor(color==null?R.color.pdlg_color_black : color));
        return this;
    }

    public PrettyDialog setMessage(String text){
        if(text.trim().length()>0) {
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText(text);
        }
        else {
            tv_message.setVisibility(View.GONE);
        }
        return this;
    }

    public PrettyDialog setMessageColor(Integer color){
        //tv_message.setTextColor(ContextCompat.getColor(context,color==null?R.color.pdlg_color_black :color));
        tv_message.setTextColor(context.getResources().getColor(color==null?R.color.pdlg_color_black :color));
        return this;
    }

    public PrettyDialog setIcon(Integer icon){
        iv_icon.setImageResource(icon==null?R.drawable.pdlg_icon_close :icon);
        icon_animation = false;
        iv_icon.setOnTouchListener(null);
        return this;
    }

    public PrettyDialog setIconTint(Integer color){
        //iv_icon.setColorFilter(ContextCompat.getColor(context,color==null?default_icon_tint:color), PorterDuff.Mode.MULTIPLY);
        iv_icon.setColorFilter(context.getResources().getColor(color==null?default_icon_tint:color), PorterDuff.Mode.MULTIPLY);
        return this;
    }

    public PrettyDialog setIconCallback(final PrettyDialogCallback callback){
        iv_icon.setOnTouchListener(null);
        if (callback != null) {
            iv_icon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            v.setAlpha(0.7f);
                            return true;
                        case MotionEvent.ACTION_UP:
                            v.setAlpha(1.0f);
                            callback.onClick();
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        return this;
    }

    public PrettyDialog setIcon(Integer icon, Integer iconTint, final PrettyDialogCallback callback){
        icon_animation = false;
        iv_icon.setImageResource(icon==null?R.drawable.pdlg_icon_close :icon);
        //iv_icon.setColorFilter(ContextCompat.getColor(context,iconTint==null?default_icon_tint:iconTint), PorterDuff.Mode.MULTIPLY);
        iv_icon.setColorFilter(context.getResources().getColor(iconTint==null?default_icon_tint:iconTint), PorterDuff.Mode.MULTIPLY);
        iv_icon.setOnTouchListener(null);
        if (callback != null) {
            iv_icon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            v.setAlpha(0.7f);
                            return true;
                        case MotionEvent.ACTION_UP:
                            v.setAlpha(1.0f);
                            callback.onClick();
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        return this;
    }

    public PrettyDialog setTypeface(Typeface tf){
        typeface = tf;
        tv_title.setTypeface(tf);
        tv_message.setTypeface(tf);

        for (int i=0;i<ll_buttons.getChildCount();i++){
            PrettyDialogButton button = (PrettyDialogButton) ll_buttons.getChildAt(i);
            button.setTypeface(tf);
            button.requestLayout();
        }

        return this;
    }

    public PrettyDialog setAnimationEnabled(boolean enabled){
        if (enabled){
            getWindow().getAttributes().windowAnimations = R.style.pdlg_default_animation;
        }
        else {
            getWindow().getAttributes().windowAnimations = R.style.pdlg_no_animation;
        }
        return this;
    }
}
