package SearchEngine;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    public static List<Doc> read(String filename) throws Exception {
        File file = new File(filename);
        List<Doc> docs = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            // get <doc>
            NodeList list = doc.getElementsByTagName("doc");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get the document's attributes
                    String id = element.getAttribute("id");
                    String url = element.getAttribute("url");
                    String title = element.getAttribute("title");
                    String content = element.getTextContent();

                    //For debugging purposes
                    //System.out.println("Current DocumentID :" + id);
                    //System.out.println("URL: " + url);
                    //System.out.println("Title: " + title);
                    //System.out.println("Content: " + content);

                    docs.add(new Doc(id, url, title, content.split("[\r\n\t\f]+|\n+")));
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            throw e;
        }

        return docs;
    }
}
