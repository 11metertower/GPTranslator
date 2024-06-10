package TranslatingWordProcessor;
import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket soc;
	private int id;
	private Translator trans;
	
	public ServerThread(Socket soc, int id) {
		this.soc = soc;
		this.id = id;
	}
	public void run () {
		try {
			DataInputStream dis = new DataInputStream (soc.getInputStream());
			OutputStream os = soc.getOutputStream ();
			DataOutputStream dos = new DataOutputStream (os);
			String text = dis.readUTF();
			String inLang = dis.readUTF();
			String outLang = dis.readUTF();
			System.out.println(Server.getLog ("new connection arrived (" + id + ")"));
			trans = new Translator(text, inLang, outLang);
			dos.writeUTF(trans.translate(inLang, outLang, text));//여기서 text를 번역한 것을 client로 다시 보낸다.
			dos.close();
			this.soc.close ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}