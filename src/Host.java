import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import static java.util.Comparator.comparing;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Host extends MainWindow
{
	
	public boolean run_me = false;
	
	Host ()
	{
		MyLabel l_titulo = new MyLabel("CPU | RAM | SO | Version SO | Ancho de banda");
		JPanel loginBox = new JPanel();
		
		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_titulo);
		
		int x = 70,y = 70, b = 700,h = 300;
		loginBox.setBounds(x, y, b, h+20);
		loginBox.setBackground(colores.get(0));
		panelCentro.add(loginBox);
	}
	
	public void run () throws IOException {
		// TODO Auto-generated method stub
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
		Socket s = null;
		ServerSocket ss = new ServerSocket(5400);
		while (this.run_me)
		{
			try
			{

			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			} finally {
				if( oos !=null ) oos.close();
				if( ois !=null ) ois.close();
				if( s != null ) s.close();
				System.out.println("Conexion cerrada!");
			}
		}
	}
}
