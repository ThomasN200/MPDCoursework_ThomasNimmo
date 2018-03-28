package labstuff.gcu.me.org.mpdcoursework_thomasnimmo;

//Matriculation Number - S1625410
//Name - Thomas Nimmo

//An object to hold the information related to each current incident or planned roadwork item
public class XMLObject {
    private String title;
    private String description;
    private String link;
    private String georssPoint;
    private String author;
    private String comments;
    private String pupDate;


    public void setTitle(String _title) {
        this.title = _title;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public void setLink(String _link) {
        this.link = _link;
    }

    public void setGeorssPoint(String _georssPoint) {
        this.georssPoint = _georssPoint;
    }

    public void setAuthor(String _author) {
        this.author = _author;
    }

    public void setComments(String _comments) {
        this.comments = _comments;
    }

    public void setPupDate(String _pubDate) {
        this.pupDate = _pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return  link;
    }

    public String getGeorssPoint() {
        return  georssPoint;
    }

    public String getAuthor() {
        return author;
    }

    public String getComments() {
        return comments;
    }

    public String getPupDate() {
        return pupDate;
    }

    //Returns a string excluding the author and comments if these elements are empty (included
    // as they very often are)
    @Override
    public String toString() {

        if (!author.matches(".*[a-z].*") && !comments.matches(".*[a-z].*")) {
            return "Title: " + title + "\n\nDescription: " + description + "\n\nLink: " + link + "\nGeorss point: " + georssPoint
                    + "\nPublished Date: " + pupDate;
        }
        else {
            return "Title: " + title + "\n\nDescription: " + description + "\n\nLink: " + link + "\nGeorss point: " + georssPoint
                    + "\nAuthor: " + author + "\nComments: " + comments + "\nPublished Date: " + pupDate;
        }
    }
}

