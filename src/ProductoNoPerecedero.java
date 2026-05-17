import java.util.Random;

public class ProductoNoPerecedero extends Producto{

    private int garantia;

    public ProductoNoPerecedero(int codigo, String nombre, double precio, int cantidad,
                                int ID, Random random, int garantia) {
        super(codigo, nombre, precio, cantidad, ID = random.nextInt(1000), random);
        this.garantia = garantia;
    }
}
