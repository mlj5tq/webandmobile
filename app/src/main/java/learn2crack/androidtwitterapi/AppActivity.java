package learn2crack.androidtwitterapi;

import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.Button;
        import android.view.View;
        import android.view.View.OnClickListener;

public class AppActivity extends Activity {

    Button settingsButton;
    Button classroomButton;
    Button quizButton;
    Button tweetButton;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        addListenerOnSettingsButton();
        addListenerOnClassroomButton();
        addListenerOnQuizButton();
        addListenerOnTweetButton();

    }

    public void addListenerOnSettingsButton() {

        final Context context = this;

        settingsButton = (Button) findViewById(R.id.settingsbutton);

        settingsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, App2Activity.class);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnClassroomButton() {

        final Context context = this;

        classroomButton = (Button) findViewById(R.id.classroombutton);

        classroomButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, App3Activity.class);
                Bundle bunble=getIntent().getExtras();
                if(bunble!=null)
                {
                    //Getting the value stored in the name "NAME"
                    intent.putExtra("ip",bunble.getString("ip"));
                    intent.putExtra("color",bunble.getString("color"));
                }
                startActivity(intent);

            }

        });

    }
    public void addListenerOnQuizButton() {

        final Context context = this;

        quizButton = (Button) findViewById(R.id.quizbutton);

        quizButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, App4Activity.class);
                Bundle bunble=getIntent().getExtras();
                if(bunble!=null)
                {
                    //Getting the value stored in the name "NAME"
                    intent.putExtra("ip",bunble.getString("ip"));
                    intent.putExtra("color",bunble.getString("color"));
                }
                startActivity(intent);

            }

        });

    }

    public void addListenerOnTweetButton() {

        final Context context = this;

        tweetButton = (Button) findViewById(R.id.tweetbutton);

        tweetButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("randomBinary", setBinary());
                startActivity(intent);

            }

        });

    }
    public static String setBinary() {

        int max = 63;
        int value;
        String binary;

        value = (int)(Math.random() * max + 1);

        binary = Integer.toBinaryString(value);

        return binary;
    }


}