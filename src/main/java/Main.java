import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Main extends Thread{

    public static void main(String[] args) {

        Object[][] data = {};
        Object[] header = {"Time","Weather","Temperature","Wind"};
        try {
            data = update();
        } catch (Exception e){

        }



        JFrame f=new JFrame("Что-то типа про погоду и пробки там, но этого пока нет");//creating instance of JFrame
        Box contents = new Box(BoxLayout.Y_AXIS);
        f.getContentPane().setBackground(new Color(70, 130, 180));
        JTable table = new JTable(data, header);
        table.setVisible(true);
        contents.add(new JScrollPane(table));//adding button in JFrame
        f.setContentPane(contents);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(400,500);//400 width and 500 height
        //f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible


    }


    public static String[][] update() throws IOException, InterruptedException{

        String[][] res = new String[8][4];

        Document doc1 = Jsoup.connect("https://www.gismeteo.ru/weather-shelekhov-4788/")
                .userAgent("Chrome/4.0.249.1 Safari/532.5")
                .referrer("http://www.google.com")
                .get();

        String q1 = "body > section > div.content_wrap > div > div.main > div > div.__frame_sm > div.forecast_frame.hw_wrap > div.widget__wrap > div > div.widget__body > div > div.widget__row.widget__row_time";
        String q2 = "body > section > div.content_wrap > div > div.main > div > div.__frame_sm > div.forecast_frame.hw_wrap > div.widget__wrap > div > div.widget__body > div > div.widget__row.widget__row_table.widget__row_icon";
        String q3 = "body > section > div.content_wrap > div > div.main > div > div.__frame_sm > div.forecast_frame.hw_wrap > div.widget__wrap > div > div.widget__body > div > div.widget__row.widget__row_table.widget__row_temperature";
        String q4 = "body > section > div.content_wrap > div > div.main > div > div.__frame_sm > div.forecast_frame.hw_wrap > div.widget__wrap > div > div.widget__body > div > div.widget__row.widget__row_table.widget__row_wind-or-gust";
        Elements listTimes = doc1.select(q1);
        Elements listWeathers = doc1.select(q2);
        Elements listTemp = doc1.select(q3);
        Elements listWinds = doc1.select(q4);

        String[] buf = listTimes.select("div").get(0).text().split(" ");
        for (int i = 0; i < 8; i++){
            if(buf[i].length()==3)
                buf[i] = "0"+buf[i];
            res[i][0] = String.format("%c%c:%c%c ",buf[i].charAt(0),buf[i].charAt(1),buf[i].charAt(2),buf[i].charAt(3));
            res[i][1] = listWeathers.select("span.tooltip").get(i).attr("data-text");
            res[i][2] = listTemp.select("span.unit.unit_temperature_c").get(i).text();
            res[i][3] = listWinds.select("span.unit.unit_wind_m_s").get(i).text();
        }
        return res;
    }
}


