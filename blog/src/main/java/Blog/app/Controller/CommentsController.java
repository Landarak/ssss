package Blog.app.Controller;

import Blog.app.Blog.Comments;
import Blog.app.Service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    private final CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    // Crear un nuevo comentario
    @PostMapping
    public ResponseEntity<Comments> createComment(@RequestBody Comments comment) {
        Comments createdComment = commentsService.createComments(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // Obtener todos los comentarios de un post
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comments>> getAllCommentsByPost(@PathVariable Long postId) {
        List<Comments> comments = commentsService.getAllCommentsbyPost(postId);
        return ResponseEntity.ok(comments);
    }

    // Actualizar un comentario
    @PutMapping("/{id}")
    public ResponseEntity<Comments> updateComment(@PathVariable Long id, @RequestBody Comments commentDetails) {
        Comments updatedComment = commentsService.updateComments(id, commentDetails);
        return ResponseEntity.ok(updatedComment);
    }

    // Eliminar un comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentsService.deleteComments(id);
        return ResponseEntity.noContent().build();
    }
}
