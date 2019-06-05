public class Handler extends Thread
{
    public String status;

    public Handler(String status){
        this.status = status;
    }

    public void run(){
        switch(status){
            case Estados.host:
                Host h = new Host(Controller.currentServer);
                Controller.nextStatus = h.run();
                break;
            case Estados.server:
                Server s = new Server();
                Controller.nextStatus = s.run();
                break;
            case Estados.apagar:
                System.exit(0);
                break;
        }
    }
}
