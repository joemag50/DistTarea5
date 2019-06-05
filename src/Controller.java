public class Controller
{
    public static String nextStatus;
    public Controller()
    {

    }

    public void start(){
        while(true){
            Handler h = new Handler(nextStatus);
            h.start();
            h.joins();
        }
    }
}
