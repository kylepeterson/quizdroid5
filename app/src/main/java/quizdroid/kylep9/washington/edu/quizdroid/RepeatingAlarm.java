package quizdroid.kylep9.washington.edu.quizdroid;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by iguest on 2/23/15.
 */
public class RepeatingAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, final Intent intent) {
        Log.i("download", "alarm received");
        SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(context);
        String urlString1 = sharedPrefs1.getString("url", "No URL Set");
        Toast.makeText(context, urlString1, Toast.LENGTH_SHORT).show();
        if (Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0) {
            Log.i("download", "airplane mode on");

            // Airplane mode is on
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Airplane Mode Is On.\nTurn Off to Proceed.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create();
        } else {

            if(!isConnected(context)) {
                Log.i("download", "not connected");
                // Offline (No bars)
                new AlertDialog.Builder(context)
                        .setTitle("Currently Offline")
                        .setMessage("Cannot Download Questions")
                        .show();
            } else {
                Log.i("download", "download beginning");

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                String urlString = sharedPrefs.getString("url", "No URL Set");
                Log.i("download", "urlString: " + urlString);

                int count;
                /*try {
                    URL url = new URL("http://txt2html.sourceforge.net/sample.txt");
                    Log.i("download", "url set to " + url.toString());
                    URLConnection connect = url.openConnection();
                    connect.connect();
                    Log.i("download", "connected");

                    int fileLength = connect.getContentLength();
                    InputStream input = url.openStream();
                    FileOutputStream fos = new FileOutputStream("quizdata.json");
                    byte data[] = new byte[1024];
                    long total = 0;
                    int progress = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        progress = (int) total * 100 / fileLength;
                        fos.write(data, 0, count);
                    }
                    fos.close();

                } catch (Exception e) {
                    Log.i("download", "ERROR" + e.getMessage());
                    Log.e("DOWNLOAD ERROR", "Error downloading" + e.getMessage());
                    new AlertDialog.Builder(context)
                            .setTitle("Download Error")
                            .setMessage("Question Download Failed")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("Quit App", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })
                            .show();
                }*/
                StringBuilder builder = new StringBuilder();
                HttpClient client = new DefaultHttpClient();

                try {
                    URI uri = new URI("http://txt2html.sourceforge.net/sample.txt");
                    HttpGet httpGet = new HttpGet();
                    httpGet.setURI(uri);
                    HttpResponse response = client.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                        String line;
                        while((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    }
                } catch(Exception e) {
                    Log.i("download", "ERROR" + e.getMessage());
                    Log.e("DOWNLOAD ERROR", "Error downloading" + e.getMessage());
                    new AlertDialog.Builder(context)
                            .setTitle("Download Error")
                            .setMessage("Question Download Failed")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("Quit App", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })
                            .show();
                }
            }
        }
    }

    private static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected()));
    }
}