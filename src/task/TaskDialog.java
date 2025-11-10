import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Diálogo Swing para agregar una nueva tarea
 */
public class TaskDialog extends JDialog {
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<Task.Prioridad> cbPrioridad;
    private JTextField txtFecha;
    private boolean tareaCreada;
    private Task tareaResultado;
    
    /**
     * Constructor del diálogo
     */
    public TaskDialog(Frame parent) {
        super(parent, "Agregar Nueva Tarea", true);
        tareaCreada = false;
        inicializarComponentes();
        configurarVentana();
    }
    
    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal con el formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblTitulo, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTitulo = new JTextField(20);
        panelFormulario.add(txtTitulo, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblDescripcion, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panelFormulario.add(scrollDescripcion, gbc);
        
        // Prioridad
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblPrioridad = new JLabel("Prioridad:");
        lblPrioridad.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblPrioridad, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbPrioridad = new JComboBox<>(Task.Prioridad.values());
        cbPrioridad.setRenderer(new PrioridadRenderer());
        panelFormulario.add(cbPrioridad, gbc);
        
        // Fecha
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel lblFecha = new JLabel("Fecha (dd/MM/yyyy):");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblFecha, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panelFormulario.add(txtFecha, gbc);
        
        add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        
        JButton btnAceptar = new JButton("Agregar Tarea");
        btnAceptar.setBackground(new Color(76, 175, 80));
        btnAceptar.setForeground(Color.BLACK);
        btnAceptar.setFocusPainted(false);
        btnAceptar.addActionListener(e -> aceptarTarea());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> cancelar());
        
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    /**
     * Maneja el evento de agregar tarea
     */
    private void aceptarTarea() {
        // Validar título
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El título es obligatorio", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            txtTitulo.requestFocus();
            return;
        }
        
        // Validar descripción
        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "La descripción es obligatoria", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            txtDescripcion.requestFocus();
            return;
        }
        
        try {
            // Parsear fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), formatter);
            
            // Crear tarea
            tareaResultado = new Task(
                txtTitulo.getText().trim(),
                txtDescripcion.getText().trim(),
                (Task.Prioridad) cbPrioridad.getSelectedItem(),
                fecha
            );
            
            tareaCreada = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Formato de fecha inválido. Use dd/MM/yyyy", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            txtFecha.requestFocus();
        }
    }
    
    /**
     * Maneja el evento de cancelar
     */
    private void cancelar() {
        tareaCreada = false;
        dispose();
    }
    
    /**
     * Devuelve si se creó una tarea
     */
    public boolean isTareaCreada() {
        return tareaCreada;
    }
    
    /**
     * Devuelve la tarea creada
     */
    public Task getTarea() {
        return tareaResultado;
    }
    
    /**
     * Renderer personalizado para mostrar las prioridades con colores
     */
    private static class PrioridadRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Task.Prioridad) {
                Task.Prioridad prioridad = (Task.Prioridad) value;
                label.setText(prioridad.getNombre());
                
                if (!isSelected) {
                    switch (prioridad) {
                        case ALTA:
                            label.setBackground(new Color(255, 235, 238));
                            label.setForeground(new Color(198, 40, 40));
                            break;
                        case MEDIA:
                            label.setBackground(new Color(255, 248, 225));
                            label.setForeground(new Color(237, 137, 54));
                            break;
                        case BAJA:
                            label.setBackground(new Color(232, 245, 233));
                            label.setForeground(new Color(46, 125, 50));
                            break;
                    }
                }
            }
            
            return label;
        }
    }
}
