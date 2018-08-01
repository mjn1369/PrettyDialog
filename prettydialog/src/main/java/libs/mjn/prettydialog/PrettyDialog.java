package libs.mjn.prettydialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

/**
 * Created by mJafarinejad on 8/15/2017.
 */

public class PrettyDialog extends AppCompatDialog {

    Resources resources;
    LinearLayout ll_content, ll_buttons;
    PrettyDialogCircularImageView iv_icon;
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
        iv_icon = (PrettyDialogCircularImageView) findViewById(R.id.iv_icon);

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
        if(color==null){
            iv_icon.setColorFilter(null);
        }
        else
            iv_icon.setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
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
        if(iconTint==null) {
            iv_icon.setColorFilter(null);
        }
        else
            iv_icon.setColorFilter(context.getResources().getColor(iconTint), PorterDuff.Mode.MULTIPLY);
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

    protected static class PrettyDialogCircularImageView extends AppCompatImageView {

        // Properties
        private float borderWidth;
        private int canvasSize;
        private ColorFilter colorFilter;

        // Object used to draw
        private Bitmap image;
        private Drawable drawable;
        private Paint paint;
        private Paint paintBorder;
        private Paint paintBackground;

        //region Constructor & Init Method
        public PrettyDialogCircularImageView(final Context context) {
            this(context, null);
        }

        public PrettyDialogCircularImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public PrettyDialogCircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context, attrs, defStyleAttr);
        }

        private void init(Context context, AttributeSet attrs, int defStyleAttr) {
            // Init paint
            paint = new Paint();
            paint.setAntiAlias(true);

            paintBorder = new Paint();
            paintBorder.setAntiAlias(true);

            paintBackground = new Paint();
            paintBackground.setAntiAlias(true);

            setBorderWidth(0);
            setBorderColor(Color.WHITE);

            setBackgroundColor(Color.WHITE);
        }
        //endregion

        //region Set Attr Method
        public void setBorderWidth(float borderWidth) {
            this.borderWidth = borderWidth;
            requestLayout();
            invalidate();
        }

        public void setBorderColor(int borderColor) {
            if (paintBorder != null)
                paintBorder.setColor(borderColor);
            invalidate();
        }

        public void setBackgroundColor(int backgroundColor) {
            if (paintBackground != null)
                paintBackground.setColor(backgroundColor);
            invalidate();
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            if (this.colorFilter == colorFilter)
                return;
            this.colorFilter = colorFilter;
            drawable = null; // To force re-update shader
            invalidate();
        }

        @Override
        public ScaleType getScaleType() {
            ScaleType currentScaleType = super.getScaleType();
            return currentScaleType == null || currentScaleType != CENTER_INSIDE ? CENTER_CROP : currentScaleType;
        }

        @Override
        public void setScaleType(ScaleType scaleType) {
            if (scaleType != CENTER_CROP && scaleType != CENTER_INSIDE) {
                throw new IllegalArgumentException(String.format("ScaleType %s not supported. " +
                        "Just ScaleType.CENTER_CROP & ScaleType.CENTER_INSIDE are available for this library.", scaleType));
            } else {
                super.setScaleType(scaleType);
            }
        }
        //endregion

        //region Draw Method
        @Override
        public void onDraw(Canvas canvas) {
            // Load the bitmap
            loadBitmap();

            // Check if image isn't null
            if (image == null)
                return;

            if (!isInEditMode()) {
                canvasSize = Math.min(canvas.getWidth(), canvas.getHeight());
            }

            // circleCenter is the x or y of the view's center
            // radius is the radius in pixels of the cirle to be drawn
            // paint contains the shader that will texture the shape
            int circleCenter = (int) (canvasSize - (borderWidth * 2)) / 2;
            float margeWithShadowRadius = 0;

            // Draw Border
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - margeWithShadowRadius, paintBorder);
            // Draw Circle background
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - margeWithShadowRadius, paintBackground);
            // Draw CircularImageView
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - margeWithShadowRadius, paint);
        }

        private void loadBitmap() {
            if (drawable == getDrawable())
                return;

            drawable = getDrawable();
            image = drawableToBitmap(drawable);
            updateShader();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            canvasSize = Math.min(w, h);
            if (image != null)
                updateShader();
        }

        private void updateShader() {
            if (image == null)
                return;

            // Create Shader
            BitmapShader shader = new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            // Center Image in Shader
            float scale = 0;
            float dx = 0;
            float dy = 0;

            switch (getScaleType()) {
                case CENTER_CROP:
                    if (image.getWidth() * getHeight() > getWidth() * image.getHeight()) {
                        scale = getHeight() / (float) image.getHeight();
                        dx = (getWidth() - image.getWidth() * scale) * 0.5f;
                    } else {
                        scale = getWidth() / (float) image.getWidth();
                        dy = (getHeight() - image.getHeight() * scale) * 0.5f;
                    }
                    break;
                case CENTER_INSIDE:
                    if (image.getWidth() * getHeight() < getWidth() * image.getHeight()) {
                        scale = getHeight() / (float) image.getHeight();
                        dx = (getWidth() - image.getWidth() * scale) * 0.5f;
                    } else {
                        scale = getWidth() / (float) image.getWidth();
                        dy = (getHeight() - image.getHeight() * scale) * 0.5f;
                    }
                    break;
            }

            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postTranslate(dx, dy);
            shader.setLocalMatrix(matrix);

            // Set Shader in Paint
            paint.setShader(shader);

            // Apply colorFilter
            paint.setColorFilter(colorFilter);
        }

        private Bitmap drawableToBitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            } else if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            try {
                // Create Bitmap object out of the drawable
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //endregion

        //region Measure Method
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = measureWidth(widthMeasureSpec);
            int height = measureHeight(heightMeasureSpec);
            setMeasuredDimension(width, height);
        }

        private int measureWidth(int measureSpec) {
            int result;
            int specMode = MeasureSpec.getMode(measureSpec);
            int specSize = MeasureSpec.getSize(measureSpec);

            if (specMode == MeasureSpec.EXACTLY) {
                // The parent has determined an exact size for the child.
                result = specSize;
            } else if (specMode == MeasureSpec.AT_MOST) {
                // The child can be as large as it wants up to the specified size.
                result = specSize;
            } else {
                // The parent has not imposed any constraint on the child.
                result = canvasSize;
            }

            return result;
        }

        private int measureHeight(int measureSpecHeight) {
            int result;
            int specMode = MeasureSpec.getMode(measureSpecHeight);
            int specSize = MeasureSpec.getSize(measureSpecHeight);

            if (specMode == MeasureSpec.EXACTLY) {
                // We were told how big to be
                result = specSize;
            } else if (specMode == MeasureSpec.AT_MOST) {
                // The child can be as large as it wants up to the specified size.
                result = specSize;
            } else {
                // Measure the text (beware: ascent is a negative number)
                result = canvasSize;
            }

            return result + 2;
        }
        //endregion
    }
}
