package TranslatingWordProcessor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {
	private static ArrayList<Thread> arr = new ArrayList<Thread> ();
	private static SimpleDateFormat sdfDate = new SimpleDateFormat ("yyy-MM-dd HH:mm:SSS");
	public static String getLog (String msg) {
		return "[" + sdfDate.format(new Date ()) + "] Server thread: " + msg;
	}
	public static void main (String[] args) {
		ServerSocket ss = null;
		int id = 0;
		try {
			ss = new ServerSocket(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("server is ready");
		while (true) {
			try {
				Socket soc = ss.accept ();
				Thread t = new ServerThread(soc, id ++);
				t.start ();
				arr.add(t); //thread 저장
				Iterator<Thread> iter = arr.iterator ();
				while (iter.hasNext ()) {
					t = iter.next ();
					if (!t.isAlive ()) {
						iter.remove ();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}