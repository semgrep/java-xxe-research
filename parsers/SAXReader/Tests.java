package parsers.SAXReader;

import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

public class Tests {

    public static void main(String [] args) {
        // ignore error messages
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));


        try {
            System.out.println("TESTING SAXReader configurations");
            testDefaultConfig();
            test1();
            test2();
            //test3(); --> Not recognized
            test4();
            test5();
            test6();
            test7();
            test8();
            test9();
            test10();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }
    }

    public static void testDefaultConfig(){
        System.out.println("Default config");
        SAXReader saxReader = new SAXReader();
        parseAll(saxReader);
    }

    public static void test0() throws SAXException {
        System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        parseAll(saxReader);
    }

    public static void test1() throws SAXException {
        System.out.println("setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        parseAll(saxReader);
    }

    public static void test2() throws SAXException {
        System.out.println("setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        parseAll(saxReader);
    }

    public static void test3() throws SAXException {
        System.out.println("setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, \"\");");
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        parseAll(saxReader);
    }

    public static void test4() throws SAXException {
        System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        parseAll(saxReader);
    }

    public static void test5() throws SAXException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        parseAll(saxReader);
    }

    public static void test6() throws SAXException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        parseAll(saxReader);
    }

    public static void test7() throws SAXException{
        System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/xinclude", false);
        parseAll(saxReader);
    }

    public static void test8() throws SAXException{
        System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        parseAll(saxReader);
    }
    public static void test9() throws SAXException{
        System.out.println("NoopEntity resolver");
        SAXReader saxReader = new SAXReader();
        EntityResolver noop = (publicId, systemId) -> new InputSource(new StringReader(""));
        saxReader.setEntityResolver(noop);
        parseAll(saxReader);
    }

    public static void test10(){
        System.out.println("setIncludeExternalDTDDeclarations(false);");
        SAXReader saxReader = new SAXReader();
        saxReader.setIncludeExternalDTDDeclarations(false);
        parseAll(saxReader);
    }

    public static void parseAll(SAXReader saxReader){
        parseXmlBomb(saxReader);
        parseInputWithSchema(saxReader);
        parseInputWithStylesheet(saxReader);
        parseInputWithParameterEntity(saxReader);
    }

    public static void parseXmlBomb(SAXReader sr){
        try {
            File input = new File("payloads/input-dos/xml-bomb.xml");
            sr.read(input);
            System.out.println("    Parse XML Bomb: Secure");
        } catch (Exception e) {
            if (e.getMessage().contains("JAXP00010001: The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Insecure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Secure");
            }else if(e.getMessage().contains("Cannot find the declaration of element")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }

    }

    public static void parseInputWithSchema(SAXReader saxReader){
        try {
            File input = new File("payloads/input-with-schema/input.xml");
            saxReader.read(input);
            System.out.println("    Parse Input With Schema: Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse Input With Schema: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Schema: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")) {
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Schema: Secure");
            }else if(e.getMessage().contains("Cannot find the declaration of element")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Schema: Secure");
            }else if(e.getMessage().contains("must match DOCTYPE root")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Schema: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseInputWithStylesheet(SAXReader saxReader){
        try {
            File input = new File("payloads/input-with-stylesheet/input.xml");
            saxReader.read(input);
            System.out.println("    Parse Input With Stylesheet: Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse Input With Stylesheet: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Stylesheet: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Stylesheet: Secure");
            }else if(e.getMessage().contains("Cannot find the declaration of element")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Stylesheet: Secure");
            }else if(e.getMessage().contains("must match DOCTYPE root")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Stylesheet: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseInputWithParameterEntity(SAXReader saxReader){
        try {
            File input = new File("payloads/input-with-parameter-entity/input.xml");
            saxReader.read(input);
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
            }else if(e.getMessage().contains("Cannot find the declaration of element")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Xml Input With Parameter Entity: Secure");
            }
            else {
                System.out.println(e.getMessage());
            }
        }
    }
}
