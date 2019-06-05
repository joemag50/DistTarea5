public class Handler extends Thread
{
    public String status;

    public Handler(String status){
        this.status = status;
    }

    public void run(){
        switch(status){
            case "Host":
                Host h = new Host(Controller.currentServer);
                Controller.nextStatus = h.run();
                break;
            case "Server":
                Server s = new Server();
                Controller.nextStatus = s.run();
                break;
            case "Off":
                System.exit(0);
                break;
        }
    }
}
