import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDoctor extends JFrame{
    private JPanel panel1;
    private JTextField txtuserID;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JButton registerButton;
    private JButton loginButton;
    User user;
    DatabaseReference ref;


    public RegisterDoctor(){
        setPreferredSize(new Dimension(350, 400));
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID= txtuserID.getText();
                String name=txtName.getText();
                String password=txtPassword.getText();
                user=new User(userID,name,password);

                ref= FirebaseDatabase.getInstance().getReference("user");

                ref.child(userID).setValue(user,new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError!=null){
                            JOptionPane.showMessageDialog(RegisterDoctor.this,"Doctor could not be registered!"+databaseError.getMessage());



                        }
                        else
                        {
                            new MainPage(userID);
                        }

                    }
                });
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
            }
        });
    }
}
