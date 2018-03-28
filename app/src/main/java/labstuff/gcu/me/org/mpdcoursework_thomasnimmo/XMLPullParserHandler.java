package labstuff.gcu.me.org.mpdcoursework_thomasnimmo;

//Matriculation Number - S1625410
//Name - Thomas Nimmo

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//A class to parse the information related to each item to an object and
//pass this object into a list of these objects
public class XMLPullParserHandler {

    private List<XMLObject> XMLObjectList = new ArrayList<XMLObject>();
    private XMLObject _XMLObject;
    private String text;

    public List<XMLObject> getPlannedRoadworks() {
        return XMLObjectList;
    }

    public List<XMLObject> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            // create a new instance of employee
                            _XMLObject = new XMLObject();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            // add employee object to list
                            XMLObjectList.add(_XMLObject);
                        }else if (tagname.equalsIgnoreCase("title")) {
                            _XMLObject.setTitle(text);
                        }  else if (tagname.equalsIgnoreCase("description")) {
                            _XMLObject.setDescription(text);
                            //plannedRoadworks.add(plannedRoadwork);
                        }  else if (tagname.equalsIgnoreCase("link")) {
                            _XMLObject.setLink(text);
                        }  else if (tagname.equalsIgnoreCase("georsspoint")) {
                            _XMLObject.setGeorssPoint(text);
                        }  else if (tagname.equalsIgnoreCase("author")) {
                            _XMLObject.setAuthor(text);
                        }  else if (tagname.equalsIgnoreCase("comments")) {
                           _XMLObject.setComments(text);
                        }  else if (tagname.equalsIgnoreCase("pubdate")) {
                            _XMLObject.setPupDate(text);
                        }

                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return XMLObjectList;
    }
}

