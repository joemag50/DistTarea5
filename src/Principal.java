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

public class Principal extends MainWindow
{
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	Socket s = null;
	ServerSocket ss = null;
	
	Principal () {

	}
	
	public static void main(String[] args) {
		
		Server server = new Server();
		server.finGUI();
		
		while (true)
		{
			
		}
	}
}
