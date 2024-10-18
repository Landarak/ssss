package Blog.app.Blog;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.List;

@Entity
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private Long Friends;

    @ManyToMany
    @JoinTable(
            name = "amistades",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<Users> amigos;

    @OneToMany(mappedBy = "user")
    private List<Posts> posts;
    @OneToMany(mappedBy = "user")
    private List<Drafts> drafts;
    @OneToMany(mappedBy = "user")
    private List<Comments> comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getFriends() {
        return Friends;
    }

    public void setFriends(Long friends) {
        Friends = friends;
    }

    public List<Users> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Users> amigos) {
        this.amigos = amigos;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public List<Drafts> getDrafts() {
        return drafts;
    }

    public void setDrafts(List<Drafts> drafts) {
        this.drafts = drafts;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
