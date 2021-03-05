import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseReference;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Login extends JFrame {

    private JPanel panel1;
    private JTextField txtDoctorID;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel errorLabel;

    DatabaseReference ref;
    User user;


    public Login() {
        setPreferredSize(new Dimension(350, 400));
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        try{
            FileInputStream serviceAccount=new FileInputStream("src/main/resources/firebasekey/javacovid19-80ae9-firebase-adminsdk-96ro0-284c0f69ba.json");
            FirebaseOptions options= FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("javacovid19-80ae9.appspot.com")
                    .setDatabaseUrl("https://javacovid19-80ae9-default-rtdb.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);


        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     /*   btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userID= txtDoctorID.getText();
                String name=textField2.getText();
                String password=textField3.getText();
                user=new User(userID,name,password);



                ref.child(userID).setValue(user,new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError!=null){
                            errorLabel.setText("data can not be save "+databaseError.getMessage());

                        }
                        else
                        {
                            errorLabel.setText("data save successfully");
                        }

                    }
                });
            }
        });*/



        ref=FirebaseDatabase.getInstance().getReference("user");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID= txtDoctorID.getText();
                String password=txtPassword.getText();

                if(userID.isEmpty() || password.isEmpty()){
                    errorLabel.setForeground(Color.red);
                    errorLabel.setText("Please fill all fields!");
                }
                else {

                    try {
                        ref.child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                if (password.equals(user.getPassword())) {
                                    new MainPage(userID);
                                } else {
                                    errorLabel.setForeground(Color.red);
                                    errorLabel.setText("Wrong password! Pleas try again");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                catch(Exception e1){
                    errorLabel.setText("Error: "+e1.getMessage());

                }}
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterDoctor();
            }
        });


    }


    public static void main(String[] args) {
        new Login();

    }
}
