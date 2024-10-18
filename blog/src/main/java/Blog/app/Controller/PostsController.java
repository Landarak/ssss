package Blog.app.Controller;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Service.CommentsService;
import Blog.app.Service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class    PostsController {

    private final PostsService postsService;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    @Autowired
    public PostsController(PostsService postsService, PostsRepository postsRepository, CommentsRepository commentsRepository) {
        this.postsService = postsService;
        this.postsRepository = postsRepository;
        this.commentsRepository = commentsRepository;
    }

    // Crear un nuevo post
    @PostMapping
    public ResponseEntity<Posts> createPost(@RequestBody Posts post) {
        Posts createdPost = postsService.createPosts(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // Obtener todos los posts
    @GetMapping
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postsService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Actualizar un post
    @PutMapping("/{id}")
    public ResponseEntity<Posts> updatePost(@PathVariable Long id, @RequestBody Posts postDetails) {
        Posts updatedPost = postsService.updatePosts(id, postDetails);
        return ResponseEntity.ok(updatedPost);
    }

    // Agregar un comentario a un post
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comments> addCommentToPost(@PathVariable Long postId, @RequestBody Comments comment) {

        Comments createdComment = postsService.addCommentToPost(postId, comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // Obtener todos los posts de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Posts>> getAllPostsByUser(@PathVariable Long userId) {
        List<Posts> posts = postsService.getAllPostsbyUser(userId);
        return ResponseEntity.ok(posts);
    }

    // Eliminar un post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postsService.deletePosts(id);
        return ResponseEntity.noContent().build();
    }
}
