package com.jessicathornsby.pinnedshortcut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.Icon;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.net.Uri;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Create an instance of the ShortcutManager//

        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

//Create a ShortcutInfo object that defines all the shortcut’s characteristics//

        ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "pinned-shortcut")
                .setShortLabel("Android Auth")
                .setLongLabel("Launch Android Authority")
                .setIcon(Icon.createWithResource(this, R.mipmap.launch_url))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.androidauthority.com/")))
                .build();

        shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));

//Check that the device's default launcher supports pinned shortcuts//

        if (shortcutManager.isRequestPinShortcutSupported()) {
            ShortcutInfo pinShortcutInfo = new ShortcutInfo
                    .Builder(MainActivity.this,"pinned-shortcut")
                    .build();
            Intent pinnedShortcutCallbackIntent =
                    shortcutManager.createShortcutResultIntent(pinShortcutInfo);

//Get notified when a shortcut is pinned successfully//

            PendingIntent successCallback = PendingIntent.getBroadcast(MainActivity.this, 0,
                    pinnedShortcutCallbackIntent, 0);
            shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.getIntentSender());
        }

    }

}
