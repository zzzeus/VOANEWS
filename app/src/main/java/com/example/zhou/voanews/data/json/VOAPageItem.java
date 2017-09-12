package com.example.zhou.voanews.data.json;

/**
 * Created by Zhou on 8/20/2017.
 */

public class VOAPageItem {

    /**
     * ImgPath :
     * EndTiming : 4.3
     * ParaId : 1
     * IdIndex : 1
     * sentence_cn : 欢迎收听《今日热点》栏目。
     * ImgWords :
     * Timing : 0.1
     * Sentence : This is What’s Trending Today.
     */

    private String ImgPath;
    public double EndTiming;
    private String ParaId;
    private String IdIndex;
    private String sentence_cn;
    private String ImgWords;
    public double Timing;
    private String Sentence;

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String ImgPath) {
        this.ImgPath = ImgPath;
    }

    public double getEndTiming() {
        return EndTiming;
    }

    public void setEndTiming(double EndTiming) {
        this.EndTiming = EndTiming;
    }

    public String getParaId() {
        return ParaId;
    }

    public void setParaId(String ParaId) {
        this.ParaId = ParaId;
    }

    public String getIdIndex() {
        return IdIndex;
    }

    public void setIdIndex(String IdIndex) {
        this.IdIndex = IdIndex;
    }

    public String getSentence_cn() {
        return sentence_cn;
    }

    public void setSentence_cn(String sentence_cn) {
        this.sentence_cn = sentence_cn;
    }

    public String getImgWords() {
        return ImgWords;
    }

    public void setImgWords(String ImgWords) {
        this.ImgWords = ImgWords;
    }

    public double getTiming() {
        return Timing;
    }

    public void setTiming(double Timing) {
        this.Timing = Timing;
    }

    public String getSentence() {
        return Sentence;
    }

    public void setSentence(String Sentence) {
        this.Sentence = Sentence;
    }
}
