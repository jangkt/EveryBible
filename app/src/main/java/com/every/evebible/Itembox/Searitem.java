package com.every.evebible.Itembox;

public class Searitem {
    private String testament;
    private String bible;
    private int chapter;
    private int verse;
    private String sentence;

    public Searitem(String bible, String sentence, int chapter, int verse) {
        this.bible = bible;
        this.sentence = sentence;
        this.chapter = chapter;
        this.verse = verse;
    }

    public String getTestament() {
        return testament;
    }

    public String getBible() {
        return bible;
    }

    public int getChapter() {
        return chapter;
    }

    public int getVerse() {
        return verse;
    }

    public String getSentence() {
        return sentence;
    }

    public void setTestament(String testament) {
        this.testament = testament;
    }

    public void setBible(String bible) {
        this.bible = bible;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
