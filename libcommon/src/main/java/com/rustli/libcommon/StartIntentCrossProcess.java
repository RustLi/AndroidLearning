package com.rustli.libcommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * @date: 2019-11-26
 * @author: lwl
 * @description: 不同的进程间通过Intent的方式调用
 * 关键：需要将Intent由隐式转为显式，通过匹配action开启Intent
 * <p>
 * <service
 * android:name=".home.KeepWeChatAliveService"
 * android:enabled="true"
 * android:exported="true"
 * android:priority="1000"
 * android:process=":keepAlive" >
 * <intent-filter>
 * <action android:name="io.virtualapp.home.KeepWeChatAliveService" />
 * </intent-filter>
 * </service>
 */

public class StartIntentCrossProcess {

    private void startKeepAliveService(Context context) {
        Intent intent = new Intent();
        intent.setAction("io.virtualapp.home.KeepWeChatAliveService");
        final Intent explicitIntent = new Intent(createExplicitFromImplicitIntent(context, intent));
        context.startService(explicitIntent);
    }

    /**
     * des: Intent隐式转显式
     */
    private Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
