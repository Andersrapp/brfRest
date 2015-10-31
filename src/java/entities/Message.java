package entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anders
 */
@XmlRootElement
public class Message {

    private String message;
    private int statusCode;
    private String documentationLink;
    private Link link;

    public Message() {
    }

    public Message(String message, int statusCode, String documentationLink) {
        this.message = message;
        this.statusCode = statusCode;
        this.documentationLink = documentationLink;
    }

    public Message(String message, int statusCode, Link link) {
        this.message = message;
        this.statusCode = statusCode;
        this.link = link;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDocumentationLink() {
        return documentationLink;
    }

    public void setDocumentationLink(String documentationLink) {
        this.documentationLink = documentationLink;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

}
