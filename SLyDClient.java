import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;

public abstract class SLyDClient {
    private String SLyDLocator = "SLyD://No:67";
    private String TargetDomain;
    private int port;

    private Socket sock = null;
    private void getDomain(String locator) throws IllegalSLyDFormatExpertion {
        if (locator.substring(0, 7) != "SLyD://") {
            throw new IllegalSLyDFormatExpertion("no SLyD prefix!");
        }
        String[] mda = null;
        String nopref = locator.substring(7);
        if (nopref.contains(":")) {
            mda = nopref.split(":");
            assert mda != null;
        } else {
            TargetDomain = mda[1];
        }
    }
    void getPort(String locator) throws IllegalSLyDFormatExpertion {
        if (locator.substring(0, 7) != "SLyD://") {
            throw new IllegalSLyDFormatExpertion("no SLyD prefix!");
        }
        String[] mda = null;
        String nopref = locator.substring(7);
        if (nopref.contains(":")) {
            mda = nopref.split(":");

        }
        assert mda != null;
        port = Integer.parseInt(mda[2]);
    }
    public void setSLyDLocator(String locator) throws IllegalSLyDFormatExpertion {
        if (locator.substring(0, 7) != "SLyD://") {
            throw new IllegalSLyDFormatExpertion("no SLyD prefix!");
        }
        if (locator.substring( 7) == null) {
            throw new IllegalSLyDFormatExpertion("no address!");
        }
        if (locator.substring( 7) == null && locator.contains(":")) {
            throw new IllegalSLyDFormatExpertion("no port!");
        }
    }
    public final void Connect(){
                String githubUrl = "https://raw.githubusercontent.com/JvVireo/SLyD-protocol/main/serverlist.txt"; // Замените на реальный URL
                try {
                    getPort(SLyDLocator);
                    getDomain(SLyDLocator);
                    URL url = new URL(githubUrl);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                    sock = new Socket(line,port);
                        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        out.println("domain");
                        if(!Objects.equals(in.readLine(), TargetDomain)){
                            continue;
                        }
                        ConnectionAction();
                        break;
                    }

                    reader.close();

                } catch (IOException e) {
                    System.err.println("that's an error during reading file: " + e.getMessage());
                    e.printStackTrace(); // Вывод более подробной информации об ошибке
                } catch (IllegalSLyDFormatExpertion e){
                    System.err.println("that's an SlyD format error: " + e.getMessage());
                }


    }
    public abstract void ConnectionAction();
}