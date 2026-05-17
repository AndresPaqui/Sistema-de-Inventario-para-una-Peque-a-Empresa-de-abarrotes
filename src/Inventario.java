import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<Producto> listaProductos = new ArrayList<>();

    public int agregar(Producto p) {listaProductos.add(p);return 1;}

    public int eliminar(int id) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getID() == id) {
                listaProductos.remove(i);
                return 1;
            }
        }
        return 0;
    }

    public int mostrar() {
        for (int i = 0; i < listaProductos.size(); i++) {
            System.out.println(listaProductos.get(i).getNombre());
        }
        return listaProductos.size();
    }

    public List<Producto> getListaProductos() { return listaProductos; }
    public void setListaProductos(List<Producto> lista) { this.listaProductos = lista; }
}
