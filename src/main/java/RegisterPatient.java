import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.database.*;
import com.sun.tools.javac.Main;
import io.grpc.Context;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class RegisterPatient extends JFrame {
    private JPanel panel1;
    private JTextField txtPatietID;
    private JTextField txtDob;
    private JTextField txtPatientName;
    private JTextField txtCity;
    private JRadioButton yesRadioButton;
    private JTextArea textArea1;
    private JButton btnReg;
    private JButton chooseImageButton;
    private JLabel im;
    private JLabel lblinfo;
    private JButton backButton;
    private JTextField txtImageName;
    private JLabel image;
    private URI imgUri;

    DatabaseReference ref;
    RegisterClass regClass;
    User user;


    public RegisterPatient(String id) {
        setPreferredSize(new Dimension(500, 580));
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        textArea1.hide();
        lblinfo.hide();

        //get current date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //reference to database
        ref= FirebaseDatabase.getInstance().getReference("user");

        //Register patient
        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(id);
                String pacientID= txtPatietID.getText();
                String name= txtPatientName.getText();
                String dob= txtDob.getText();
                String city= txtCity.getText();
                String date=dtf.format(now);
                String addInfo=textArea1.getText();
                String imageName=txtImageName.getText();

                regClass=new RegisterClass(pacientID,name,dob,city,date,addInfo,imageName);
                ref.child(id).child("patient").child(pacientID).setValue(regClass,new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError!=null){
                            JOptionPane.showMessageDialog(RegisterPatient.this,"Patient could be saved!"+databaseError.getMessage());

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(RegisterPatient.this,"Patient registered succesfully!");
                            new MainPage(id);
                            RegisterPatient.this.hide();
                        }

                    }
                });
            }
        });

        // get and upload image
        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                JFileChooser jfc = new JFileChooser();
                //  jfc.showOpenDialog(null);
                String o;
                jfc.setFileFilter(new FileNameExtensionFilter("*.png", "png","jpg"));
                if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();

                    o=file.getAbsolutePath();
                    BufferedImage img = ImageIO.read(new File(o));
                    Image resized = img.getScaledInstance(image.getWidth(),image.getHeight(),
                            Image.SCALE_SMOOTH);
                    image.setIcon(new ImageIcon(resized));
                    InputStream testFile = new FileInputStream(o);
                    BlobId blobId = BlobId.of("javacovid19-80ae9.appspot.com",txtImageName.getText());
                    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build();
                    Blob blob = storage.create(blobInfo,testFile);


                } else {
                    System.out.println("No file choosen!");
                }


            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }


                }

        });

        //radio button show textflied when clicked
        yesRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {


                    JRadioButton button = (JRadioButton) e.getSource();
                    String text = button.getText();
                    if(button.isSelected()) {
                        textArea1.setVisible(true);
                        lblinfo.setVisible(true);

                    }
                    else{
                        textArea1.hide();
                    }
            }
        });

        // Back to mainPage
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage(id);
                RegisterPatient.this.hide();
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


