package parsers.SAXBuilder;

import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

public class Tests {


    public static void main(String [] args) {
        // ignore error messages
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));


        try {
            System.out.println("TESTING SAXBuilder configurations");
            testDefaultConfig();
            testConstructor1();
            testConstructor2();

            test1();
            test2();
            test3();
            test4();
            test6();
            test7();
            test8();
            test9();
            test5();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }
    }

    public static void testDefaultConfig(){
        System.out.println("Default config empty constructor");
        SAXBuilder saxBuilder = new SAXBuilder();
        parseAll(saxBuilder);
    }

    public static void testConstructor1(){
        System.out.println("Default config DTDValidating");
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.DTDVALIDATING);
        parseAll(saxBuilder);
    }

    public static void testConstructor2(){
        System.out.println("Default config XSDValidating");
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        parseAll(saxBuilder);
    }

    public static void test1(){
        System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        parseAll(saxBuilder);
    }

    public static void test2(){
        System.out.println("setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        parseAll(saxBuilder);
    }

    public static void test3(){
        System.out.println("setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        parseAll(saxBuilder);
    }

    public static void test4(){
        System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        parseAll(saxBuilder);
    }

    public static void test6(){
        System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        parseAll(saxBuilder);
    }

    public static void test7(){
        System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        parseAll(saxBuilder);
    }

    public static void test8(){
        System.out.println("setExpandEntities(false)");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setExpandEntities(false);
        parseAll(saxBuilder);
    }

    public static void test9(){
        System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/xinclude", false);
        parseAll(saxBuilder);
    }

    public static void test5(){
        System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        parseAll(saxBuilder);
    }


    public static void parseAll(SAXBuilder saxBuilder){
        parseXmlBomb(saxBuilder);
        parseInputWithSchema(saxBuilder);
        parseInputWithStylesheet(saxBuilder);
        parseInputWithParameterEntity(saxBuilder);
    }

    public static void parseXmlBomb(SAXBuilder saxBuilder){
        try {
            File input = new File("payloads/input-dos/xml-bomb.xml");
            saxBuilder.build(input);
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

    public static void parseInputWithSchema(SAXBuilder saxBuilder){
        try {
            File input = new File("payloads/input-with-schema/input.xml");
            saxBuilder.build(input);
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

    public static void parseInputWithStylesheet(SAXBuilder saxBuilder){
        try {
            File input = new File("payloads/input-with-stylesheet/input.xml");
            saxBuilder.build(input);
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

    public static void parseInputWithParameterEntity(SAXBuilder saxBuilder){
        try {
            File input = new File("payloads/input-with-parameter-entity/input.xml");
            saxBuilder.build(input);
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
