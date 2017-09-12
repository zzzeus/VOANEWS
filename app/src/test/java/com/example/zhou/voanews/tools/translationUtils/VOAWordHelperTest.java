package com.example.zhou.voanews.tools.translationUtils;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.example.zhou.voanews.tools.DownloadHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.ErrorListener;

import static org.junit.Assert.*;

/**
 * Created by Zhou on 2017/9/8.
 */
public class VOAWordHelperTest extends DefaultHandler{
    public static final String url="http://word.iyuba.com/words/apiWord.jsp?q=%s&uid=50132762&appid=201&testmode=1";
    int result;
    String key;
    String nodeName;
    String pron;
    String audioUrl;
    StringBuilder trans=new StringBuilder();
    @Test
    public void getMeaning() throws Exception {
        String word="network";
        String t= DownloadHelper.getPageText(String.format(url,word));
//        Document doc=Jsoup.parse(t);
//        for (Element e:doc.getElementsByTag("orig")) {
//            System.out.println(e.data());
//        }
        SAXParserFactory factory=SAXParserFactory.newInstance();
        XMLReader xmlReader=factory.newSAXParser().getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.parse(new InputSource(new StringReader(t)));
//        System.out.println(t);
    }


    @Override
    public void startDocument() throws SAXException {
//        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
//        super.endDocument();
        System.out.println("...........over...........");
        System.out.println(trans.toString());
        System.out.println("audioUrl:");
        System.out.println(audioUrl);
        System.out.println("pron:");
        System.out.println(pron);
        System.out.println("result");
        System.out.println(result);
        System.out.println(key);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        super.startElement(uri, localName, qName, attributes);
//        System.out.println("uri = [" + uri + "], localName = [" + localName + "], qName = [" + qName + "], attributes = [" + attributes + "]");
//        System.out.println("localName = [" + localName + "]");
        nodeName=qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
//        System.out.println("localName = [" + localName + "]");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
//        System.out.println(nodeName);

        if("orig".equals(nodeName)){
            trans.append(ch, start, length);
//            trans.append('\n');
        }else if("trans".equals(nodeName)){
           trans.append(ch, start, length);
//            trans.append('\n');
        }else if("pron".equals(nodeName)&&length>1){
            pron=String.valueOf(ch, start, length);
            System.out.println(length);
            System.out.println(pron);
        }else if("audio".equals(nodeName)&&length>1){
            audioUrl=String.valueOf(ch,start,length);
            System.out.println(length);
            System.out.println(audioUrl);
        }else if("result".equals(nodeName)&&length==1)
        {
            String s=String.valueOf(ch, start, length);
            System.out.println("sssssssss");
            System.out.println(0+s);
            System.out.println("sssssssss");
            if(s.equals("1"))
                result=1;
        }else if("key".equals(nodeName)&&length>1){
            key=String.valueOf(ch, start, length);
        }


    }
}