import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

//Heredamos de JFrame para sacar de la libreria swing
public class MainWindow extends JFrame implements ActionListener, MenuListener
{
	private static final long serialVersionUID = -1171527061718987226L;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final Double WIDTH = new Double(screenSize.getWidth());
	static final Double HEIGHT = new Double(screenSize.getHeight());
	public static int x_int = 50;
	public static int y_int = 50;
	public Container frame;
	public JPanel panelNorte, panelEste, panelOeste, panelSur;
	public static JDesktopPane panelCentro;
	public MyLabel l_titulo, elTiempo;
	
	public static ArrayList<Color> colores;
	MainWindow ()
	{
		frame = getContentPane();
		frame.setLayout(new BorderLayout());
		this.setTitle("Proyecto Sistemas Distribuidos y Paralelos");
		this.setResizable(true);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panelSur   = new JPanel();
		panelCentro = new JDesktopPane();
		panelCentro.setLayout(null);

		colores = new ArrayList<Color>();
		colores.add(new Color(168, 168, 168));
		colores.add(new Color(205, 82, 123));
		colores.add(new Color(182, 202, 121));
		colores.add(new Color(72, 72, 72));
		colores.add(new Color(54, 54, 54));

		panelCentro.setBackground(colores.get(3));
	}
	public void finGUI()
	{
		frame.add(panelCentro, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		pack();
		this.setSize(725, 400);
	}

	public void setWindowSize(MainWindow e, int x, int y)
	{
		e.setSize(MainWindow.WIDTH.intValue()-x, MainWindow.HEIGHT.intValue()-y);
	}

	protected KeyAdapter solonum = new KeyAdapter()
	{
		public void keyTyped(KeyEvent e)
		{
			char c = e.getKeyChar();
			if (!((c >= '0') && (c <= '9') || (c == '.') || (c == KeyEvent.VK_TAB) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) ))        
			{
				JOptionPane.showMessageDialog(null, "Favor de ingresar solo números en este campo.");
				e.consume();
			}
		}
	};
    protected Boolean isInteger(String s)
    {
        try
        {
        	Integer.parseInt(s);
        }
        catch(NumberFormatException e)
        {
        	return false;
        }
        catch(NullPointerException e)
        {
        	return false;
        }
        return true;  //Regresamos verdadero en caso de que sea numero realmente
    }
    //Esta funcion es para ver si es double o no
    protected Boolean isDouble(String s)
    {
        try
        {
        	Double.parseDouble(s);
        }
        catch(NumberFormatException e)
        {
        	return false;
        }
        catch(NullPointerException e)
        {
        	return false;
        }
        return true;  //Regresamos verdadero en caso de que sea numero realmente
    }
	public void actionPerformed(ActionEvent arg0)
	{
		String boton = arg0.getActionCommand();
		//System.out.println(boton);
	}
	@Override
	public void menuSelected(MenuEvent e) {/*JCGE: Aun sin nada*/}
	@Override
	public void menuCanceled(MenuEvent arg0) {/*JCGE: Aun sin nada*/}
	@Override
	public void menuDeselected(MenuEvent arg0) {/*JCGE: Aun sin nada*/}
}
