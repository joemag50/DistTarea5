import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainWindow extends JFrame implements ActionListener, MenuListener
{
  private static final long serialVersionUID = -1171527061718987226L;
  static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  static final Double WIDTH = new Double(screenSize.getWidth());
  static final Double HEIGHT = new Double(screenSize.getHeight());
  public static int x_int = 50;
  public static int y_int = 50;

  public Container frame;
  public JPanel panelNorte;
  public JPanel panelEste;
  public JPanel panelOeste;
  public JPanel panelSur;
  public static JDesktopPane panelCentro;
  public MyLabel l_titulo;
  public MyLabel elTiempo;
  public static ArrayList<Color> colores;
  
  MainWindow()
  {
    this.frame = getContentPane();
    this.frame.setLayout(new BorderLayout());
    setTitle("Proyecto Sistemas Distribuidos y Paralelos");
    setResizable(true);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(3);
    this.panelSur = new JPanel();
    panelCentro = new JDesktopPane();
    panelCentro.setLayout(null);
    colores = new ArrayList();
    colores.add(new Color('¨', '¨', '¨'));
    colores.add(new Color('Í', 82, 123));
    colores.add(new Color('¶', 'Ê', 121));
    colores.add(new Color(72, 72, 72));
    colores.add(new Color(54, 54, 54));
    panelCentro.setBackground((Color)colores.get(3));
  }

  public void finGUI() {
    this.frame.add(panelCentro, "Center");
    setLocationRelativeTo(null);
    pack();
    setSize(725, 400);
  }

  public void actionPerformed(ActionEvent arg0) { String boton = arg0.getActionCommand(); }
  public void menuSelected(MenuEvent e) {}
  public void menuCanceled(MenuEvent arg0) {}
  public void menuDeselected(MenuEvent arg0) {}
}
