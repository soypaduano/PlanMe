package itesm.mx.planme;


public class Event {

    private String nombre;
    private String descripcion;
    private String horario;
    private String tipodeplan;
    private String address;
    private String byteArray;
    private String uid;

    public Event() {
    }

    public Event(String nombre, String descripcion, String horario, String tipodeplan, String address, String byteArray, String uid) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horario = horario;
        this.tipodeplan = tipodeplan;
        this.address = address;
        this.byteArray = byteArray;
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTipodeplan() {
        return tipodeplan;
    }

    public void setTipodeplan(String tipodeplan) {
        this.tipodeplan = tipodeplan;
    }

    public String getByteArray() {
        return byteArray;
    }

    public void setByteArray(String byteArray) {
        this.byteArray = byteArray;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }
}
