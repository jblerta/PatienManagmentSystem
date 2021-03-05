
public class RegisterClass implements Comparable<RegisterClass> {
private String pacientID;
private String fullName;
private String dateOfBirth;
private String city;
private String date;
private String additionalInformation;
private String imageName;

      public RegisterClass() {
    }



    public RegisterClass(String pacientID, String fullName, String dateOfBirth, String city, String date,String additionalInformation,String imageName) {
        this.pacientID = pacientID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.city=city;
        this.date=date;
        this.additionalInformation=additionalInformation;
        this.imageName=imageName;

    }


    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public RegisterClass(String fullName) {
          this.fullName=fullName;
    }


    public String getDate() {
        return date;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getpacientID() {
        return pacientID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setpacientID(String pacientID) {
        this.pacientID = pacientID;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public String getdateOfBirth() {
        return dateOfBirth;
    }

    public void setdateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String toString() {
        return this.fullName;
    }

    @Override
    public int compareTo(RegisterClass another) {
        return this.fullName.compareTo(another.getfullName());
    }
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof RegisterClass) {
            RegisterClass another = (RegisterClass) obj;
            if (this.fullName.equals(another.getfullName())) {
                return true;
            }
        }

        return false;
    }
}
