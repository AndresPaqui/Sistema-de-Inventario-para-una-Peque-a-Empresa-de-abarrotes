public class Main {
    public static void main(String[] args) {
        Inventario inventario  = new Inventario();
        DatosMiau controlador = new DatosMiau(inventario);
        Interfaz interfaz      = new Interfaz(controlador);
        interfaz.mostrar();
    }
}
