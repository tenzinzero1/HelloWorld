package io.zero1.TashiDev;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ActivityManager.RunningServiceInfo> taskInfos ;
    List<ActivityManager.RunningAppProcessInfo> rapinfo;
    List<AndroidAppProcess> processes ;

//    ActivityManager.RunningAppProcessInfo depreciated..
    Context context;
    ActivityManager activityManager;
    PackageManager packageManager;
    private static final String TAG="Application";
    PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        packageManager = getPackageManager();
        activityManager = (ActivityManager) getSystemService(context.ACTIVITY_SERVICE);
        taskInfos =activityManager.getRunningServices(Integer.MAX_VALUE);
        pm = getPackageManager();

        GetAllRunningApps();

       // getListOfRunningApps();
        //ListRunningApps();


    }

    private void GetAllRunningApps() {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
//            if the device is using above lollipop version

            UsingGetRunningServices();


        }else if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M && Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){

//            For lollipop device
            UsingLibraryGetAppInfo();


        }else {

            UsinggetRunningAppProcesses();

        }
    }

    private void UsingLibraryGetAppInfo() {

        processes = AndroidProcesses.getRunningAppProcesses();

        int data = processes.size();
        for(int i=0; i<data; i++){
            try{

            AndroidAppProcess process= processes.get(i);

            String pkname = process.getPackageName().toString();

                PackageInfo pm = process.getPackageInfo(context,0);
                String appName =pm.applicationInfo.loadLabel(packageManager).toString();
                String icon=pm.applicationInfo.loadIcon(packageManager).toString();
               Log.d(TAG,"App Name ="+appName);
//                Log.d(TAG,pkname + "Library"+"Total Apps ="+data +"appName="+appName+" icon"+icon) ;

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }



        }
    }

    private void UsinggetRunningAppProcesses() {

        rapinfo = activityManager.getRunningAppProcesses();
        int rapintosize= rapinfo.size();

        for(int i=0; i<rapintosize;i++){

            String pkgName = rapinfo.get(i).processName;
            Log.d(TAG,pkgName);

        }


    }

    private void UsingGetRunningServices() {

        taskInfos=activityManager.getRunningServices(Integer.MAX_VALUE);
        int task=taskInfos.size();

        for(int i=0;  i<task;i++){


            String pkname = taskInfos.get(i).service.getPackageName();
            Log.d(TAG,"Total Services"+task);
            Log.d(TAG,"Hello"+pkname);

        }
    }

    private void ListRunningApps() {

        List<ActivityManager.RunningAppProcessInfo> processe = AndroidProcesses.getRunningAppProcessInfo(context);

         int al= processe.size();
        for (int j=0; j<al;j++){

            String nannn = processe.get(j).processName.toString();
            Log.d(TAG,nannn);

        }


        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();

        int total = processes.size();
        Log.d(TAG, String.valueOf(total));
        for(int i=0; i<total;i++){
            try {

                if(AndroidProcesses.isMyProcessInTheForeground()){
                    AndroidAppProcess process=processes.get(i);
                    String name = process.name;
                    String pname=process.getPackageName().toString();


                    PackageInfo packinfo =process.getPackageInfo(context,0);
                    String appname =packinfo.applicationInfo.loadLabel(pm).toString();
                    Log.d(TAG,"App Name: "+ name+"and package name: "+ pname +" appname: "+appname);

                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

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
