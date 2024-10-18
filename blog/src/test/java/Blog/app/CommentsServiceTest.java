package Blog.app;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import Blog.app.Service.CommentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import Blog.app.Blog.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentsServiceTest {

    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private PostsRepository postsRepository;
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private CommentsService commentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComments_validComment_savesComment() {
        // Crear un usuario y un post ficticio
        Users user = new Users();
        user.setId(1L);  // Asignar un ID ficticio
        user.setName("Test User");

        Posts post = new Posts();
        post.setId(1L);  // Asignar un ID ficticio
        post.setLabel("Test Post");

        // Crear el comentario y asignarle el usuario y el post
        Comments comment = new Comments();
        comment.setDescription("Nuevo comentario");
        comment.setUser(user);  // Asignar el usuario al comentario
        comment.setPost(post);  // Asignar el post al comentario

        // Simular el guardado del comentario
        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);

        // Llamar al método para guardar el comentario
        Comments savedComment = commentsService.createComments(comment);

        // Verificar que el comentario se haya guardado correctamente
        assertNotNull(savedComment);
        verify(commentsRepository, times(1)).save(comment);
    }


    @Test
    void getAllCommentsbyPost_existingPost_returnsComments() throws Exception {
        Long postId = 1L;
        Posts post = new Posts();
        List<Comments> comments = new ArrayList<>();
        comments.add(new Comments());
        post.setComentarios(comments);

        when(postsRepository.findById(postId)).thenReturn(Optional.of(post));

        List<Comments> retrievedComments = commentsService.getAllCommentsbyPost(postId);

        assertEquals(1, retrievedComments.size());
        verify(postsRepository, times(1)).findById(postId);
    }

    @Test
    void updateComments_existingComment_updatesDescription() {
        Long commentId = 1L;

        // Crear el comentario existente con la descripción original
        Comments existingComment = new Comments();
        existingComment.setDescription("Descripción original");
        existingComment.setId(commentId);

        // Simular que el repositorio encuentra el comentario existente
        when(commentsRepository.findById(commentId)).thenReturn(Optional.of(existingComment));

        // Actualizar la descripción del comentario existente
        existingComment.setDescription("Nueva descripción");

        // Simular el guardado del comentario actualizado
        when(commentsRepository.save(existingComment)).thenReturn(existingComment);

        // Llamar al método de servicio que actualiza el comentario
        Comments result = commentsService.updateComments(commentId, existingComment);

        // Verificar que el comentario ha sido actualizado correctamente
        assertEquals("Nueva descripción", result.getDescription());

        // Verificar que el repositorio fue llamado con el comentario correcto
        verify(commentsRepository, times(1)).findById(commentId);
        verify(commentsRepository, times(1)).save(existingComment);
    }


    @Test
    void deleteComments_existingComment_deletesComment() {
        Long commentId = 1L;

        // Simular que el comentario existe en el repositorio
        Comments existingComment = new Comments();
        existingComment.setId(commentId);
        when(commentsRepository.findById(commentId)).thenReturn(Optional.of(existingComment));

        // Simular la eliminación sin hacer nada
        doNothing().when(commentsRepository).deleteById(commentId);

        // Llamar al método que elimina el comentario
        commentsService.deleteComments(commentId);

        // Verificar que se buscó el comentario en el repositorio
        verify(commentsRepository, times(1)).findById(commentId);

        // Verificar que se llamó al método deleteById
        verify(commentsRepository, times(1)).deleteById(commentId);
    }

    @Test
    void createComments_postOrUserDoesNotExist_throwsException() {
        // Crear un comentario ficticio
        Comments comment = new Comments();

        // Simular que no se encuentra ni el usuario ni el post
        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(postsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción al intentar crear un comentario
        assertThrows(RuntimeException.class, () -> commentsService.createComments(comment));

        // Verificar que no se intentó guardar el comentario
        verify(commentsRepository, never()).save(any(Comments.class));
    }

    @Test
    void getAllCommentsbyPost_nonExistingPost_throwsException() throws Exception {
        Long postId = 1L;

        // Simular que no se encuentra el post
        when(postsRepository.findById(postId)).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción al intentar obtener los comentarios
        assertThrows(RuntimeException.class, () -> commentsService.getAllCommentsbyPost(postId));

        // Verificar que el repositorio de comentarios no fue llamado
        verify(commentsRepository, never()).findAll();
    }

    @Test
    void updateComments_nonExistingComment_throwsException() {
        Long commentId = 1L;

        // Simular que no se encuentra el comentario
        when(commentsRepository.findById(commentId)).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción al intentar actualizar el comentario
        Comments updatedComment = new Comments();
        updatedComment.setDescription("Nueva descripción");
        assertThrows(RuntimeException.class, () -> commentsService.updateComments(commentId, updatedComment));

        // Verificar que no se intentó guardar el comentario
        verify(commentsRepository, never()).save(any(Comments.class));
    }

    @Test
    void deleteComments_nonExistingComment_throwsException() {
        Long commentId = 1L;

        // Simular que no se encuentra el comentario
        when(commentsRepository.findById(commentId)).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción al intentar borrar el comentario
        assertThrows(RuntimeException.class, () -> commentsService.deleteComments(commentId));

        // Verificar que no se intentó borrar ningún comentario
        verify(commentsRepository, never()).deleteById(commentId);
    }
}
