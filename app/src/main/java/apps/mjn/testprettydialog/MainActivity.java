package apps.mjn.testprettydialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btn_titleMessage, btn_okCancel, btn_allCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_titleMessage = (AppCompatButton) findViewById(R.id.btn_titleMessage);
        btn_okCancel = (AppCompatButton) findViewById(R.id.btn_okCancel);
        btn_allCustom = (AppCompatButton) findViewById(R.id.btn_allCustom);

        setup();
    }

    private void setup(){
        setup_titleMessageDialog();
        setup_okCancelDialog();
        setup_allCustomDialog();
    }

    private void setup_titleMessageDialog(){
        final PrettyDialog dialog = new PrettyDialog(this)
                .setIcon(
                        R.drawable.pdlg_icon_info,    // Icon resource
                        R.color.pdlg_color_green,      // Icon tint
                        new PrettyDialogCallback() {  // Icon OnClick listener
                            @Override
                            public void onClick() {
                                // Do what you gotta do
                            }
                })
                .setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                // Do what you gotta do
                            }
                        }
                )
                .addButton(
                        "Cancel",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                // Dismiss
                            }
                        }
                )
                .addButton(
                        "Option 3",
                        R.color.pdlg_color_black,
                        R.color.pdlg_color_gray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                Toast.makeText(MainActivity.this, "I Do Nothing :)", Toast.LENGTH_SHORT).show();
                                
                            }
                        }
                )
                .setTitle("Do you agree?")
                .setTitleColor(R.color.pdlg_color_blue)
                .setAnimationEnabled(true)
                .setMessage("By agreeing to our terms and conditions, you agree to our terms and conditions.")
                .setMessageColor(R.color.pdlg_color_gray)
                .setTypeface(Typeface.createFromAsset(getResources().getAssets(),"myfont.ttf"));
        btn_titleMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void setup_okCancelDialog(){
        final PrettyDialog dialog = new PrettyDialog(this);
        dialog
                .setTitle("")
                .setMessage("Do you want to Proceed?")
                .setIcon(R.drawable.pdlg_icon_info,R.color.pdlg_color_blue,null)
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"OK selected",Toast.LENGTH_SHORT).show();
                    }
                })
                .addButton("Cancel", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Cancel selected",Toast.LENGTH_SHORT).show();
                    }
                });
        btn_okCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void setup_allCustomDialog(){
        final PrettyDialog dialog = new PrettyDialog(this);
        dialog
                .setTitle("Custom PrettyDialog")
                .setTitleColor(R.color.pdlg_color_blue)
                .setMessage("You can customize icon, buttons, button colors and text colors...")
                .setMessageColor(R.color.pdlg_color_black)
                .setTypeface(Typeface.createFromAsset(getResources().getAssets(),"myfont.ttf"))
                .setAnimationEnabled(false)
                .setIcon(R.drawable.pdlg_icon_close, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Close selected",Toast.LENGTH_SHORT).show();
                    }
                })
                .addButton("Option 1", R.color.pdlg_color_white, R.color.pdlg_color_blue, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Option 1 selected",Toast.LENGTH_SHORT).show();
                    }
                })
                .addButton("Option 2", R.color.pdlg_color_black, R.color.pdlg_color_gray, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Option 2 selected",Toast.LENGTH_SHORT).show();
                    }
                })
                .addButton("Option 3", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Option 3 selected",Toast.LENGTH_SHORT).show();
                    }
                })
                .addButton("Option 4", R.color.pdlg_color_white, R.color.pdlg_color_blue, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Option 4 selected",Toast.LENGTH_SHORT).show();
                    }
                });
        btn_allCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}
