import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.io.UnsupportedEncodingException;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;




public class ChatBotMain extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JButton btnSend;
	JPanel pane ;
	JTextArea textArea;
	

	
	

	/**
	 * Create the frame.
	 */
	
     public ChatBotMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(400, 600);
        setBackground(Color.blue);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 5, 5, 5));
        setContentPane(contentPane);
    
        textField_1 = new JTextField();
        textField_1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    action();
                }
            }
        });
        textField_1.setFont(new Font("sans serif", Font.PLAIN, 23));
    
        textField_1.setColumns(10);
        btnSend = new JButton("Send");
        btnSend.setBackground(Color.GREEN);
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action();
            }
        });
    
        JPanel sendAction = new JPanel();
        sendAction.setLayout(new BorderLayout());
        sendAction.setPreferredSize(new Dimension(400, 60));
        sendAction.add(textField_1, BorderLayout.CENTER);
        sendAction.add(btnSend, BorderLayout.EAST);
        contentPane.add(sendAction, BorderLayout.SOUTH);
    
        pane = new JPanel() ;
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        pane.setFont(new Font("sans serif", Font.CENTER_BASELINE, 30));
        JScrollPane scrollPane = new JScrollPane(pane);
    
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    
	
	public void action () 
{
    String query=textField_1.getText();
    JLabel msg = new JLabel("\n    "+ query + "    \n") ;
	msg.setPreferredSize(new Dimension(400, 70)); // set label size
	msg.setForeground(Color.WHITE);
	msg.setBackground(Color.BLUE);
	msg.setOpaque(true);
	msg.setBorder(new EmptyBorder(10, 10, 10, 10));
	pane.add(msg) ;
    pane.revalidate();
    pane.repaint();
	pane.add(Box.createRigidArea(new Dimension(0, 10))); // 10-pixel gap between labels
    
	String apiKey = "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";
    String prompt = query;
    int maxTokens = 15;
	

    try {
        String encodedPrompt = java.net.URLEncoder.encode(prompt, java.nio.charset.StandardCharsets.UTF_8.toString());
        String urlString = "https://api.openai.com/v1/engines/text-davinci-002/completions?prompt=" + encodedPrompt + "&max_tokens=" + maxTokens;
        URI uri = new URI(urlString);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString("{\n" + "  \"prompt\": \"" + prompt + "\",\n" + "  \"max_tokens\": "
                        + maxTokens + "\n" + "}"))
                .build();


        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString());

        response.thenApply(HttpResponse::body)
                .thenAccept(body -> {
                    int startIndex = body.indexOf("text\":\"") + 7;
                    int endIndex = body.indexOf("\",\"index");
                    String output = body.substring(startIndex, endIndex).replace("\\n", "");
                    this.reply(output);
                });

        response.join();

    } catch (UnsupportedEncodingException | URISyntaxException e) {
        // Handle the exception here
        e.printStackTrace();
    }



}


	public void reply(String s){
		JLabel rep = new JLabel("    "+s+"    ") ;
		rep.setPreferredSize(new Dimension(200, 30)); // set label size
        rep.setForeground(Color.WHITE);
        rep.setBackground(Color.DARK_GRAY);
        rep.setOpaque(true);
		rep.setBorder(new EmptyBorder(10, 10, 10, 10));
        
		pane.add(rep) ;
		pane.revalidate();
		pane.repaint();
		pane.add(Box.createRigidArea(new Dimension(0, 10)));
		textField_1.setText("");
	}

	
	
	



}
