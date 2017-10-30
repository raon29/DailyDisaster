package com.dailydisaster;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.DocumentsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    TextView showWhen, showWeather, showWarning;
    Button goAddSchedule, goCheck, goWeek;
    Document doc = null;
    DBHelper WdbHelper;
    SQLiteDatabase Wsql;
    Cursor Wcursor;
    DBHelper dbHelper;
    SQLiteDatabase sql;
    Cursor cursor;
    int c_time;
    long longTime = System.currentTimeMillis();
    Date curdate = new Date(longTime);
    double DateTime;
    String wearther="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showWhen = (TextView) findViewById(R.id.when);
        showWeather = (TextView) findViewById(R.id.weather);
        showWarning = (TextView) findViewById(R.id.warning);
        goAddSchedule = (Button) findViewById(R.id.goAddSchedule);
        goCheck = (Button) findViewById(R.id.goCheck);
        goWeek = (Button) findViewById(R.id.goWeek);
        WdbHelper = new DBHelper(getApplicationContext(), "Week");
        Wsql = WdbHelper.getWritableDatabase();
        dbHelper = new DBHelper(getApplicationContext(), "Schedule");
        sql = dbHelper.getWritableDatabase();
        String when = null;
        String name = null;
        int count = 0;
        boolean check = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        Date firstdate = new Date();

        Calendar calendar = Calendar.getInstance( );
        int nowWeek = (calendar.get(Calendar.DAY_OF_WEEK)+5)%7+1;
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        Wcursor = Wsql.rawQuery("SELECT * FROM Week WHERE position%8 == "+nowWeek+" AND name != '-';", null);
        Wcursor.moveToFirst();
        if (Wcursor.getCount() > 0) {
            while ((!check && (Wcursor.getCount() > 0) && count < Wcursor.getCount())) {
                int hour = Wcursor.getInt(Wcursor.getColumnIndex("position"))/8;
                if(hour<nowHour){
                    Wcursor.moveToNext();
                    count++;
                }
                else{
                    String weekStr="";
                    switch (nowWeek) {
                        case 1:weekStr = "월요일";break;
                        case 2:weekStr = "화요일";break;
                        case 3:weekStr = "수요일";break;
                        case 4:weekStr = "목요일";break;
                        case 5:weekStr = "금요일";break;
                        case 6:weekStr = "토요일";break;
                        case 7:weekStr = "일요일";break;
                    }
                    String Wname = Wcursor.getString(Wcursor.getColumnIndex("name"));
                    showWhen.setText(weekStr+ " " +hour+"시\n"+Wname);
                    c_time = (hour/3)*3;
                    if(c_time == 0)
                        c_time = 24;
                    GetXMLTask task = new GetXMLTask();
                    task.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=3000000000");
                    check=true;
                }
            }
        }
        count=0;
        check = false;
        cursor = sql.rawQuery("SELECT * FROM Schedule ORDER BY datetime ASC;", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while ((!check && (cursor.getCount() > 0) && count < cursor.getCount())) {
                DateTime = cursor.getInt(cursor.getColumnIndex("datetime"));
                when = (int) (DateTime / 100000000 + 2000) + "-" + (int) (DateTime % 100000000) / 1000000 + "-" + (int) (DateTime % 1000000) / 10000 + " " + (int) (DateTime % 10000) / 100 + ":" + (int) (DateTime % 100);
                name = cursor.getString(cursor.getColumnIndex("name"));
                try {
                    firstdate = dateFormat.parse(when);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (curdate.compareTo(firstdate) > 0) {
                    cursor.moveToNext();
                    count++;
                } else {
                    when = (int)(DateTime / 100000000 + 2000) + "-" + (int) (DateTime % 100000000) / 1000000 + "-" + (int) (DateTime % 1000000) / 10000 + "\n" + (int) (DateTime % 10000) / 100 + ":" + (int) (DateTime % 100);
                    showWhen.setText(when + "\n" + name);


                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    int c_year = cal.get(cal.YEAR);
                    int c_month = cal.get ( cal.MONTH ) + 1 ;
                    int c_date = cal.get ( cal.DATE ) ;

                    DateTime = cursor.getInt(cursor.getColumnIndex("datetime"));
                    if((c_year==(int)(DateTime / 100000000 + 2000))&&(c_month==(int) (DateTime % 100000000) / 1000000)&&(c_date==(int) (DateTime % 1000000) / 10000)) {
                        String local = "30";
                        c_time = (((cursor.getInt(cursor.getColumnIndex("datetime")) % 10000) / 100) / 3) * 3;
                        if (c_time == 0)
                            c_time = 24;

                        //지역 설정
                        switch (cursor.getString(cursor.getColumnIndex("place"))) {
                            case "서울 특별시":local = "11";break;
                            case "부산 광역시":local = "26";break;
                            case "대구 광역시":local = "27";break;
                            case "인천 광역시":local = "28";break;
                            case "광주 광역시":local = "29";break;
                            case "대전 광역시":local = "30";break;
                            case "울산 광역시":local = "31";break;
                            case "경기도":local = "41";break;
                            case "강원도":local = "42";break;
                            case "충청 북도":local = "43";break;
                            case "충청 남도":local = "44";break;
                            case "전라 북도":local = "45";break;
                            case "전라 남도":local = "46";break;
                            case "경상 북도":local = "47";break;
                            case "경상 남도":local = "48";break;
                            case "제주 특별 자치도":local = "50";break;
                        }
                        //파싱시작하려함
                        GetXMLTask task = new GetXMLTask();
                        task.execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=" + local + "00000000");
                    }else{
                        showWeather.setText("당일이 아닙니다");
                    }
                    check = true;
                }
            }
        }
//        if (!check) {
//            showWhen.setText("일정이 없습니다.");
//            showWeather.setText("미래도 없습니다.");
//        }
        sql.close();
        goAddSchedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSchedule.class);
                startActivity(intent);
                finish();
            }
        });

        goCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Check.class);
                startActivity(intent);
                finish();
            }
        });
        goWeek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeeklySchedule.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Setcover(View view) {
        Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
        startActivity(intent);
        finish();
    }


    //파싱 하는 class
    private class GetXMLTask extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            wearther = "";
            NodeList nodeList = doc.getElementsByTagName("data");

            for(int i = 0; i< nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList nameList  = fstElmnt.getElementsByTagName("temp");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();

                NodeList timeList = fstElmnt.getElementsByTagName("hour");
                if(Integer.parseInt(timeList.item(0).getChildNodes().item(0).getNodeValue()) == c_time) {
                    s += "" + timeList.item(0).getChildNodes().item(0).getNodeValue() + "시\n기온 : " + ((Node) nameList.item(0)).getNodeValue() + "\n";

                    NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
                    s += "날씨 : " ;
                    wearther = websiteList.item(0).getChildNodes().item(0).getNodeValue();
                    break;
                }

            }
            showWeather.setText(s +"\""+wearther+"\"");

            if(wearther==""){ showWarning.setText(""); }
            else if(wearther.contains("비")||wearther.contains("눈")){
                showWarning.setText("우산을 챙기세요!");
            }
            else{
                showWarning.setText("아무것도 필요없어");
            }

            super.onPostExecute(doc);
        }
    }



    public double getDateTime(){
        return this.DateTime;
    }

    public void setPassword(View view){
        Intent intent=new Intent(MainActivity.this, SetPassword.class);
        startActivity(intent);
        finish();
    }

    public void Predice(View view) {
        Intent intent = new Intent(MainActivity.this, Predict.class);
        startActivity(intent);
        finish();
    }

}