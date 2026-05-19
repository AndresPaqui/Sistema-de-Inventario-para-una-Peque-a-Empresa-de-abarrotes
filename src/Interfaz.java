import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Interfaz {

    private DatosMiau controlador;
    private DefaultTableModel modelo;
    private JLabel lblTotal;

    public Interfaz(DatosMiau controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        JFrame ventana = new JFrame("Comercial Don Andrés - Inventario");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(850, 500);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());


        String[] columnas = {"ID", "Nombre", "Precio", "Cantidad", "Tipo", "Info"};
        modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        ventana.add(new JScrollPane(tabla), BorderLayout.CENTER);


        lblTotal = new JLabel("Valor total: $0.00");
        ventana.add(lblTotal, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar   = new JButton("Agregar producto");
        JButton btnEliminar  = new JButton("Eliminar producto");
        JButton btnActualizar = new JButton("Actualizar cantidad");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        ventana.add(panelBotones, BorderLayout.SOUTH);


        btnAgregar.addActionListener(e -> {
            mostrarFormularioAgregar();
            refrescarTabla();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(ventana, "Seleccione un producto.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            controlador.eliminarProducto(id);
            refrescarTabla();
        });

        btnActualizar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(ventana, "Seleccione un producto.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            mostrarFormularioActualizar(id);
            refrescarTabla();
        });



        refrescarTabla();
        ventana.setVisible(true);
    }

    private void mostrarFormularioAgregar() {
        JTextField txtNombre   = new JTextField(15);
        JTextField txtPrecio   = new JTextField(15);
        JTextField txtCantidad = new JTextField(15);
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Perecedero", "No Perecedero"});
        JTextField txtDia      = new JTextField(5);
        JTextField txtMes      = new JTextField(5);
        JTextField txtAño      = new JTextField(5);
        JTextField txtGarantia = new JTextField(5);

        txtGarantia.setEnabled(false);

        cbTipo.addActionListener(e -> {
            if (cbTipo.getSelectedIndex() == 0) { 
                txtDia.setEnabled(true);
                txtMes.setEnabled(true);
                txtAño.setEnabled(true);
                txtGarantia.setEnabled(false);
                txtGarantia.setText(""); 
            } else { 
                txtDia.setEnabled(false);
                txtMes.setEnabled(false);
                txtAño.setEnabled(false);
                txtDia.setText(""); 
                txtMes.setText("");
                txtAño.setText("");
                txtGarantia.setEnabled(true);
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));        panel.add(txtPrecio);
        panel.add(new JLabel("Cantidad:"));      panel.add(txtCantidad);
        panel.add(new JLabel("Tipo:"));          panel.add(cbTipo);
        panel.add(new JLabel("Día venc.:"));     panel.add(txtDia);
        panel.add(new JLabel("Mes venc.:"));     panel.add(txtMes);
        panel.add(new JLabel("Año venc.:"));     panel.add(txtAño);
        panel.add(new JLabel("Garantía (m):")); panel.add(txtGarantia);

        int ok = JOptionPane.showConfirmDialog(null, panel,
                "Nuevo producto", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            String nombre   = txtNombre.getText().trim();
            int    codigo   = 0;
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int    cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (precio < 0 || cantidad < 0) {
                JOptionPane.showMessageDialog(null, "No ngrese numeros negativos");
                return;
            }
            if (cbTipo.getSelectedIndex() == 0) {
                int dia = Integer.parseInt(txtDia.getText().trim());
                if(dia > 31) {
                    JOptionPane.showMessageDialog(null, "Ingrese una dia valido.");
                    return;
                }
                int mes = Integer.parseInt(txtMes.getText().trim());
                if(mes > 12) {
                    JOptionPane.showMessageDialog(null, "Ingrese un mes valido.");
                    return;
                }
                int año = Integer.parseInt(txtAño.getText().trim());
                if(año < 2026) {
                    JOptionPane.showMessageDialog(null, "Ingrese una año valido.");
                    return;
                }
                controlador.agregarPerecedero(nombre, codigo, precio, cantidad, dia, mes, año);
            } else {
                int garantia = Integer.parseInt(txtGarantia.getText().trim());
                controlador.agregarNoPerecedero(nombre, codigo, precio, cantidad, garantia);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese una fecha valida.");
        }
    }

    private void mostrarFormularioActualizar(int id) {
        String entrada = JOptionPane.showInputDialog(null, "Nueva cantidad:");
        if (entrada == null) return;
        try {
            int nuevaCantidad = Integer.parseInt(entrada.trim());
            controlador.actualizarCantidad(id, nuevaCantidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
        }
    }

    private void refrescarTabla() {
        modelo.setRowCount(0);

        for (Producto p : controlador.getProductos()) {
            String tipo;
            String info;

            if (p instanceof ProductoPerecedero) {
                ProductoPerecedero pp = (ProductoPerecedero) p;
                tipo = "Perecedero";
                info = "Vence: " + pp.getDia() + "/" + pp.getMes() + "/" + pp.getAño();
            } else {
                ProductoNoPerecedero np = (ProductoNoPerecedero) p;
                tipo = "No Perecedero";
                info = "Garantía: " + np.getGarantiaMeses() + " mes(es)";
            }

            modelo.addRow(new Object[]{
                    p.getID(),
                    p.getNombre(),
                    String.format("$%.2f", p.getPrecio()),
                    p.getCantidad(),
                    tipo,
                    info
            });
        }

        lblTotal.setText(String.format("Valor total: $%.2f", controlador.calcularValorTotal()));
    }
}
