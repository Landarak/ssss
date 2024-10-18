package Blog.app;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import Blog.app.Service.CommentsService;
import Blog.app.Service.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostsServiceTest {

    @Mock
    private PostsRepository postsRepository;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private CommentsService commentsService;

    @InjectMocks
    private PostsService postsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. createPosts
    @Test
    void createPosts_validPost_savesPost() {
        Users user = new Users();
        user.setId(1L);
        Posts post = new Posts();
        post.setUser(user);

        when(postsRepository.save(any(Posts.class))).thenReturn(post);

        Posts savedPost = postsService.createPosts(post);

        assertNotNull(savedPost);
        verify(postsRepository, times(1)).save(post);
    }

    @Test
    void createPosts_noUser_throwsException() {
        Posts post = new Posts(); // Sin usuario

        assertThrows(IllegalArgumentException.class, () -> postsService.createPosts(post));

        verify(postsRepository, never()).save(any(Posts.class));
    }

    // 2. getAllPosts
    @Test
    void getAllPosts_returnsAllPosts() {
        List<Posts> postsList = new ArrayList<>();
        postsList.add(new Posts());
        postsList.add(new Posts());

        when(postsRepository.findAll()).thenReturn(postsList);

        List<Posts> result = postsService.getAllPosts();

        assertEquals(2, result.size());
        verify(postsRepository, times(1)).findAll();
    }

    // 3. updatePosts
    @Test
    void updatePosts_existingPost_updatesPost() {
        Long postId = 1L;
        Posts existingPost = new Posts();
        existingPost.setId(postId);
        Posts updatedPost = new Posts();
        updatedPost.setLabel("Updated Label");
        updatedPost.setDescription("Updated Description");

        when(postsRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postsRepository.save(existingPost)).thenReturn(existingPost);

        Posts result = postsService.updatePosts(postId, updatedPost);

        assertEquals("Updated Label", result.getLabel());
        assertEquals("Updated Description", result.getDescription());
        verify(postsRepository, times(1)).findById(postId);
        verify(postsRepository, times(1)).save(existingPost);
    }

    // 4. addCommentToPost
    @Test
    void addCommentToPost_existingPost_addsComment() {
        Long postId = 1L;
        Posts post = new Posts();
        post.setId(postId);
        Comments comment = new Comments();

        when(postsRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);

        Comments savedComment = postsService.addCommentToPost(postId, comment);

        assertEquals(post, savedComment.getPost());
        verify(postsRepository, times(1)).findById(postId);
        verify(commentsRepository, times(1)).save(comment);
    }

    @Test
    void addCommentToPost_nonExistingPost_throwsException() {
        Long postId = 1L;
        Comments comment = new Comments();

        when(postsRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> postsService.addCommentToPost(postId, comment));

        verify(commentsRepository, never()).save(any(Comments.class));
    }

    // 5. getAllPostsbyUser
    @Test
    void getAllPostsbyUser_existingUser_returnsPosts() {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        List<Posts> posts = new ArrayList<>();
        posts.add(new Posts());
        user.setPosts(posts);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        List<Posts> result = postsService.getAllPostsbyUser(userId);

        assertEquals(1, result.size());
        verify(usersRepository, times(1)).findById(userId);
    }

    @Test
    void getAllPostsbyUser_nonExistingUser_throwsException() {
        Long userId = 1L;

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> postsService.getAllPostsbyUser(userId));

        verify(usersRepository, times(1)).findById(userId);
    }

    // 6. deletePosts
    @Test
    void deletePosts_existingPost_deletesPost() {
        Long postId = 1L;

        doNothing().when(postsRepository).deleteById(postId);

        postsService.deletePosts(postId);

        verify(postsRepository, times(1)).deleteById(postId);
    }

    @Test
    void deletePosts_nonExistingPost_throwsException() {
        Long postId = 1L;

        doThrow(new RuntimeException("Post not found")).when(postsRepository).deleteById(postId);

        assertThrows(RuntimeException.class, () -> postsService.deletePosts(postId));

        verify(postsRepository, times(1)).deleteById(postId);
    }
}
