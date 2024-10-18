package Blog.app.Repository;

import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
