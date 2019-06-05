
public class GuiHelper implements Runnable{
	
	public MainWindow mw;
	
	public GuiHelper(MainWindow mw) {
		this.mw = mw;
	}
	
	public void run() {
		mw.finGUI();
	}
}
