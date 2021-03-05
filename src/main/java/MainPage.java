
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Query;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class MainPage extends JFrame {
    private JPanel panel1;
    private JLabel txtWelcome;
    private JButton newPatientButton;
    private JButton searchButton;
    DatabaseReference ref,li;
    RegisterClass regClass;
    User user;

    protected JList<RegisterClass> patientList = new JList<>();
    private JLabel cnumber;
    protected CustomListModel<RegisterClass> listModel;
    protected ArrayList<RegisterClass> patients = new ArrayList<>();


    public MainPage(String id) {
        setPreferredSize(new Dimension(350, 400));
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        ref=FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference li=ref.child(id).child("patient");

        //get doctor name for welcome label
        ref.child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        txtWelcome.setText("Welcome Doctor:"+user.getName());

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //patient List


                patientList.setPreferredSize(new Dimension(300, 360));
            setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
            ref.child(id).child("patient").orderByChild("date").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RegisterClass u = dataSnapshot.getValue(RegisterClass.class);
                    String name = u.getpacientID();
                    System.out.println(name);
                    listModel = new CustomListModel<RegisterClass>(patients);
                    patientList.setModel(listModel);
                    listModel.addElement(new RegisterClass(name));
                    add(patientList);


                }


                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





        newPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterPatient(id);
            }
        });



        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPersons();

            }
        });

        patientList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JList target = (JList) e.getSource();
                    int index = target.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Object item = target.getModel().getElementAt(index);
                        li.child(item.toString()).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        RegisterClass patient = dataSnapshot.getValue(RegisterClass.class);
                                        cnumber.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                                        new PatientDetails(id,patient.getpacientID(),patient.getfullName(),patient.getdateOfBirth(),patient.getCity(),patient.getDate(),patient.getAdditionalInformation(),patient.getImageName());



                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



    }
    private void searchPersons() {
        String personName = JOptionPane.showInputDialog(this, "Enter patient id to search for:");

        if (personName == null) {
            return;
        }

        Collections.sort(patients);

        int foundIndex = Collections.binarySearch(patients, new RegisterClass(personName));

        if (foundIndex >= 0) {
            patientList.setSelectedIndex(foundIndex);
        } else {
            JOptionPane.showMessageDialog(MainPage.this, "Could not find patient " + personName);
        }
    }



}

