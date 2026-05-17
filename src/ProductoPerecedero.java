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
}
