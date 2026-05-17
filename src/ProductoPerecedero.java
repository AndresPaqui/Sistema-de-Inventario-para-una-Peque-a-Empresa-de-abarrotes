import java.util.Random;

public class ProductoPerecedero extends Producto{
    private int dia;
    private int mes;
    private int año;

    public ProductoPerecedero(int codigo, String nombre, double precio,
                              int cantidad, int ID, Random random,
                              int dia, int mes, int año) {
        super(codigo, nombre, precio, cantidad, ID = random.nextInt(1000), random);
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
