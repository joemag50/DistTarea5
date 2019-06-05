import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
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
import org.json.JSONArray; 
import org.json.JSONObject;

public class Server extends MainWindow implements Runnable
{
	public MyLabel l_m1, l_m2, l_m3, l_m4, l_m5;
	public MyLabel l_m6, l_m7, l_m8, l_m9, l_m10;
	public MyLabel l_m11, l_m12, l_m13, l_m14, l_m15;

	public int pc = 0;
	public ArrayList<MyLabel> labels;
	public ArrayList<Client> clients;
    public Client oClient;

	public boolean run_me = false;
	public int puerto = 5400;
	public MyLabel l_puerto;
	public MyLabel l_quiensoy;

	public void CreateGUI ()
	{
		pc = 0;
		labels = new ArrayList<MyLabel>();
        this.oClient = new Client();
        l_quiensoy = new MyLabel("");

        l_puerto = new MyLabel("IP : " + this.oClient.ip);

		MyLabel l_titulo = new MyLabel("CPU | RAM | SO | Version SO | Ancho de banda | IP");
		l_m1 = new MyLabel("");
		l_m2 = new MyLabel("");
		l_m3 = new MyLabel("");
		l_m4 = new MyLabel("");
		l_m5 = new MyLabel("");

		l_m6 = new MyLabel("");
		l_m7 = new MyLabel("");
		l_m8 = new MyLabel("");
		l_m9 = new MyLabel("");
		l_m10 = new MyLabel("");

		l_m11 = new MyLabel("");
		l_m12 = new MyLabel("");
		l_m13 = new MyLabel("");
		l_m14 = new MyLabel("");
		l_m15 = new MyLabel("");
		JPanel loginBox = new JPanel();

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_quiensoy);
		loginBox.add(l_puerto);
		loginBox.add(l_titulo);

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

		int x = 70,y = 70, b = 700,h = 300;
		loginBox.setBounds(x, y, b, h+20);
		loginBox.setBackground(colores.get(0));
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
		Serving();
		this.dispose();
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
					l_quiensoy.setText("SERVIDOR");
				} else {
					l_quiensoy.setText("HOST");
				}
				
				JSONArray ary = obj.getJSONArray("clients");
		        
		        for (MyLabel ml : this.labels) {
		        	ml.setText("");
		        }
				this.clients = new ArrayList<Client>();
				
				for (int i = 0; i < ary.length(); i++) {
					JSONObject jo = new JSONObject(ary.get(i).toString());
					//String cpu, String ram, String os, String version, String ip) {

					this.clients.add(new Client(jo.getString("cpu"), jo.getString("ram"),
							jo.getString("os"), jo.getString("version"), 
							jo.getString("ip")));
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
			labels.get(i).setText(clients.get(i).labelText() + " 10 Mbps");
		}
	}

	public void orderClients() {
		clients.sort(comparing(Client::getCpuDouble).thenComparing(Client::getRam));
	}

    public void sendIPs(String message){
    	ArrayList<String> ipes = new ArrayList<String>();
    	
        for(int i=0; i < this.clients.size(); i++){
        	boolean b = clients.get(i).ip != this.oClient.ip && !searchArrayList(ipes, clients.get(i).ip);
        	boolean bb = !searchArrayList(ipes, clients.get(i).ip);
        	System.out.println();
        	System.out.println(b);
        	System.out.println();
        	System.out.println(bb);
            if (clients.get(i).ip != this.oClient.ip && !searchArrayList(ipes, clients.get(i).ip) ) {
            	System.out.println();
            	System.out.println(clients.get(i).ip);
            	System.out.println();
            	System.out.println(ipes);
            	System.out.println();
            	System.out.println(i);
            	System.out.println();
            	ipes.add(clients.get(i).ip);
                RequestServer(clients.get(i).ip, message);
            }
        }
    }
    
    public boolean searchArrayList(ArrayList<String> array, String texto)
    {
    	boolean encontrado = false;
    	for (String txt : array)
    	{
    		if (txt.equals(texto)) {
    			encontrado = true;
    			break;
    		}
    	}
    	return encontrado;
    }

	public void RequestServer (String ip, String message)
	{
		// TODO Auto-generated method stub
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket s = null;

		try
		{
			s = new Socket(ip, this.puerto);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());

			oos.writeObject(message);
			//this.btn_enviar.setEnabled(false);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if( ois != null )
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( oos != null )
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( s != null )
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
