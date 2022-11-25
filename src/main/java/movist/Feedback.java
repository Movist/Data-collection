package movist;

public class Feedback {
    private String pluses;
    private String minuses;
    private String comment;



    public String getPluses() {
        return pluses;
    }

    public void setPluses(String pluses) {
        this.pluses = pluses;
    }

    public String getMinuses() {
        return minuses;
    }

    public void setMinuses(String minuses) {
        this.minuses = minuses;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "feedback{" +
                "pluses='" + pluses + '\'' +
                ", minuses='" + minuses + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
