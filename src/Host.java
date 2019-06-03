import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Host extends MainWindow
{
	JTextField txt_ip;
	JButton btn_enviar;

	Host()
	{
		MyLabel l_ip = new MyLabel("IP Servidor:");
		txt_ip = new JTextField(50);
		txt_ip.requestFocusInWindow();

		MyLabel l_titulo = new MyLabel("¿Enviar datos?");
		btn_enviar = new JButton("Enviar");
		JPanel loginBox = new JPanel();

		btn_enviar.addActionListener(this);

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_ip);
		loginBox.add(txt_ip);
		loginBox.add(l_titulo);
		loginBox.add(btn_enviar);


		int x = 70,y = 70, b = 700,h = 100;
		loginBox.setBounds(x, y, b, h);
		loginBox.setBackground(colores.get(0));
		panelCentro.add(loginBox);
	}

	public void RequestServer () throws IOException
	{
		// TODO Auto-generated method stub
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket s = null;

		try
		{
			s = new Socket(this.txt_ip.getText(),5400);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());

            Client c = new Client();
			oos.writeObject(String.format("%s,%s,%s,%s", c.cpu, c.ram, c.os, c.version ));

			this.btn_enviar.setEnabled(false);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error: No hubo conexion, ¿Esta encendido el servidor?");
		}
		finally
		{
			if( ois != null ) ois.close();
			if( oos != null ) oos.close();
			if( s != null ) s.close();
		}
	}

	//JCGE: Este es el metodo que se encarga de tomar las acciones en los botones
	public void actionPerformed(ActionEvent arg0)
	{
		String boton = arg0.getActionCommand();
		System.out.println(boton);
		if (boton == "Enviar")
		{
			try {
				this.RequestServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
