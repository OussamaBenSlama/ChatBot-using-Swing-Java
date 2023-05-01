import java.awt.EventQueue;

public class App {
    public static void main(String[] args) throws Exception {
        
		
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatBotMain frame = new ChatBotMain();
                    frame.setTitle("Swing Chat");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
    }
}
