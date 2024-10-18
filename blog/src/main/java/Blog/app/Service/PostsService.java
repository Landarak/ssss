package Blog.app.Service;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final CommentsRepository commentsRepository;
    private final CommentsService commentsService;
    @Autowired
    public PostsService(PostsRepository postsRepository, UsersRepository usersRepository, CommentsRepository commentsRepository, CommentsService commentsService) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
        this.commentsRepository = commentsRepository;
        this.commentsService = commentsService;
    }


    public Posts createPosts(Posts posts) {

        if (posts.getUser() == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }

        return postsRepository.save(posts);
    }
    public List<Posts> getAllPosts() {
        return postsRepository.findAll();
    }

    public Posts updatePosts(Long id, Posts posts) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLabel(posts.getLabel());
        post.setDescription(posts.getDescription());
        post.setComentarios(posts.getComentarios());
        return postsRepository.save(post);  // Debes guardar el post actualizado, no el nuevo post directamente
    }


    public Comments addCommentToPost( Long postId, Comments comment)  {

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));


        comment.setPost(post);



        return commentsRepository.save(comment);
    }

    public List<Posts> getAllPostsbyUser(Long user_id) {
        try {
            Users user = usersRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("User not found"));
            return user.getPosts();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deletePosts(Long id) {

        postsRepository.deleteById(id);
    }


}
