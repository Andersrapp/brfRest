package se.andersrapp.brf.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Anders
 */
public class Link implements Serializable {

    String uri;
    String rel;

    public Link() {
    }

    public Link(String uri, String rel) {
        this.uri = uri;
        this.rel = rel;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 91 * hash + Objects.hashCode(this.uri);
        hash = 91 * hash + Objects.hashCode(this.rel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        if (!Objects.equals(this.uri, other.uri)) {
            return false;
        }
        if (!Objects.equals(this.rel, other.rel)) {
            return false;
        }
        return true;
    }

}
