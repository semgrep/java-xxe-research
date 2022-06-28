package parsers.DocumentBuilderFactory;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

// DocumentBuilderFactory is secured from DoS through Entity Expansion by default
public class Tests {


    public static void main(String [] args) {
        // ignore error messages
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));


        try {
            System.out.println("TESTING DocumentBuilderFactory configurations");
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
            testLoadDTDGrammar();
            testNamespaces();
            testSetNameSpaceAware();

            testDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
            // do nothing
        }
    }

    public static void testDefaultConfig() throws ParserConfigurationException {
        System.out.println("Default Config");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testSetFeatureSecreProcessing() throws ParserConfigurationException {
        System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testAccessExternalDTD() throws ParserConfigurationException {
        System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testAccessExternalSchema() throws ParserConfigurationException {
        System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testDisallowDoctypeDecl() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testExternalGeneralEntities() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testExternalParameterEntities() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testSetValidating() throws ParserConfigurationException {
        System.out.println("setValidating(false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testSetExpandEntities() throws ParserConfigurationException {
        System.out.println("setExpandEntityReferences(false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setExpandEntityReferences(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testSetXIncludeAware() throws ParserConfigurationException {
        System.out.println("setXIncludeAware(false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setXIncludeAware(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testXInclude() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/xinclude", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testLoadExternalDTD() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testLoadDTDGrammar() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testNamespaces() throws ParserConfigurationException {
        System.out.println("setFeature(\"http://apache.org/xml/features/namespaces\", false)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testSetNameSpaceAware() throws ParserConfigurationException {
        System.out.println("setNameSapceAware(true)");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        parseAll(dBuilder);
    }

    public static void testDocumentBuilder() throws ParserConfigurationException {
        System.out.println("testDocumentBuilder set empty entity resolver");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        dBuilder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                return new InputSource(new StringReader(""));
            }
        });
        parseAll(dBuilder);
    }

    /*
        Parse the test files
     */

    public static void parseAll(DocumentBuilder db){
        parseXmlBomb(db);
        parseInputWithSchema(db);
        parseInputWithStylesheet(db);
        parseInputWithParameterEntity(db);
    }

    public static void parseXmlBomb(DocumentBuilder dBuilder){
        try {
            File input = new File("payloads/input-dos/xml-bomb.xml");
            dBuilder.parse(input);
            System.out.println("    Parse XML Bomb: Secure");
        } catch (Exception e) {
            if (e.getMessage().equals("JAXP00010001: The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Insecure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseInputWithSchema(DocumentBuilder dBuilder){
        try {
            File input = new File("payloads/input-with-schema/input.xml");
            dBuilder.parse(input);
            System.out.println("    Parse XML Input With Schema: Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse XML Input With Schema: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Input With Schema: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Input With Schema: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseInputWithStylesheet(DocumentBuilder dBuilder){
        try {
            File input = new File("payloads/input-with-stylesheet/input.xml");
            dBuilder.parse(input);
            System.out.println("    Parse XML Input With Schema: Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse Xml Input With Stylesheet: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Xml Input With Stylesheet: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Xml Input With Stylesheet: Secure");
            }
            else {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void parseInputWithParameterEntity(DocumentBuilder dBuilder){
        try {
            File input = new File("payloads/input-with-parameter-entity/input.xml");
            dBuilder.parse(input);
            System.out.println("    Parse Xml Input With Parameter Entity: Secure");
        } catch (Exception e){
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse Xml Input With Parameter Entity: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Xml Input With Parameter Entity: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Xml Input With Parameter Entity: Secure");
            }
            else {
                System.out.println(e.getMessage());
            }
        }
    }
}
