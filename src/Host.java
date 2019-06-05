import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Host extends MainWindow implements Runnable
{
	JButton btn_enviar;
	MyLabel l_ip;
	MyLabel l_puerto;
	MyLabel l_datos;
	
	public boolean run_me = false;
	public int puerto = 5400;
	
	public void CreateGUI() {
		MyLabel l_titulo = new MyLabel("Proyecto");
		
		l_ip = new MyLabel("IP Servidor: ");
		l_puerto = new MyLabel("Puerto: " + puerto);
		l_datos = new MyLabel("Datos: ");

		JPanel loginBox = new JPanel();

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_titulo);
		loginBox.add(l_ip);
		loginBox.add(l_puerto);
		loginBox.add(l_datos);

		int x = 70,y = 70, b = 700,h = 130;
		loginBox.setBounds(x, y, b, h);
		loginBox.setBackground(colores.get(0));
		panelCentro.add(loginBox);
		this.finGUI();
	}

	public void Hos ()
	{
		Runnable r = new Host();
		new Thread(r).start();
	}
	
	public void run () {
		CreateGUI();
		Hosting();
		this.dispose();
	}
	
	public void Hosting() {
		while (true) {
			try {
				Client c = new Client();
				String url = String.format("https://api-distribuidos.herokuapp.com/update/?ip=%s&cpu=%s&ram=%s", c.ip, c.cpu.replace("%",  ""), c.ram.replace(" ", ""));
				System.out.println(url);
				String shiet = Connection.Connect(url);
				System.out.println(shiet);
				
				JSONObject obj = new JSONObject(shiet);
				if (obj.getBoolean("server")) {
					break;
				}
				l_ip.setText(shiet);
				l_datos.setText(String.format("%s %s %s %s %s", c.os, c.version, c.ip, c.cpu, c.ram));
				
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//JCGE: Este es el metodo que se encarga de tomar las acciones en los botones
	public void actionPerformed(ActionEvent arg0)
	{
		String boton = arg0.getActionCommand();
	}
}
