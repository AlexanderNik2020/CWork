import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader extends Thread {

    private String text;

    Reader(){
        text = "";
    }

    public void read(String path) throws FileNotFoundException {

        File file = new File(path);
        Scanner in = new Scanner(file);
        while(in.hasNextLine()) {
            text += in.nextLine() + "\n";
        }
    }

    public String getText() {
        return text;
    }

}
