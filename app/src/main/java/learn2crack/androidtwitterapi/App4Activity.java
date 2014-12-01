package learn2crack.androidtwitterapi;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

/**
 * Created by meganjoyner on 11/11/14.
 */
public class App4Activity extends Activity {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

    private TextView mlvTextMatches;
    private Button mbtSpeak;
    private TextView binary;
    private TextView what;
    private TextView qnum;
    private String ranNum;
    private TextView tv;
    private TextView coi;
    private Button next;
    private Button tweet;
    private String lightcolor;
    private int qn = 1;
    private int score = 0;
    private boolean guessed = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app4);
        mlvTextMatches = (TextView) findViewById(R.id.whatdidisay);
        mbtSpeak = (Button) findViewById(R.id.btSpeak);
        binary = (TextView) findViewById(R.id.binarynumber);
        what = (TextView) findViewById(R.id.whatnumber);
        coi = (TextView) findViewById(R.id.correctorincorrect);
        next = (Button) findViewById(R.id.nextbutton);
        tv = (TextView) findViewById(R.id.textView);
        qnum = (TextView) findViewById(R.id.questionnumber);
        tweet = (Button) findViewById(R.id.tweetit);

        checkVoiceRecognition();
        ranNum = setBinary();
        binary.setText(ranNum);
        Bundle bunble=getIntent().getExtras();
        if(bunble!=null)
        {
            String color = bunble.getString("color");
            lightcolor = bunble.getString("color");
//            System.out.println();
//            System.out.println();
//
//            System.out.println(light(ranNum, color));
            sendJson(light(ranNum, color), false);
        }

    }

    public void checkVoiceRecognition() {
        // Check if voice recognition is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            mbtSpeak.setEnabled(false);
            mbtSpeak.setText("Voice recognizer not present");
            Toast.makeText(this, "Voice recognizer not present",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
                .getPackage().getName());



        // Given an hint to the recognizer about what the user is going to say
        //There are two form of language model available
        //1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
        //2.LANGUAGE_MODEL_FREE_FORM  : If not sure about the words or phrases and its domain.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);


        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        //Start the Voice recognizer activity for the result.
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            //If Voice recognition is successful then it returns RESULT_OK
            if (resultCode == RESULT_OK) {

                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                System.out.println(textMatchList.get(0));
                mlvTextMatches.setText(textMatchList.get(0));
                int b = 0;
                try
                {
                    //nextInt will throw InputMismatchException
                    //if the next token does not match the Integer
                    //regular expression, or is out of range
                    b =Integer.parseInt(textMatchList.get(0));


                    if(Integer.toBinaryString(b).equals(ranNum)){
                        coi.setText("CORRECT!");
                        score = score + 10;
                        guessed = true;
                        mbtSpeak.setEnabled(false);



                            sendJson(light(ranNum, "\"red\":0, \"blue\":0, \"green\":255"), false);
                    }
                    else{
                        coi.setText("INCORRECT!");
                        sendJson(light(ranNum, "\"red\":255, \"blue\":0, \"green\":0"), false);
                        guessed = true;
                        mbtSpeak.setEnabled(false);



                    }

                }
                catch(NumberFormatException exception)
                {
                    //Print "This is not an integer"
                    //when user put other than integer
                    coi.setText("Please Say A Number!");
                }
                if (!textMatchList.isEmpty()) {
                    // If first Match contains the 'search' word
                    // Then start web search.
                    if (textMatchList.get(0).contains("search")) {

                        String searchQuery = textMatchList.get(0);
                        searchQuery = searchQuery.replace("search", "");
                        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                        search.putExtra(SearchManager.QUERY, searchQuery);
                        startActivity(search);
                    } else {
                        // populate the Matches
//                        mlvTextMatches
//                                .setText(new ArrayAdapter<String>(this,
//                                        android.R.layout.simple_list_item_1,
//                                        textMatchList).getItem(0));
                    }

                }
                //Result code for various error.
            } else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
                showToastMessage("Audio Error");
            } else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
                showToastMessage("Client Error");
            } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
                showToastMessage("Network Error");
            } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
                showToastMessage("No Match");
            } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
                showToastMessage("Server Error");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Helper method to show the toast message
     */
    void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public static String setBinary() {

        int max = 63;
        int value;
        String binary;

        value = (int)(Math.random() * max + 1);

        binary = Integer.toBinaryString(value);

        return binary;
    }
    public static String light(String lights, String colorChoice) {

        String json = "[";
        boolean lightAdded = false;
        int length = lights.length();
        int i = 1;


        while (i <= length) {

            if (lights.charAt(length - i) == '1') {
                if (lightAdded) {
                    json += ",";
                }

                json += "\n{ \"lightId\": " + i + "," + colorChoice + ", \"intensity\": 0.5}";

                lightAdded = true;
            } else if (!lightAdded) {
                json += "\n{ \"lightId\": " + i + "," + colorChoice + ", \"intensity\": 0.0},";
            }

            i++;
        }

        json += "\n]";

        if (!lightAdded) {
            json = "[{ \"lightId\": 1, " + colorChoice + ", \"intensity\": 0.0}]";
        }

        return json;
    }


    protected void sendJson(final String json, final Boolean prop) {
        Thread t = new Thread() {

            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                String ipAddress = "0.0.0.0";

                try {
                    Bundle bunble=getIntent().getExtras();
                    if(bunble!=null)
                    {
                        //Getting the value stored in the name "NAME"
                        ipAddress = bunble.getString("ip");
                    }
                    HttpPost post = new HttpPost("http://"+ipAddress+"/rpi");
                    StringEntity se = new StringEntity( "{\"lights\": "+json+", \"propagate\": "+prop+"}");
//                    System.out.println( "{\"lights\": "+json+", \"propagate\": "+prop+"}");
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        };

        t.start();
    }

    Handler qnumhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.questionnumber);
            myTextView.setText("Question " + qn);
        }
    };

    Handler binaryhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.binarynumber);
            myTextView.setText(ranNum);
        }
    };

    public void next(View view){
        if (guessed == true) {
            qn++;
            mbtSpeak.setEnabled(true);
            guessed = false;

            if (qn == 11) {
                mlvTextMatches.setVisibility(view.INVISIBLE);
                mbtSpeak.setVisibility(view.INVISIBLE);
                coi.setVisibility(view.INVISIBLE);
                next.setVisibility(view.INVISIBLE);
                tv.setVisibility(view.INVISIBLE);
                tweet.setVisibility(view.VISIBLE);

                qnum.setText("You're score: ");
                what.setText(Integer.toString(score) + "%");

                System.out.println("i'm now 11");
                if(score == 100 || score == 90){
                    binary.setText("You're a binary master!");
                }
                else if(score == 80 || score == 70){
                    binary.setText("You're a binary wiz!");
                }
                else if(score == 60 || score == 50){
                    binary.setText("You're a binary beginner!");
                }
                else{
                    binary.setText("You're binary skills could use some work. Maybe you should spend some time in the classroom!");
                }

            }
            else{
                ranNum = setBinary();
                qnumhandler.sendEmptyMessage(0);
                binaryhandler.sendEmptyMessage(0);

                mlvTextMatches.setText("You said:");
                coi.setText(" ");
                sendJson(light(ranNum, lightcolor), false);

            }
        }
        else{
            coi.setText("Please guess a number");
        }

    }

    public void tweet(View view){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("score", Integer.toString(score));
        startActivity(i);
    }

}