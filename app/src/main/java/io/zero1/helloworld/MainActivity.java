package io.zero1.helloworld;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ActivityManager.RunningServiceInfo> taskInfos ;

//    ActivityManager.RunningAppProcessInfo depreciated..
    Context context;
    ActivityManager activityManager;
    PackageManager packageManager;
    private static final String TAG="Application";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        packageManager = getPackageManager();
        activityManager = (ActivityManager) getSystemService(context.ACTIVITY_SERVICE);
        taskInfos =activityManager.getRunningServices(Integer.MAX_VALUE);

        getListOfRunningApps();


    }

    private void getListOfRunningApps() {

//        to get list of installed application...
//        ************************************/


//        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
//        for (int i=0; i < packList.size(); i++)
//        {
//            PackageInfo packInfo = packList.get(i);
//            if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
//            {
//                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
//                Log.d(TAG + Integer.toString(i), appName);
//            }
//        }


//        ******************************
        if(Build.VERSION.SDK_INT>=23){

            if(taskInfos!=null){

                for (int i=0; i<taskInfos.size();i++){

                    String packageName = taskInfos.get(i).process.toString();

                    Log.d(TAG,packageName);


                }

            }


        } else if(Build.VERSION.SDK_INT <23) {
            showMessage("i m below 23");

            if(taskInfos!=null){

                for (int i=0; i<taskInfos.size();i++){
                    String nul = String.valueOf(taskInfos.size());


                    String packageName = taskInfos.get(i).process.toString();
                   String name= taskInfos.get(i).service.getClassName().toString();
//                    String lable = taskInfos.get(i).

                    /// start gettin application name...
                    int pi= taskInfos.get(i).pid;

                    Log.d(TAG,packageName + " "+pi+" "+name);
                    Log.d(TAG,nul);

                }

            }
        }


    }

    private void showMessage(String s) {
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }


}
