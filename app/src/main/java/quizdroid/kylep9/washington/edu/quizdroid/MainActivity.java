// Kyle Peterson, INFO 498, QuizDroid
// Home page
package quizdroid.kylep9.washington.edu.quizdroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;


public class MainActivity extends ActionBarActivity {
    protected QuizApp app;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (QuizApp) getApplication();
        QuizApp instance = app.getInstance();
        SimpleTopicRepo repo = instance.topicRepo;
        Iterator topicIt = repo.getTopicNames().iterator();
        final Button button1 = (Button) findViewById(R.id.button1);
        String topic = (String) topicIt.next();
        button1.setText(topic);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent nextActivity = new Intent(MainActivity.this, QuestionActivity.class);
                nextActivity.putExtra("topic-name", button1.getText());
                startActivity(nextActivity);
            }
        });

        final Button button2 = (Button) findViewById(R.id.button2);
        topic = (String) topicIt.next();
        button2.setText(topic);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent nextActivity = new Intent(MainActivity.this, QuestionActivity.class);
                nextActivity.putExtra("topic-name", button2.getText());
                startActivity(nextActivity);
            }
        });

        final Button button3 = (Button) findViewById(R.id.button3);
        topic = (String) topicIt.next();
        button3.setText(topic);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent nextActivity = new Intent(MainActivity.this, QuestionActivity.class);
                nextActivity.putExtra("topic-name", button3.getText());
                startActivity(nextActivity);

            }
        });
        initializeAlarm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.pref_button:
                final Intent nextActivity = new Intent(MainActivity.this, UserSettingActivity.class);
                startActivityForResult(nextActivity, 1);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(MainActivity.this, RepeatingAlarm.class);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int interval = Integer.parseInt(sharedPrefs.getString("alarmFrequency", "1"));
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 7000, pendingIntent);
        Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

}
