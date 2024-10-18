package Blog.app.Service;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    @Autowired
    public CommentsService(CommentsRepository commentsRepository, PostsRepository postsRepository, UsersRepository usersRepository) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;

    }

    public Comments createComments(Comments comments) {

        if (comments.getUser() == null || comments.getPost() == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }

        return commentsRepository.save(comments);
    }

    public List<Comments> getAllCommentsbyPost(Long post_id) {
        try {
            Posts post = postsRepository.findById(post_id)
                    .orElseThrow(() -> new Exception("Post not found"));
            return post.getComentarios();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Comments updateComments(Long id, Comments comments) {
    Comments comment = commentsRepository.findById(id).get();
    comment.setDescription(comments.getDescription());

        return commentsRepository.save(comments);
    }

    public void deleteComments(Long id) {
        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        commentsRepository.deleteById(id);
    }


}
