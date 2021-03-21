package com.fff.challenge;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FBInitialize {
	
	@PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount = new ClassPathResource("/serviceaccount.json").getInputStream();
            System.out.println();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://challenge-data-process-default-rtdb.firebaseio.com")
                    .build();

            if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
            	FirebaseApp.initializeApp(options);            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
