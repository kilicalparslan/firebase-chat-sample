package com.alparslankilic.firebasechatsample;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        FirebaseApp.initializeApp(this);
        FirebaseInstallations.getInstance().getToken(true)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    if (task.getResult() != null) {
                        String refreshedToken = task.getResult().getToken();
                        Log.d(TAG, refreshedToken);
                        Toast.makeText(MyFirebaseInstanceIDService.this, refreshedToken, Toast.LENGTH_SHORT).show();
                        sendRegistrationToServer(refreshedToken);
                    }
                });


    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
