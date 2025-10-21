import java.time.LocalDate;

public abstract class Empleado {
    private String nombre;
    private String apellido;
    private int id;
    private static int contador=0;
    private LocalDate fechaDeNacimiento;
    private String dni;


    public Empleado(String nombre, String apellido, LocalDate fechaDeNacimiento, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.dni = dni;
        contador++;
        id=contador;
    }

    public Empleado() {
        nombre="a";
        apellido="a";
        fechaDeNacimiento=LocalDate.of(2000, 1, 1);
        dni="a";
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

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getDni() {
        return dni;
    }

}
