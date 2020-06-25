package com.thewire.med.config.firebase;

import com.thewire.med.security.models.SecurityProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

@Configuration
public class FirebaseConfig
{
	@Autowired
	SecurityProperties securityProperties;

	@Primary
	@Bean
	public void firebaseInit() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.getApplicationDefault())
				.setDatabaseUrl(securityProperties.getFirebaseProps().getDatabaseUrl()).build();
		if(FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
	}

	@Bean
	public Firestore getDatabase() throws IOException {
		FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
				.setCredentials(GoogleCredentials.getApplicationDefault()).build();
		return firestoreOptions.getService();
	}
}
