package itesm.mx.planme;


public class Usuario {

    private String uid;
    private String name;
    private String surname;
    private String birthday;
    private String email;
    private String number;
    private String sexo;
    private String byteArray;

    public Usuario() {
    }

    public Usuario(String uid, String name, String surname, String birthday, String email, String number, String sexo, String byteArray) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
        this.number = number;
        this.sexo = sexo;
        this.byteArray = byteArray;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getbirthday() {
        return birthday;
    }

    public void setbirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getsurname() {
        return surname;
    }

    public void setsurname(String surname) {
        this.surname = surname;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getByteArray() {
        return byteArray;
    }

    public void setByteArray(String byteArray) {
        this.byteArray = byteArray;
    }
}