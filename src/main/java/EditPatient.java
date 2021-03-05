import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditPatient extends JFrame {
    private JPanel panel1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button1;
    private JLabel txtPid;
    DatabaseReference ref;


    //  public EditPatient(String getpacientID, String getfullName, String getdateOfBirth, String city, String date, String additionalInformation) {
    public EditPatient(String doctorID, RegisterClass p1) {

        setPreferredSize(new Dimension(500, 520));
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        ref = FirebaseDatabase.getInstance().getReference("user");


        txtPid.setText(p1.getpacientID());
        textField2.setText(p1.getfullName());
        textField3.setText(p1.getdateOfBirth());
        textField4.setText(p1.getCity());
        textArea1.setText(p1.getAdditionalInformation());




        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pacientID= p1.getpacientID();
                String name= textField2.getText();
                String dob= textField3.getText();
                String city= textField4.getText();
                String date=dtf.format(now);
                String addInfo=textArea1.getText();
                String img=null;

                RegisterClass newPatient=new RegisterClass(pacientID,name,dob,city,date,addInfo,img);

                ref.child(doctorID).child("patient").child(p1.getpacientID()).setValue(newPatient, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            JOptionPane.showMessageDialog(EditPatient.this, "Patient could be saved!" + databaseError.getMessage());

                        } else {
                            JOptionPane.showMessageDialog(EditPatient.this, "Patient edited succesfully!");
                            EditPatient.this.hide();
                        }
                    }

                });
            }
        });
    }
}
