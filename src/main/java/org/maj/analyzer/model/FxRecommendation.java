package org.maj.analyzer.model;

import java.time.LocalDate;

/**
 * Created by shamikm78 on 9/23/16.
 */
public class FxRecommendation {
    private String when;
    private String who;
    private String what;
    private String details;
    private Sentiment sentiment;

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "FxRecommendation{" +
                "when=" + when +
                ", who='" + who + '\'' +
                ", what='" + what + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
