package org.maj.analyzer.model;

/**
 * Created by shamikm78 on 9/16/16.
 */
public class StockDetails {
    private String source;
    private String user;
    private String message;
    private Sentiment sentiment;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "StockDetails{" +
                "source='" + source + '\'' +
                ", user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", sentiment=" + sentiment +
                '}';
    }
}
