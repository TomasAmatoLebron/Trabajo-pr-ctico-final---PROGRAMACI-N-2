import java.sql.Timestamp;

public abstract class Empleado {
    private final String nombre;
    private final String apellido;
    private int id;
    private static int contador;
    private final Timestamp fechaDeNacimiento;
    private final String dni;


    public Empleado(String nombre, String apellido, Timestamp fechaDeNacimiento, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.dni = dni;
        contador++;
        id=contador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public abstract void estadoMesa(Mesa mesa);
}
