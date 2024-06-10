package TranslatingWordProcessor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

class Client{
	String text,inLang,toLang;
	public Client(String text,String inLang,String toLang) {
			this.text = text;
			this.inLang = inLang;
			this.toLang = toLang;
		}
	
	public String getTrans() { //GUI.java에서 넘어와서
		try {
			Socket soc = new Socket("localhost", 5000);
			
			DataInputStream dis = new DataInputStream (soc.getInputStream());
			OutputStream os = soc.getOutputStream ();
			DataOutputStream dos = new DataOutputStream (os);
			dos.writeUTF(text); //여기서 server text를 보낸다.
			dos.writeUTF(inLang);
			dos.writeUTF(toLang);
			String result = dis.readUTF();
			dis.close();
			soc.close();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}