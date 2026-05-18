import java.util.List;
import java.util.Random;

public class DatosMiau {

    private Inventario inventario;
    private Random random = new Random();

    public DatosMiau(Inventario inventario) {
        this.inventario = inventario;
    }

    public void agregarPerecedero(String nombre, int codigo, double precio,
                                  int cantidad, int dia, int mes, int año) {

        ProductoPerecedero p = new ProductoPerecedero(
                codigo,
                nombre,
                precio,
                cantidad,
                random,
                dia,
                mes,
                año
        );

        inventario.agregar(p);

        System.out.println("Perecedero agregado");
    }

    public void agregarNoPerecedero(String nombre, int codigo, double precio,
                                    int cantidad, int garantia) {
        ProductoNoPerecedero p = new ProductoNoPerecedero(
                codigo,
                nombre,
                precio,
                cantidad,
                random,
                garantia
        );

        inventario.agregar(p);

        System.out.println("No perecedero agregado");
    }

    public void eliminarProducto(int id) {
        inventario.eliminar(id);
    }

    public void actualizarCantidad(int id, int nuevaCantidad) {
        for (Producto p : inventario.getListaProductos()) {
            if (p.getID() == id) {
                p.setCantidad(nuevaCantidad);
                break;
            }
        }
    }

    public List<Producto> getProductos() {
        return inventario.getListaProductos();
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Producto p : inventario.getListaProductos()) {
            total += p.getPrecio() * p.getCantidad();
        }
        return total;
    }

    //Llamamos a los metodos de inventario

}
