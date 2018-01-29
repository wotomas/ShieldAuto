package info.kimjihyok.shieldautolibrary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by jihyokkim on 2018. 1. 29..
 */
public class DependencyMap extends HashMap<String, String> {
    public DependencyMap() {

    }

    void initializeWithXML(InputStream inputStream) {
        Document document = null;
        try {
            document = readXml(inputStream);
            Element shield = document.getDocumentElement();
            NodeList list = shield.getElementsByTagName("path");

            for (int i = 0; i < list.getLength(); i++) {
                Element path = (Element) list.item(i);
                put(path.getAttribute("name"), path.getTextContent());
            }
        } catch (Exception exceptions) {
            exceptions.printStackTrace();
        }


    }

    private Document readXml(InputStream is) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);

        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        db.setEntityResolver(new NullResolver());

        return db.parse(is);
    }

    private class NullResolver implements EntityResolver {
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
        }
    }

}
