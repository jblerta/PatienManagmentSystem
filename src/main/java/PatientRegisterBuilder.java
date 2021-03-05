public class PatientRegisterBuilder {
    private String pacientID;
    private String fullName;
    private String dateOfBirth;
    private String city;
    private String date;
    private String info;
    private String imgName;

    public PatientRegisterBuilder setImgName(String imgName) {
        this.imgName = imgName;
        return this;
    }

    public PatientRegisterBuilder setInfo(String info) {
        this.info = info;
        return this;
    }

    public PatientRegisterBuilder setPacientID(String pacientID) {
        this.pacientID = pacientID;
        return this;
    }

    public PatientRegisterBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PatientRegisterBuilder setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public PatientRegisterBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public PatientRegisterBuilder setDate(String date) {
        this.date = date;
        return this;
    }
    public RegisterClass getRegisterClass(){
        return new RegisterClass(pacientID,fullName,dateOfBirth,city,date,info,imgName);
    }
}
