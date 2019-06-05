import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Server extends MainWindow implements Runnable
{
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

	public MyLabel l_m1, l_m2, l_m3, l_m4, l_m5;
	public int pc = 0;
	public ArrayList<MyLabel> labels;
	public ArrayList<Client> clients;
    public Client oClient;

	public boolean run_me = false;
	public int puerto = 5400;

	Server ()
	{

	}
	
	public void serv() {
		Runnable r = new Server();
		new Thread(r).start();
	}
	
	public void CrearGUI() {
		this.oClient = new Client();

		pc = 0;
		labels = new ArrayList<MyLabel>();
		MyLabel l_puerto = new MyLabel("Mi Ip: " + this.oClient.ip + ":" + puerto);

		MyLabel l_titulo = new MyLabel("CPU | RAM | SO | Version SO | Ancho de banda | IP");
		l_m1 = new MyLabel("-");
		l_m2 = new MyLabel("-");
		l_m3 = new MyLabel("-");
		l_m4 = new MyLabel("-");
		l_m5 = new MyLabel("-");
		JPanel loginBox = new JPanel();

		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_puerto);
		loginBox.add(l_titulo);

		labels.add(l_m1);
		labels.add(l_m2);
		labels.add(l_m3);
		labels.add(l_m4);
		labels.add(l_m5);
		
		clients = new ArrayList<Client>();
        clients.add(oClient);
        this.orderClients();
        this.setLabelsText(this.clients);

		loginBox.add(l_m1);
		loginBox.add(l_m2);
		loginBox.add(l_m3);
		loginBox.add(l_m4);
		loginBox.add(l_m5);

		int x = 70,y = 70, b = 700,h = 300;
		loginBox.setBounds(x, y, b, h+20);
		loginBox.setBackground(colores.get(0));
		panelCentro.add(loginBox);
		
		System.out.println("FINGUI 1");
		frame.add(panelSur,   BorderLayout.SOUTH);
		frame.add(panelCentro, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		pack();
		this.setSize(900, 600);
		System.out.println("FINGUI 2");
	}

	public void run() {
		this.CrearGUI();
		this.runs();
	}
	// Server Escuchando
	public String runs () {
        //this.finGUI();
		System.out.println("Si paso");
		String estado = Estados.server;
        try {
            Socket s = null;
            ServerSocket ss = new ServerSocket(this.puerto);
            while (estado.equals(Estados.server))
            {
                try
                {
                    s = ss.accept();
                    ois = new ObjectInputStream( s.getInputStream() );
                    oos = new ObjectOutputStream( s.getOutputStream() );

                    String nom = (String)ois.readObject();
                    String[] respuesta = nom.split(",");

                    this.clients.add(new Client(respuesta[0], respuesta[1], respuesta[2], respuesta[3], respuesta[4]));
                    
                    // Puede ser apagado
                    oos.writeObject(Estados.host);

                    if (this.pc < this.labels.size() - 2) {
                        this.orderClients();
                        this.setLabelsText(this.clients);
                        this.pc++;
                    }
                    else if(!this.clients.get(0).ip.equals(this.oClient.ip)) {
                        estado = Estados.host;
                        break;
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                } finally {
                    if( oos !=null ) oos.close();
                    if( ois !=null ) ois.close();
                    if( s != null ) s.close();
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        sendIPs(this.clients.get(0).ip);
        
        return estado;
	}

	public void setLabelsText(ArrayList<Client> clients) {
		for (int i = 0; i < clients.size(); i++) {
			labels.get(i).setText(clients.get(i).labelText() + " 10 Mbps");
		}
	}

	public void orderClients() {
        Collections.sort(clients);
	}

    public void sendIPs(String message){
    	ArrayList<String> ipes = new ArrayList<String>();

        for(int i=0; i < this.clients.size(); i++){
            if ( clients.get(i).ip != this.oClient.ip && !searchArrayList(ipes, clients.get(i).ip) ) {
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
		Socket s = null;

		try
		{
			s = new Socket(ip, this.puerto);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());

			oos.writeObject(message);
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
