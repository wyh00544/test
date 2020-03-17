package com.example.flutter_plugin;

import android.content.Context;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

/**
 * Created by Android Studio.
 * User: ahui
 * Date: 2020-03-17
 * Time: 11:31
 */
public class MyPlugin   implements MethodChannel.MethodCallHandler{

    private Context mContext;

    private MyPlugin(Context context) {
        mContext = context;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(PluginRegistry registry) {
        String CHANNEL = "com.example.flutter_plugin/plugin";
        PluginRegistry.Registrar registrar = registry.registrarFor(CHANNEL);
        MethodChannel methodChannel = new MethodChannel(registrar.messenger(), CHANNEL);
        MyPlugin myPlugin = new MyPlugin(registrar.activity());
        methodChannel.setMethodCallHandler(myPlugin);
    }



    @Override
    public void onMethodCall(MethodCall methodCall, final MethodChannel.Result result) {
        switch (methodCall.method) {
            case "add":
                int x = methodCall.argument("x");
                int y = methodCall.argument("y");
                String sum = (x + y) + "";
                result.success(sum);
                break;

        }
    }
}
