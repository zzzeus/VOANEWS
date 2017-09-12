package com.example.zhou.voanews.data.json;

/**
 * Created by Zhou on 2017/9/8.
 */

public class VOAWord {
    public int result;
    public String key;
    public String nodeName;
    public String pron;
    public String audioUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String text;
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
