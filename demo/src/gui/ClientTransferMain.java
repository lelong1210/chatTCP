package gui;

public class ClientTransferMain {
    public static void main(String[] args) {
        ClientTransferView view = new ClientTransferView();
        new ClientTransferController(view);
    }
}
