package itesm.mx.planme;


import java.io.Serializable;

public class Usuario implements Serializable{

    //Atributos
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

    //Constructor con valores de inicialización indicados
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


    //Setters
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setbirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setsurname(String surname) {
        this.surname = surname;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public void setByteArray(String byteArray) {
        this.byteArray = byteArray;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }



    //Getters

    public String getname() {
        return name;
    }

    public String getUid() {
        return uid;
    }


    public String getbirthday() {
        return birthday;
    }

    public String getsurname() {
        return surname;
    }

    public String getemail() {
        return email;
    }


    public String getnumber() {
        return number;
    }


    public String getSexo() {
        return sexo;
    }


    public String getByteArray() {
        return byteArray;
    }

}