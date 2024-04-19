import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class QueueGUI {
    private JTextArea colaTextArea;
    private JPanel panelBotones;
    private JButton btnAgregar;
    private JButton btnDesencolar;
    private JButton btnGuardar;
    private JButton btnRecuperar;

    private Queue<String> cola = new LinkedList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("QueueGUI");
        frame.setContentPane(new QueueGUI().panelBotones);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public QueueGUI() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String elemento = JOptionPane.showInputDialog("Ingrese un elemento:");
                if (elemento != null && !elemento.isEmpty()) {
                    cola.offer(elemento);
                    actualizarTextArea();
                }
            }
        });
        btnDesencolar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cola.isEmpty()) {
                    cola.poll();
                    actualizarTextArea();
                } else {
                    JOptionPane.showMessageDialog(null, "La cola está vacía");
                }
            }
        });
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarColaEnArchivo();
            }
        });
        btnRecuperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarColaDesdeArchivo();
            }
        });
    }

    private void actualizarTextArea() {
        colaTextArea.setText("Cola:\n");
        for (String elemento : cola) {
            colaTextArea.append(elemento + "\n");
        }
    }

    private void guardarColaEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cola.txt"))) {
            for (String elemento : cola) {
                writer.write(elemento);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "La cola ha sido guardada en el archivo 'cola.txt'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recuperarColaDesdeArchivo() {
        cola.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("cola.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                cola.offer(linea);
            }
            JOptionPane.showMessageDialog(null, "La cola ha sido recuperada desde el archivo 'cola.txt'");
            actualizarTextArea();
        } catch (IOException e) {
            // Si el archivo no existe o hay un error de lectura, simplemente no hacemos nada
            // JOptionPane.showMessageDialog(null, "Error al leer el archivo 'cola.txt'");
        }
    }
}
