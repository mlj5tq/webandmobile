package learn2crack.androidtwitterapi;

import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.entity.StringEntity;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicHeader;
        import org.apache.http.protocol.HTTP;

        import java.io.InputStream;

public class App2Activity extends Activity {

    String ipAddress = "0.0.0.0";
    String colorChoice = "\"red\":0, \"blue\":255, \"green\":0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app2);


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.myTextView);
            myTextView.setText("Lights are blue!");
        }
    };
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.myTextView);
            myTextView.setText("Lights are pink!");
        }
    };
    Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.myTextView);
            myTextView.setText("Lights are teal!");
        }
    };
    Handler handler5 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.myTextView);
            myTextView.setText("Lights are orange!");
        }
    };
    Handler handler6 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.myTextView);
            myTextView.setText("Lights are yellow!");
        }
    };
    Handler handler7 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.myTextView);
            myTextView.setText("Lights are purple!");
        }
    };

    Handler handler4 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView =
                    (TextView)findViewById(R.id.myTextView);
            myTextView.setText("IP Address Set!");
        }
    };


    public void setBlue(View view){
//        sendJson(1,0,0,255,true, 0.5);
        handler.sendEmptyMessage(0);
        colorChoice = "\"red\":0, \"blue\":255, \"green\":0";
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("color", colorChoice);
        intent.putExtra("ip", ipAddress);
        startActivity(intent);
    }

    public void setRed(View view){
//        sendJson(1,255,0,0,true, 0.5);
        handler2.sendEmptyMessage(0);
        colorChoice = "\"red\":255, \"blue\":180, \"green\":105";
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("color", colorChoice);
        intent.putExtra("ip", ipAddress);

        startActivity(intent);

    }

    public void setGreen(View view){
//        sendJson(1,0,255,0,true, 0.5);
        handler3.sendEmptyMessage(0);
        colorChoice = "\"red\":0, \"blue\":128, \"green\":128";
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("color", colorChoice);
        intent.putExtra("ip", ipAddress);
        startActivity(intent);


    }

    public void setOrange(View view){
//        sendJson(1,255,128,0,true, 0.5);
        handler5.sendEmptyMessage(0);
        colorChoice = "\"red\":255, \"blue\":0, \"green\":128";
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("color", colorChoice);
        intent.putExtra("ip", ipAddress);

        startActivity(intent);

    }

    public void setYellow(View view){
//        sendJson(1,255,255,0,true, 0.5);
        handler6.sendEmptyMessage(0);
        colorChoice = "\"red\":255, \"blue\":0, \"green\":255";
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("color", colorChoice);
        intent.putExtra("ip", ipAddress);

        startActivity(intent);

    }

    public void setPurple(View view){
//        sendJson(1,255,0,255,true, 0.5);
        handler7.sendEmptyMessage(0);
        colorChoice = "\"red\":255, \"blue\":255, \"green\":0";
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("color", colorChoice);
        intent.putExtra("ip", ipAddress);

        startActivity(intent);

    }

    public void buttonClick(View view){
        Runnable runnable = new Runnable(){
            public void run(){
                EditText editText = (EditText)findViewById(R.id.edit_message);

                String editTextStr = editText.getText().toString();
                System.out.println(editTextStr);
                ipAddress = editTextStr;
                handler4.sendEmptyMessage(0);

            }

        };
        Thread mythread = new Thread(runnable);
        mythread.start();

    }

//
//
//    protected void sendJson(final String json, final Boolean prop) {
//        Thread t = new Thread() {
//
//            public void run() {
//                HttpClient client = new DefaultHttpClient();
//                HttpResponse response;
//
//                try {
//                    HttpPost post = new HttpPost("http://"+ipAddress+"/rpi");
//                    StringEntity se = new StringEntity( "{\"lights\": "+json+", \"propagate\": "+prop+"}");
//                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                    post.setEntity(se);
//                    response = client.execute(post);
//
//                    /*Checking response */
//                    if(response!=null){
//                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
//                    }
//
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//
//        t.start();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}