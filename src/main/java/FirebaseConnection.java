import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.connection.ConnectionContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class FirebaseConnection {DatabaseReference database;
void st(){    try{
    FileInputStream serviceAccount=new FileInputStream("src/main/resources/firebasekey/javacovid19-80ae9-firebase-adminsdk-96ro0-284c0f69ba.json");
    FirebaseOptions options= FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://javacovid19-80ae9-default-rtdb.firebaseio.com/")
            .build();
    FirebaseApp.initializeApp(options);
    database= FirebaseDatabase.getInstance().getReference("user");

}
catch (FileNotFoundException e){
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}
}}


