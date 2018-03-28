package labstuff.gcu.me.org.mpdcoursework_thomasnimmo;

//Matriculation Number - S1625410
//Name - Thomas Nimmo

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    //List of the variables and widgets used within the application
    private String url1="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String url2="https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String result = "";

    private Button btnPlannedRoadworks;
    private Button btnCurrentIncidents;

    private ListAdapter myAdapter;

    private ListView display;

    List<XMLObject> XMLObjects;

    private EditText searchBar;

    private TextView feedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlannedRoadworks = (Button) findViewById(R.id.plannedRoadworks);
        btnPlannedRoadworks.setOnClickListener(this);

        btnCurrentIncidents = (Button) findViewById(R.id.currentIncidents);
        btnCurrentIncidents.setOnClickListener(this);

        searchBar = (EditText) findViewById(R.id.searchListView);

        feedCount = (TextView) findViewById(R.id.lvText);

        display = (ListView) findViewById(R.id.lvDisplay);

        display.setTextFilterEnabled(true);

        btnPlannedRoadworks.performClick();

    }

    public void onClick(View aview)
    {
        if(aview == btnPlannedRoadworks)
        {
            startPlannedRoadworks();
            btnPlannedRoadworks.setPressed(true);
            btnPlannedRoadworks.setSelected(true);
        }
        else if (aview == btnCurrentIncidents)
        {
            startCurrentIncidents();
            btnPlannedRoadworks.setPressed(false);
            btnCurrentIncidents.setSelected(true);
            btnPlannedRoadworks.setSelected(false);
        }
    }

    //Displays the contents of the XMK objects in the
    //listview using a custom adapter
    private void displayResults() {

        Log.e("My tag", "In displayResults");

        XMLObject[] incidentArray = new XMLObject[XMLObjects.size()];

        for(int i=0; i< XMLObjects.size(); i++){
            incidentArray[i] = XMLObjects.get(i);
        }

        myAdapter = new Custom_Adapter(this, incidentArray);
        display.setAdapter(myAdapter);

        if(btnPlannedRoadworks.isSelected())
        {
            feedCount.setText("Number of planned roadworks found: " + String.valueOf(incidentArray.length));
        }
        else feedCount.setText("Number of current incidents found: " + String.valueOf(incidentArray.length));

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<XMLObject> filteredIncidents = new ArrayList<XMLObject>();

                for (int i = 0; i < XMLObjects.size(); i++) {
                    if (XMLObjects.get(i).toString().toUpperCase().contains(s.toString().toUpperCase())) {
                        filteredIncidents.add(XMLObjects.get(i));
                    }
                }

                XMLObject[] otherArray = new XMLObject[filteredIncidents.size()];

                for (int i = 0; i < filteredIncidents.size(); i++) {
                    otherArray[i] = filteredIncidents.get(i);
                }
                if(btnPlannedRoadworks.isSelected())
                {
                    feedCount.setText("Number of planned roadworks found: " + String.valueOf(otherArray.length));
                }
                else {
                    feedCount.setText("Number of current incidents found: " + String.valueOf(otherArray.length));
                }

                //Creates a new adapter and passes the filtered list to it
                //to only show those items which match teh user input
                Custom_Adapter myNewAdapter = new Custom_Adapter(MainActivity.this, otherArray);
                display.setAdapter(myNewAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void startCurrentIncidents() {
        result = "";
        new Thread(new Task(url2)).start();
    }

    private void startPlannedRoadworks() {
        result = "";
        new Thread(new Task(url1)).start();
    }

    // Separate thread to access the internet resource over network
    class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                //Removes the non relevant lines from the XML feed
                int myInt = 2;
                while((inputLine = in.readLine()) != null)
                {
                    myInt--;
                    if (myInt == -1 || myInt <= -14)
                    {
                        result = result + inputLine;
                    }
                }

                result = result.replaceAll("georss:point", "georsspoint");
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            //Parsing the XML data
            try {

                XMLPullParserHandler parser = new XMLPullParserHandler();
                InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
                XMLObjects = parser.parse(stream);
                result = XMLObjects.toString();
            } catch (Exception e) {e.printStackTrace();}

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    displayResults();
                }
            });
        }

    }

}
