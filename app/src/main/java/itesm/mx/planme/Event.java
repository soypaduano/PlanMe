package itesm.mx.planme;


public class Event {

    private String nombre;
    private String descripcion;
    private String horario;
    private String tipodeplan;
    private byte[] byteArray;
    private String correo;

    public Event() {
    }

    public Event(String nombre, String descripcion, String horario, String tipodeplan, byte[] byteArray, String correo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horario = horario;
        this.tipodeplan = tipodeplan;
        this.byteArray = byteArray;
        this.correo = correo;
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

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
