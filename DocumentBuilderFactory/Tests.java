package DocumentBuilderFactory;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

// DocumentBuilderFactory is secured from DoS through Entity Expansion by default
public class Tests {


    public static void main(String [] args) {
        // ignore error messages
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));


        try {
            testDefaultConfig();
            testSetFeatureSecreProcessing();

            testAccessExternalDTD();
            testAccessExternalSchema();
            // access external stylesheet is not applicable to DocumentBuilderFactory
            testDisallowDoctypeDecl();
            testExternalGeneralEntities();
            testExternalParameterEntities();
            testSetValidating();
            testSetExpandEntities();
            testSetXIncludeAware();
            testXInclude();
            testLoadExternalDTD();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
            // do nothing
        }
    }

    public static void testDefaultConfig() throws ParserConfigurationException {
        System.out.println("Default Config");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testSetFeatureSecreProcessing() throws ParserConfigurationException {
        System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testAccessExternalDTD() throws ParserConfigurationException {
        System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testAccessExternalSchema() throws ParserConfigurationException {
        System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testDisallowDoctypeDecl() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testExternalGeneralEntities() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testExternalParameterEntities() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testSetValidating() throws ParserConfigurationException {
        System.out.println("setValidating(false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testSetExpandEntities() throws ParserConfigurationException {
        System.out.println("setExpandEntityReferences(false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setExpandEntityReferences(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testSetXIncludeAware() throws ParserConfigurationException {
        System.out.println("setXIncludeAware(false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setXIncludeAware(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testXInclude() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/xinclude", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAllThree(dBuilder);
    }

    public static void testLoadExternalDTD() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
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
            File input = new File("payloads/input-dos/xml-bomb.xml");
            dBuilder.parse(input);
            System.out.println("    Parse XML Bomb: Secure");
        } catch (Exception e) {
            if (e.getMessage().equals("JAXP00010001: The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK.")){
                System.out.println("    Parse XML Bomb: Insecure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("    Parse XML Bomb: Secure");
            }else{
                System.out.println(e.getMessage());
                System.out.println("    Parse XML Bomb: Insecure");
            }
        }
    }

    public static void parseInputWithSchema(DocumentBuilder dBuilder){
        try {
            System.out.print("    Parse Xml Input With Schema: ");
            File input = new File("payloads/input-with-schema/input.xml");
            dBuilder.parse(input);
            System.out.println("Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseInputWithStylesheet(DocumentBuilder dBuilder){
        try {
            System.out.print("    Parse Xml Input With Stylesheet: ");
            File input = new File("payloads/input-with-stylesheet/input.xml");
            dBuilder.parse(input);
            System.out.println("Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("Secure");
            }
            else {
                System.out.println(e.getMessage());
            }
        }
    }
}
