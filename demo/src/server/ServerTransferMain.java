package server;

public class ServerTransferMain {
    public static void main(String[] args) {
    	ServerTransferView view = new ServerTransferView();
        new ServerTransferController(view);
    }
}
