public class Handler extends Thread
{
    public String status;

    public Handler(String status){
        this.status = status;
    }

    public void run(){
        switch(status){
            case Estados.host:
                Host h = new Host();
                h.serv();
                break;
            case Estados.server:
                Server s = new Server();
                s.serv();
                System.out.println("adios mundo!");
                break;
            case Estados.apagar:
                System.exit(0);
                break;
        }
    }
}
