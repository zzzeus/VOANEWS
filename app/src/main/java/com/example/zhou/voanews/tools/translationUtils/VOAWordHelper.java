package com.example.zhou.voanews.tools.translationUtils;

import android.util.Log;

import com.example.zhou.voanews.data.json.VOAWord;
import com.example.zhou.voanews.tools.DownloadHelper;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Zhou on 2017/9/8.
 */

public class VOAWordHelper extends DefaultHandler {
    public static final String url="http://word.iyuba.com/words/apiWord.jsp?q=%s&uid=50132762&appid=201&testmode=1";
    private static final String TAG = "VOAWordHelper";
    int result;
    String key;
    String nodeName;
    String pron;
    String audioUrl;
    StringBuilder trans=new StringBuilder();
    VOAWord voaWord=new VOAWord();
    public  VOAWord getMeaning(String word){
        String t=DownloadHelper.getPageText(String.format(url,word));
        SAXParserFactory factory=SAXParserFactory.newInstance();
        XMLReader xmlReader= null;
        try {
            xmlReader = factory.newSAXParser().getXMLReader();
            xmlReader.setContentHandler(this);
            xmlReader.parse(new InputSource(new StringReader(t)));
        } catch (SAXException e) {
            Log.e(TAG, "getMeaning: ",e );
        } catch (ParserConfigurationException e) {
            Log.e(TAG, "getMeaning: ",e );
        } catch (IOException e) {
            Log.e(TAG, "getMeaning: ",e );
        }
        return voaWord;
    }
    @Override
    public void startDocument() throws SAXException {
//        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
//        super.endDocument();
//        System.out.println("...........over...........");
        voaWord.text=trans.toString();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName=qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if("orig".equals(nodeName)){
            trans.append(ch, start, length);
        }else if("trans".equals(nodeName)){
            trans.append(ch, start, length);
        }else if("pron".equals(nodeName)&&length>1){
            voaWord.pron=String.valueOf(ch, start, length);
        }else if("audio".equals(nodeName)&&length>1){
            voaWord.audioUrl=String.valueOf(ch,start,length);
        }else if("result".equals(nodeName)&&length==1)
        {
            String s=String.valueOf(ch, start, length);
            if(s.equals("1"))
                voaWord.result=1;
        }else if("key".equals(nodeName)&&length>1){
            voaWord.key=String.valueOf(ch, start, length);
        }
    }
}
