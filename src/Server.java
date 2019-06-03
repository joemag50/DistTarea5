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
	public MyLabel l_m1, l_m2, l_m3, l_m4, l_m5;
	public int pc = 0;
	public ArrayList<MyLabel> labels;
	public ArrayList<Client> clients;

	public boolean run_me = false;

	Server ()
	{
		labels = new ArrayList<MyLabel>();
		MyLabel l_titulo = new MyLabel("CPU | RAM | SO | Version SO | Ancho de banda");
		l_m1 = new MyLabel("-");
		l_m2 = new MyLabel("-");
		l_m3 = new MyLabel("-");
		l_m4 = new MyLabel("-");
		l_m5 = new MyLabel("-");
		JPanel loginBox = new JPanel();

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_titulo);

		labels.add(l_m1);
		labels.add(l_m2);
		labels.add(l_m3);
		labels.add(l_m4);
		labels.add(l_m5);

        Client oClient = new Client();

		this.clients = new ArrayList<Client>();
        this.clients.add(oClient);
        System.out.println(this.clients);

		loginBox.add(l_m1);
		loginBox.add(l_m2);
		loginBox.add(l_m3);
		loginBox.add(l_m4);
		loginBox.add(l_m5);

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
				// el ServerSocket me da el Socket
				s = ss.accept();
				// informacion en la consola
				//System.out.println("Se conectaron desde la IP: " +s.getInetAddress());
				// enmascaro la entrada y salida de bytes
				ois = new ObjectInputStream( s.getInputStream() );
				oos = new ObjectOutputStream( s.getOutputStream() );
				// leo el nombre que envia el cliente
				String nom = (String)ois.readObject();
				if (this.pc < this.labels.size()) {
					String ip = "" + s.getInetAddress();
					String[] respuesta = nom.split(",");
					this.clients.add(new Client(respuesta[0], respuesta[1], respuesta[2], respuesta[3], respuesta[4]));
					this.orderClients();
					this.setLabelsText(this.clients);
					System.out.println(this.labels);
					//server.labels.get(server.pc).setText(
					//	String.format("%s %s %s %s", respuesta[0], respuesta[1], respuesta[2], respuesta[3])
					//);
					this.pc++;
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
		clients.sort(comparing(Client::getCpuDouble).thenComparing(Client::getRam));
	}
}
