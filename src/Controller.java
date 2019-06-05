public class Controller
{
    public static String nextStatus;
    public static String currentServer;

    public Controller()
    {

    }

    public void start(){
        //while(true){
            Handler h = new Handler(nextStatus);
            Thread t = new Thread(h);
            t.start();
            try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        //}
    }
}
