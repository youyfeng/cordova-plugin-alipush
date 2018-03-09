package com.blanktrack.alipush;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.GcmRegister;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Blank on 2017-08-24.
 */

public class AliPushPlugin extends CordovaPlugin {
    public static final String TAG = "PushPlugin";
    private JSONObject params;
    private static CallbackContext pushContext;

    public static CallbackContext getCurrentCallbackContext() {
        return pushContext;
    }

    public static void initCloudChannel(Context applicationContext) {

        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String xiaomi = applicationInfo.metaData.getString("com.blanktrack.miid");
        String xiaomikey = applicationInfo.metaData.getString("com.blanktrack.mikey");
        String gcmSendId = applicationInfo.metaData.getString("com.blanktrack.gcmsendid");
        String gcmAppId = applicationInfo.metaData.getString("com.blanktrack.gcmappid");
        MiPushRegister.register(applicationContext, xiaomi, xiaomikey);
        HuaWeiRegister.register(applicationContext);
        GcmRegister.register(applicationContext, gcmSendId, gcmAppId);
    }

    private Context getApplicationContext() {
        return this.cordova.getActivity().getApplicationContext();
    }

    @Override
    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        CloudPushService service = PushServiceFactory.getCloudPushService();
        CommonCallback callback = new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                callbackContext.success(response);
            }

            @Override
            public void onFailed(String errCode, String errMsg) {
                callbackContext.error(errMsg);
            }
        };
        switch (action) {
            case "_init":
            case "init": {
                pushContext = callbackContext;
                PluginResult result = new PluginResult(PluginResult.Status.OK);
                result.setKeepCallback(true);
                callbackContext.sendPluginResult(result);
                break;
            }
            case "getDeviceId": {
                callbackContext.success(service.getDeviceId());
                break;
            }
            case "bindAccount": {
                if (args.length() < 1) {
                    callbackContext.error("invalid arguments");
                } else {
                    String account = args.getString(0);
                    service.bindAccount(account, callback);
                }
                break;
            }
            case "unbindAccount": {
                service.unbindAccount(callback);
                break;
            }
            case "unbindTag":
            case "bindTag": {
                if (args.length() < 2) {
                    callbackContext.error("invalid arguments");
                } else {
                    int target = args.getInt(0);
                    JSONArray array = args.getJSONArray(1);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        list.add(array.getString(i));
                    }
                    String alias = args.getString(2);
                    if ("bindTag".equals(action)) {
                        service.bindTag(target, list.toArray(new String[0]), alias, callback);
                    } else {
                        service.unbindTag(target, list.toArray(new String[0]), alias, callback);
                    }

                }
                break;
            }
            case "listTags":{
                service.listTags(CloudPushService.DEVICE_TARGET, callback);
                break;
            }
            case "addAlias":{
                service.addAlias(args.getString(0), callback);
                break;
            }
            case "removeAlias":{
                service.removeAlias(args.getString(0), callback);
                break;
            }
            case "listAliases":{
                service.listAliases(callback);
                break;
            }
        }

        return true;
    }

}
