package Blog.app.Repository;

import Blog.app.Blog.Drafts;
import Blog.app.Blog.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftsRepository extends JpaRepository<Drafts, Long> {
}