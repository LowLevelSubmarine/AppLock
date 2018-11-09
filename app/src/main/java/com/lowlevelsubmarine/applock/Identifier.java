package com.lowlevelsubmarine.applock;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Identifier {

    private static final MessageDigest DIGEST = createDigest();

    private final byte[] identifier;

    public Identifier(Context context) throws UnsupportedDeviceException {
        String deviceId = getDeviceId(context);
        String mac = getMac(context);
        String serial = getSerial(context);
        String rawIdentifier = "";
        if (deviceId != null) {
            rawIdentifier += deviceId;
        }
        if (mac != null) {
            rawIdentifier += mac;
        }
        if (serial != null) {
            rawIdentifier += serial;
        }
        if (rawIdentifier.equals("")) {
            throw new UnsupportedDeviceException();
        }
        this.identifier = DIGEST.digest(Base64.decode(rawIdentifier, 0));
    }

    public Identifier(String encoded) {
        this.identifier = Base64.decode(encoded, 0);
    }

    @Override
    public String toString() {
        return Base64.encodeToString(this.identifier, 0);
    }

    private static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private static String getMac(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    private static String getSerial(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                return Build.getSerial();
            } else {
                return null;
            }
        } else {
            return Build.SERIAL;
        }
    }

    private static MessageDigest createDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
