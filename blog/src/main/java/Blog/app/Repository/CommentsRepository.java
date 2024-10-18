package Blog.app.Repository;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}