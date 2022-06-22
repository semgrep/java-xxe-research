package DocumentBuilderFactory;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

// DocumentBuilderFactory is secured from DoS through Entity Expansion by default
public class Tests {


    public static void main(String [] args) throws ParserConfigurationException {
        testDefaultConfig();
        testAccessExternalDTD();
        testAccessExternalSchema();
        // access external stylesheet is not applicable to DocumentBuilderFactory
    }

    public static void testDefaultConfig() throws ParserConfigurationException {
        System.out.println("Default Config");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    // This is secure!
    public static void testAccessExternalDTD() throws ParserConfigurationException {
        System.out.println("Access External DTD");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testAccessExternalSchema() throws ParserConfigurationException {
        System.out.println("Access External Schema");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);

    }





    /*
        Parse the test files
     */

    public static void parseAllThree(DocumentBuilder db){
        parseXmlBomb(db);
        parseInputWithSchema(db);
        parseInputWithStylesheet(db);
    }

    public static void parseXmlBomb(DocumentBuilder dBuilder){
        try {
            System.out.println("    Parse Xml Bomb");
            File input = new File("payloads/input-dos/xml-bomb.xml");
            dBuilder.parse(input);
        } catch (Exception e) {
            System.out.println("    Error: " + e.getMessage());
        }
    }

    public static void parseInputWithSchema(DocumentBuilder dBuilder){
        try {
            System.out.println("    Parse Xml Input With Schema");
            File input = new File("payloads/input-with-schema/input.xml");
            dBuilder.parse(input);
        } catch (Exception e) {
            System.out.println("    Error: " + e.getMessage());
        }
    }

    public static void parseInputWithStylesheet(DocumentBuilder dBuilder){
        try {
            System.out.println("    Parse Xml Input With Stylesheet");
            File input = new File("payloads/input-with-stylesheet/input.xml");
            dBuilder.parse(input);
        } catch (Exception e) {
            System.out.println("    Error: " + e.getMessage());
        }
    }


}
