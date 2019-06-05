public class Controller
{
    public static String nextStatus;
    public static String currentServer;

    public Controller()
    {

    }

    public void start(){
        while(true){
            try {
	            Handler h = new Handler(nextStatus);
	            Thread t = new Thread(h);
	            t.start();
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
