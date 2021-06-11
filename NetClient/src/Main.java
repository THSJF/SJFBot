import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import sentence.Sentence;
import tools.JsonHelper;
import bean.DataPackage;

public class Main {
    private ConnectionThread connection;

    public static void main(String... args) throws Exception {     
        Main main = new Main();
        try {
            main.init();
            Scanner input = new Scanner(System.in);
            while (true) {
                main.connection.send(input.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void init() throws Exception {
        try {
            Socket socket = new Socket("123.207.65.93", 9961);
            System.out.println("connect succeed");
            connection = new ConnectionThread(socket);
            connection.start();
            connection.send("ljyys");
            Sentence.SentencesBean ss = new Sentence.SentencesBean();
            ss.add("春宵一刻值千金","《春宵》");
            
            ss.add("朕与将军解战袍，芙蓉帐暖度春宵​");
            ss.add("少小离家老大回，安能辨我是雌雄​");
            ss.add("荡胸生层云，家和万事兴");
            ss.add("垂死病中惊坐起，笑问客从何处来​");
            ss.add("有朋自远方来，尚能饭否");
            ss.add("相与枕藉乎舟中，不如早还家​");
            ss.add("东家有贤女，磨牙吮血，杀人如麻");
            ss.add("杨家有女初长成，黑质而白章，触草木，尽死");
            ss.add("垂死病中惊坐起，谓其妻曰，吾孰与城北徐公美​");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            ss.add("春宵一刻值千金");
            DataPackage dp = DataPackage.encode(DataPackage.typeSencence);
            dp.write(JsonHelper.toJson(ss));
          //  connection.send(dp.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    private class ConnectionThread extends Thread {

        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ConnectionThread(Socket s) {
            try {
                socket = s;
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String s) {
            try {
                if (s.equals("down")) {
                    socket.close();
                    return;
                }
                send(s.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(byte[] array) {
            try {
                out.write(array);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("receive thread start");
            try {
                int available = 0;
                while (!socket.isClosed()) {
                    while ((available = in.available()) == 0) {
                        sleep(100);  
                    }
                    byte[] bs = new byte[available];
                    in.read(bs);
                    System.out.println("bs len:" + (bs.length-"received:".length()));
                    onReceive(bs);
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void onReceive(byte[] array) {
            onReceive(new String(array, StandardCharsets.UTF_8));
        }

        private void onReceive(String content) {
            System.out.println(content);
        }
    }
}
