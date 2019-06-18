import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.json.JSONArray;
import org.json.JSONObject;

public class Server extends MainWindow implements Runnable
{
  public JButton l_m1;
  public JButton l_m2;
  public JButton l_m3;
  public JButton l_m4;
  public JButton l_m5;
  public JButton l_m6;
  public JButton l_m7;
  public JButton l_m8;
  public JButton l_m9;
  public JButton l_m10;
  public JButton l_m11;
  public JButton l_m12;
  public JButton l_m13;
  public JButton l_m14;
  public JButton l_m15;
  public int pc = 0;
  
  public ArrayList<JButton> labels;
  
  public ArrayList<Client> clients;
  public Client oClient;
  public boolean run_me = false;
  public MyLabel l_puerto;
  public MyLabel l_quiensoy;
  
  public void CreateGUI()
  {
    this.pc = 0;
    this.labels = new ArrayList();
    this.oClient = new Client();
    MyLabel l_etiquetas = new MyLabel("IP-OS-VER-CPU-RAM-AN.BANCH-RANK_STAT-RANK");
    this.l_quiensoy = new MyLabel("");
    
    this.l_puerto = new MyLabel("IP : " + this.oClient.ip);
    
    this.l_m1 = new JButton("");
    this.l_m2 = new JButton("");
    this.l_m3 = new JButton("");
    this.l_m4 = new JButton("");
    this.l_m5 = new JButton("");
    
    this.l_m6 = new JButton("");
    this.l_m7 = new JButton("");
    this.l_m8 = new JButton("");
    this.l_m9 = new JButton("");
    this.l_m10 = new JButton("");
    
    this.l_m11 = new JButton("");
    this.l_m12 = new JButton("");
    this.l_m13 = new JButton("");
    this.l_m14 = new JButton("");
    this.l_m15 = new JButton("");

    
    JPanel loginBox = new JPanel();
    
    loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
    loginBox.add(this.l_quiensoy);
    loginBox.add(this.l_puerto);
    loginBox.add(l_etiquetas);
    
    this.labels.add(this.l_m1);
    this.labels.add(this.l_m2);
    this.labels.add(this.l_m3);
    this.labels.add(this.l_m4);
    this.labels.add(this.l_m5);
    
    this.labels.add(this.l_m6);
    this.labels.add(this.l_m7);
    this.labels.add(this.l_m8);
    this.labels.add(this.l_m9);
    this.labels.add(this.l_m10);
    
    this.labels.add(this.l_m11);
    this.labels.add(this.l_m12);
    this.labels.add(this.l_m13);
    this.labels.add(this.l_m14);
    this.labels.add(this.l_m15);
    
    for (JButton jb : this.labels) {
      jb.setVisible(false);
      jb.setBackground((Color)colores.get(3));
      jb.setForeground((Color)colores.get(2));
      jb.addActionListener(this);
    } 
    
    this.clients = new ArrayList();
    this.clients.add(this.oClient);
    
    loginBox.add(this.l_m1);
    loginBox.add(this.l_m2);
    loginBox.add(this.l_m3);
    loginBox.add(this.l_m4);
    loginBox.add(this.l_m5);
    
    loginBox.add(this.l_m6);
    loginBox.add(this.l_m7);
    loginBox.add(this.l_m8);
    loginBox.add(this.l_m9);
    loginBox.add(this.l_m10);
    
    loginBox.add(this.l_m11);
    loginBox.add(this.l_m12);
    loginBox.add(this.l_m13);
    loginBox.add(this.l_m14);
    loginBox.add(this.l_m15);
    
    int x = 10, y = 10, b = 700, h = 300;
    loginBox.setBounds(x, y, b, h + 20);
    loginBox.setBackground((Color)colores.get(1));
    panelCentro.add(loginBox);
    
    finGUI();
  }

  public static void Serv()
  {
	  Runnable r = new Server();
    (new Thread(r)).start();
  }
  
  public void run()
  {
    CreateGUI();
    Activate();
    Serving();
    dispose();
  }
  
  public void Activate()
  {
    Client c = new Client();
    String url = String.format("https://api-distribuidos.herokuapp.com/activate/?ip=%s", new Object[] { c.ip });
    System.out.println(url);
    try {
      String str = Connection.Connect(url);
    } catch (Exception e) {
      
      e.printStackTrace();
    } 
  }
  
  public void Serving()
  {
    while (true) {
      try {
        Client c = new Client();
        String url = String.format("https://api-distribuidos.herokuapp.com/update/?ip=%s&cpu=%s&ram=%s&os=%s&version=%s&total_ram=%s&cores=%s", new Object[] {
              c.ip, c.cpu.replace("%", ""), 
              c.ram.replace(" ", ""), c.os.replace(" ", "_"), 
              c.version.replace(" ", "_"), 
              c.totalRam, c.cores });
        System.out.println(url);
        String shiet = Connection.Connect(url);
        System.out.println(shiet);
        
        JSONObject obj = new JSONObject(shiet);
        if (obj.getBoolean("server")) {
          this.l_quiensoy.setText("SERVER");
        } else {
          this.l_quiensoy.setText("HOST");
        } 
        
        JSONArray ary = obj.getJSONArray("clients");
        
        for (JButton ml : this.labels) {
          ml.setText("");
        }
        
        this.clients = new ArrayList();
        
        for (int i = 0; i < ary.length(); i++) {
          JSONObject jo = new JSONObject(ary.get(i).toString());
          
          this.clients.add(new Client(jo.getString("cpu"), jo.getString("ram"), 
                jo.getString("os"), jo.getString("version"), 
                jo.getString("ip"), jo.getInt("ranking") + "", jo.getInt("static_ranking") + ""));
          
          if (!jo.getBoolean("active"))
          {
            System.exit(0);
          }
        } 
        
        setLabelsText(this.clients);
        
        Thread.sleep(1000L);
      } catch (Exception e) {
        
        e.printStackTrace();
      } 
    } 
  }
  
  public void setLabelsText(ArrayList<Client> clients) {
    for (int i = 0; i < clients.size(); i++) {
      ((JButton)this.labels.get(i)).setText("");
      if (((Client)clients.get(i)).labelText().length() > 0) {
        ((JButton)this.labels.get(i)).setVisible(true);
      } else {
        ((JButton)this.labels.get(i)).setVisible(false);
      } 
      ((JButton)this.labels.get(i)).setText(((Client)clients.get(i)).labelText());
    } 
  }

  
  public void actionPerformed(ActionEvent arg0) {
    String boton = arg0.getActionCommand();
    
    String[] datos = boton.split(" -");
    System.out.println(datos[0]);
    String url = String.format("https://api-distribuidos.herokuapp.com/deactivate/?ip=%s", new Object[] { datos[0] });
    try {
      String str = Connection.Connect(url);
    } catch (Exception e) {
      
      e.printStackTrace();
    } 
  }
}
