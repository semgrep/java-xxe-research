package parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

// DocumentBuilderFactory is secured from DoS through Entity Expansion by default
public class Tests {

    public static void main(String[] args) {
        // ignore error messages
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));

        try {
            System.out.println("TESTING SAXParserFactory configurations");
            testDefaultConfig();
            testFactorySetFeatureSecureProcessing();
            testSetFeatureDissalowDoctypeDecl();
            testSetFeatureExternalGeneralEntities();
            testSetFeatureExternalParameterEntities();
            testSetValidating();
            testSetXIncludeAware();
            testSetFeatureXInclude();
            testSetFeatureLoadExternalDTD();

            // SAXParser itself can set properties too
            testSAXParserSetPropertyAccessExternalDTD();
            testSAXParserSetPropertyAccessExternalSchema();
            // testSAXParserSetPropertyAccessExternalStylesheet(); --> property not
            // recognized
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }
    }

    public static void testDefaultConfig() throws ParserConfigurationException, SAXException {
        System.out.println("Default Config");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testFactorySetFeatureSecureProcessing() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureDissalowDoctypeDecl() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureExternalGeneralEntities() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureExternalParameterEntities() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetValidating() throws ParserConfigurationException, SAXException {
        System.out.println("setValidating(false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetXIncludeAware() throws ParserConfigurationException, SAXException {
        System.out.println("setXIncludeAware(false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setXIncludeAware(false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureXInclude() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/xinclude", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureLoadExternalDTD() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSAXParserSetPropertyAccessExternalDTD() throws ParserConfigurationException, SAXException {
        System.out.println("SAXParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, \"\");");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        parseAllThree(saxParser);
    }

    public static void testSAXParserSetPropertyAccessExternalSchema()
            throws ParserConfigurationException, SAXException {
        System.out.println("SAXParser.setProperty(ACCESS_EXTERNAL_SCHEMA, \"\");");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        parseAllThree(saxParser);
    }

    public static void testSAXParserSetPropertyAccessExternalStylesheet()
            throws ParserConfigurationException, SAXException {
        System.out.println("SAXParser.setProperty(ACCESS_EXTERNAL_STYLESHEET, \"\");");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        parseAllThree(saxParser);
    }

    /*
     * Parse the test files
     */

    public static void parseAllThree(SAXParser saxParser) {
        parseXmlBomb(saxParser);
        parseXmlDtd(saxParser);
        parseXmlParameterEntity(saxParser);
        parseSchemaDtd(saxParser);
        parseSchemaImport(saxParser);
        parseSchemaInclude(saxParser);
        parseStylesheetDtd(saxParser);
        parseStylesheetImport(saxParser);
        parseStylesheetInclude(saxParser);
        parseStylesheetDocument(saxParser);
    }

    public static void parseXmlBomb(SAXParser saxParser) {
        try {
            File input = new File("payloads-new/xml-attacks/xml-bomb.xml");
            saxParser.parse(input, new DefaultHandler());
            System.out.println("    Parse XML Bomb: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains(
                    "The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Insecure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseXmlDtd(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xml-attacks/input-dtd.xml");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse XML Dtd: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse XML Dtd: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Dtd: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Dtd: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseXmlParameterEntity(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xml-attacks/input-with-parameter-entity.xml");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse XML Parameter Entity: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse XML Parameter Entity: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Parameter Entity: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Parameter Entity: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseSchemaDtd(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xsd-schema-attacks/schema-dtd.xsd");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Schema Dtd: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse Schema Dtd: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Schema Dtd: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Schema Dtd: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseSchemaImport(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xsd-schema-attacks/schema-import.xsd");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Schema Import: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse Schema Import: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Schema Import: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Schema Import: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseSchemaInclude(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xsd-schema-attacks/schema-include.xsd");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Schema Include: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse Schema Include: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Schema Include: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Schema Include: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseStylesheetDtd(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-dtd.xsl");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Stylesheet Dtd: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse Stylesheet Dtd: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Dtd: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Dtd: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseStylesheetDocument(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-document.xsl");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Stylesheet Document: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse Stylesheet Document: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Document: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Document: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseStylesheetImport(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-import.xsl");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Stylesheet Import: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse Stylesheet Import: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Import: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Import: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseStylesheetInclude(SAXParser dBuilder) {
        try {
            File input = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-include.xsl");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Stylesheet Include: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("Connection refused")) {
                System.out.println("    Parse Stylesheet Include: Insecure");
            } else if (e.getMessage().contains(
                    "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Include: Secure");
            } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Stylesheet Include: Secure");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

}