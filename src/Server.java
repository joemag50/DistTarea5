import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import static java.util.Comparator.comparing;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.json.JSONArray; 
import org.json.JSONObject;

public class Server extends MainWindow implements Runnable
{
	public JButton l_m1, l_m2, l_m3, l_m4, l_m5;
	public JButton l_m6, l_m7, l_m8, l_m9, l_m10;
	public JButton l_m11, l_m12, l_m13, l_m14, l_m15;

	public int pc = 0;
	public ArrayList<JButton> labels;
	public ArrayList<Client> clients;
	public Client oClient;

	public boolean run_me = false;
	public MyLabel l_puerto;
	public MyLabel l_quiensoy;

	public void CreateGUI ()
	{
		pc = 0;
		labels = new ArrayList<JButton>();
		this.oClient = new Client();
		l_quiensoy = new MyLabel("");

		l_puerto = new MyLabel("IP : " + this.oClient.ip);

		l_m1 = new JButton("");
		l_m2 = new JButton("");
		l_m3 = new JButton("");
		l_m4 = new JButton("");
		l_m5 = new JButton("");

		l_m6 = new JButton("");
		l_m7 = new JButton("");
		l_m8 = new JButton("");
		l_m9 = new JButton("");
		l_m10 = new JButton("");

		l_m11 = new JButton("");
		l_m12 = new JButton("");
		l_m13 = new JButton("");
		l_m14 = new JButton("");
		l_m15 = new JButton("");
		

		JPanel loginBox = new JPanel();

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_quiensoy);
		loginBox.add(l_puerto);
		labels.add(l_m1);
		labels.add(l_m2);
		labels.add(l_m3);
		labels.add(l_m4);
		labels.add(l_m5);
		
		labels.add(l_m6);
		labels.add(l_m7);
		labels.add(l_m8);
		labels.add(l_m9);
		labels.add(l_m10);
		
		labels.add(l_m11);
		labels.add(l_m12);
		labels.add(l_m13);
		labels.add(l_m14);
		labels.add(l_m15);
		
		for (JButton jb : this.labels) {
			jb.setVisible(false);
			jb.setBackground(this.colores.get(3));
			jb.setForeground(this.colores.get(2));
			jb.addActionListener(this);
		}

		clients = new ArrayList<Client>();
		clients.add(oClient);

		loginBox.add(l_m1);
		loginBox.add(l_m2);
		loginBox.add(l_m3);
		loginBox.add(l_m4);
		loginBox.add(l_m5);
		
		loginBox.add(l_m6);
		loginBox.add(l_m7);
		loginBox.add(l_m8);
		loginBox.add(l_m9);
		loginBox.add(l_m10);
		
		loginBox.add(l_m11);
		loginBox.add(l_m12);
		loginBox.add(l_m13);
		loginBox.add(l_m14);
		loginBox.add(l_m15);

		int x = 10,y = 10, b = 700,h = 300;
		loginBox.setBounds(x, y, b, h+20);
		loginBox.setBackground(colores.get(1));
		panelCentro.add(loginBox);
		
		this.finGUI();
	}
	
	public static void Serv ()
	{
		Runnable r = new Server();
		new Thread(r).start();
	}
	
	public void run () {
		CreateGUI();
		Activate();
		Serving();
		this.dispose();
	}
	
	public void Activate() {
		Client c = new Client();
		String url = String.format("https://api-distribuidos.herokuapp.com/activate/?ip=%s", c.ip);
		System.out.println(url);
		try {
			String shiet = Connection.Connect(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Serving() {
		while (true) {
			try {
				Client c = new Client();
				String url = String.format("https://api-distribuidos.herokuapp.com/update/?ip=%s&cpu=%s&ram=%s&os=%s&version=%s", c.ip, c.cpu.replace("%",  ""), c.ram.replace(" ", ""), c.os.replace(" ", "_"), c.version.replace(" ", "_"));
				System.out.println(url);
				String shiet = Connection.Connect(url);
				System.out.println(shiet);
				
				JSONObject obj = new JSONObject(shiet);
				if (obj.getBoolean("server")) {
					l_quiensoy.setText("SERVER");
				} else {
					l_quiensoy.setText("HOST");
				}
				
				JSONArray ary = obj.getJSONArray("clients");
				
				for (JButton ml : this.labels) {
					ml.setText("");
				}
				
				this.clients = new ArrayList<Client>();
				
				for (int i = 0; i < ary.length(); i++) {
					JSONObject jo = new JSONObject(ary.get(i).toString());
					
					this.clients.add(new Client(jo.getString("cpu"), jo.getString("ram"),
							jo.getString("os"), jo.getString("version"), 
							jo.getString("ip")));
					
					if (!jo.getBoolean("active")) {
						System.exit(0);
					}
				}
				//Rellenar datos
				this.setLabelsText(this.clients);
				
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setLabelsText(ArrayList<Client> clients) {
		for (int i = 0; i < clients.size(); i++) {
			labels.get(i).setText("");
			if (clients.get(i).labelText().length() > 0) {
				labels.get(i).setVisible(true);
			} else {
				labels.get(i).setVisible(false);
			}
			labels.get(i).setText(clients.get(i).labelText());
		}
	}

	public void actionPerformed(ActionEvent arg0)
	{
		String boton = arg0.getActionCommand();
		// deactivate
		String[] datos = boton.split(" -");
		System.out.println(datos[0]);
		String url = String.format("https://api-distribuidos.herokuapp.com/deactivate/?ip=%s", datos[0]);
		try {
			String shiet = Connection.Connect(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
