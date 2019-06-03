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

public class Server extends MainWindow
{
	public MyLabel l_m1, l_m2, l_m3, l_m4;
	public int pc = 0;
	public ArrayList<MyLabel> labels;
	public ArrayList<Client> clients;
	
	Server ()
	{
		//JCGE: Propiedades Generales
		this.setExtendedState(MAXIMIZED_BOTH);
		
		//JCGE: Propiedades Particulares
		labels = new ArrayList<MyLabel>();
		MyLabel l_titulo = new MyLabel("CPU | RAM | SO | Version SO | Ancho de banda");
		l_m1 = new MyLabel("-");
		l_m2 = new MyLabel("-");
		l_m3 = new MyLabel("-");
		l_m4 = new MyLabel("-");
		JPanel loginBox = new JPanel();

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_titulo);

		labels.add(l_m1);
		labels.add(l_m2);
		labels.add(l_m3);
		labels.add(l_m4);
		
		this.clients = new ArrayList<Client>();
		
		loginBox.add(l_m1);
		loginBox.add(l_m2);
		loginBox.add(l_m3);
		loginBox.add(l_m4);

		
		//JCGE: Vamos a prepararnos para poner una imagen aca loca
		int x = 320,y = 250,b = 700,h = 200;
		loginBox.setBounds((WIDTH.intValue()/2)-340, y, b, h+20);
		loginBox.setBackground(colores.get(0));
		panelCentro.add(loginBox);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
		Socket s = null;
		ServerSocket ss = new ServerSocket(5400);
		Server server = new Server();
		server.finGUI();
		server.setSize(MainWindow.WIDTH.intValue(), MainWindow.HEIGHT.intValue());

		while (true)
		{
			try
			{
				// el ServerSocket me da el Socket
				s = ss.accept();
				// informacion en la consola
				//System.out.println("Se conectaron desde la IP: " +s.getInetAddress());
				// enmascaro la entrada y salida de bytes
				ois = new ObjectInputStream( s.getInputStream() );
				oos = new ObjectOutputStream( s.getOutputStream() );
				// leo el nombre que envia el cliente
				String nom = (String)ois.readObject();
				if (server.pc < server.labels.size()) {
					String ip = "" + s.getInetAddress();
					String[] respuesta = nom.split(",");
					server.clients.add(new Client(respuesta[0], respuesta[1], respuesta[2], respuesta[3]));
					server.orderClients();
					server.setLabelsText(server.clients);
					System.out.println(server.labels);
					//server.labels.get(server.pc).setText(
					//	String.format("%s %s %s %s", respuesta[0], respuesta[1], respuesta[2], respuesta[3])
					//);
					server.pc++;
				}
				//System.out.println(nom);
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
	
	public void setLabelsText(ArrayList<Client> clients) {
		for (int i = 0; i < clients.size(); i++) {
			labels.get(i).setText(clients.get(i).labelText() + " 10 Mbps");
		}
	}
	
	public void orderClients() {
		clients.sort(comparing(Client::getCpu).thenComparing(Client::getRam));
	}
}
