import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Comparator.comparing;

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

public class Principal extends MainWindow
{
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	Socket s = null;
	ServerSocket ss = null;

	Principal () {
		MyLabel l_titulo = new MyLabel("¿Mi computadora es?");
		JPanel loginBox = new JPanel();

		JButton btn_host = new JButton("Host");
		JButton btn_server = new JButton("Server");

		btn_host.addActionListener(this);
		btn_server.addActionListener(this);

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_titulo);

		int x = 70,y = 70, b = 700,h = 300;
		loginBox.setBounds(x, y, b, h+20);
		loginBox.setBackground(colores.get(0));
		panelCentro.add(loginBox);
	}

	public static void main(String[] args) {
		//Server s = new Server();
		Server.Serv();
	}

	public void switcher() {
		while (true) {
			try {
				Client c = new Client();
				String url = String.format("https://api-distribuidos.herokuapp.com/update/?ip=%s&cpu=%s&ram=%s", c.ip, c.cpu.replace("%",  ""), c.ram.replace(" ", ""));
				System.out.println(url);
				String shiet = Connection.Connect(url);
				System.out.println(shiet);
				
				JSONObject obj = new JSONObject(shiet);
				
				if (obj.getBoolean("server")) {
					this.dispose();
					Server s = new Server();
					s.Serv();
				} else {
					this.dispose();
					Host h = new Host();
					h.Hos();
				}
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void actionPerformed(ActionEvent arg0)
	{
		String boton = arg0.getActionCommand();
		if (boton == "Host")
		{
			this.dispose();

			Host h = new Host();
			h.Hos();
			return;
		}

		if (boton == "Server")
		{
			this.dispose();
			
			Server s = new Server();
			s.Serv();
			return;
		}
	}
}
