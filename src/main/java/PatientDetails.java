import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.database.*;

public class PatientDetails extends JFrame{
    private JPanel panel1;
    private JLabel txtTitle;
    private JLabel txtPid;
    private JLabel txtPdob;
    private JLabel txtPcity;
    private JLabel txtPname;
    private JLabel txtP;
    private JLabel txtimg;
    private JButton deleteButton;
    private JButton editButton;
    private JLabel txtInfo;
    private JButton backButton;
    private JTextPane textPane1;
    DatabaseReference ref;


    public PatientDetails(String doctorID,String patientID,String pName,String pDOB,String pCity,String pDate,String info,String imgName) {
        setPreferredSize(new Dimension(450, 500));
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        txtPid.setText(patientID);
        txtPname.setText(pName);
        txtPdob.setText(pDOB);
        txtPcity.setText(pCity);
        txtP.setText(pDate);
        txtInfo.setText(info);

        ref= FirebaseDatabase.getInstance().getReference("user");
        try
        {
            Storage storage =
                    StorageOptions.newBuilder()
                            .setCredentials(
                                    ServiceAccountCredentials.fromStream(
                                            new FileInputStream("src/main/resources/firebasekey/javacovid19-80ae9-firebase-adminsdk-96ro0-284c0f69ba.json")
                                    )
                            )
                            .build()
                            .getService();
            Blob blob = storage.get(BlobId.of("javacovid19-80ae9.appspot.com","th.jpg"));
            blob.downloadTo(Paths.get("tmp.png"));
            txtimg.setText("");
            loadImage("tmp.png",txtimg);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ref.child(doctorID).child("patient").child(patientID).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        JOptionPane.showMessageDialog(PatientDetails.this,"Patient deleted successfully.");
                        new MainPage(doctorID);
                        PatientDetails.this.hide();

                    }
                });

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ref.child(doctorID).child("patient").child(patientID).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                RegisterClass patient = dataSnapshot.getValue(RegisterClass.class);
                                new EditPatient(doctorID,patient);
                                new MainPage(doctorID);
                                PatientDetails.this.hide();



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage(doctorID);
                PatientDetails.this.hide();
            }
        });
    }
    private void loadImage(String path, JLabel pictureLabel){
        pictureLabel.setIcon(null);
        try {
            BufferedImage img = ImageIO.read(new File(path));
            Image resized = img.getScaledInstance(pictureLabel.getWidth(),pictureLabel.getHeight(),
                    Image.SCALE_SMOOTH);
            pictureLabel.setIcon(new ImageIcon(resized));
            pictureLabel.revalidate();
            pictureLabel.repaint();
            pictureLabel.update(pictureLabel.getGraphics());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
