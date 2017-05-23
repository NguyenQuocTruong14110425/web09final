package model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="News")
public class NewsConten {
 

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
      
    @NotEmpty
    @Column(name="TITLE", nullable=false)
    private String title;
 
    @NotEmpty
    @Column(name="CONTENT", nullable=false)
    private String content;
    
    @NotEmpty
    @Column(name="DATEPOST", nullable=false)
    private String datepost;
    
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private Set<UserDocument> userDocuments = new HashSet<UserDocument>();
     
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }

 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
    public String getDatepost() {
        return datepost;
    }
 
    public void setDatepost(String datepost) {
        this.datepost = datepost;
    }
    public Set<UserDocument> getUserDocuments() {
        return userDocuments;
    }
 
    public void setUserDocuments(Set<UserDocument> userDocuments) {
        this.userDocuments = userDocuments;
    }
  
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof NewsConten))
            return false;
        NewsConten other = (NewsConten) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "NewsConten [id=" + id  + ", title=" + title + ", content=" + content
                 + "]";
    }
}