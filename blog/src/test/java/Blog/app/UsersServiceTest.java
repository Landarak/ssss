package Blog.app;

import Blog.app.Blog.Drafts;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.DraftsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import Blog.app.Service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PostsRepository postsRepository;
    @Mock
    private DraftsRepository draftsRepository;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. createUser
    @Test
    void createUser_validUser_savesUser() {
        Users user = new Users();
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        Users savedUser = usersService.createUser(user);

        assertNotNull(savedUser);
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void createUser_nullUser_throwsException() {
        Users user = null;

        assertThrows(IllegalArgumentException.class, () -> usersService.createUser(user));

        verify(usersRepository, never()).save(any(Users.class));
    }

    // 2. getUserById
    @Test
    void getUserById_existingUser_returnsUser() {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        Users foundUser = usersService.getUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        verify(usersRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_nonExistingUser_throwsException() {
        Long userId = 1L;

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> usersService.getUserById(userId));

        verify(usersRepository, times(1)).findById(userId);
    }

    // 3. updateUser
    @Test
    void updateUser_existingUser_updatesUser() {
        Long userId = 1L;
        Users existingUser = new Users();
        existingUser.setId(userId);
        Users updatedDetails = new Users();
        updatedDetails.setName("Updated Name");

        when(usersRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(usersRepository.save(existingUser)).thenReturn(existingUser);

        Users updatedUser = usersService.updateUser(userId, updatedDetails);

        assertEquals("Updated Name", updatedUser.getName());
        verify(usersRepository, times(1)).findById(userId);
        verify(usersRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_nonExistingUser_throwsException() {
        Long userId = 1L;
        Users updatedDetails = new Users();

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> usersService.updateUser(userId, updatedDetails));

        verify(usersRepository, times(1)).findById(userId);
        verify(usersRepository, never()).save(any(Users.class));
    }

    // 4. deleteUser
    @Test
    void deleteUser_existingUser_deletesUser() {
        Long userId = 1L;

        doNothing().when(usersRepository).deleteById(userId);

        usersService.deleteUser(userId);

        verify(usersRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_nonExistingUser_throwsException() {
        Long userId = 1L;

        doThrow(new IllegalArgumentException("User not found")).when(usersRepository).deleteById(userId);

        assertThrows(IllegalArgumentException.class, () -> usersService.deleteUser(userId));

        verify(usersRepository, times(1)).deleteById(userId);
    }

    // 5. createPostForUser
    @Test
    void createPostForUser_existingUser_createsPost() {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        Posts post = new Posts();

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postsRepository.save(post)).thenReturn(post);

        Posts savedPost = usersService.createPostForUser(userId, post);

        assertEquals(user, savedPost.getUser());
        verify(postsRepository, times(1)).save(post);
    }

    @Test
    void createPostForUser_nonExistingUser_throwsException() {
        Long userId = 1L;
        Posts post = new Posts();

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> usersService.createPostForUser(userId, post));

        verify(postsRepository, never()).save(any(Posts.class));
    }

    // 6. createDraftForUser
    @Test
    void createDraftForUser_existingUser_createsDraft() {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        Drafts draft = new Drafts();

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(draftsRepository.save(draft)).thenReturn(draft);

        Drafts savedDraft = usersService.createDraftForUser(userId, draft);

        assertEquals(user, savedDraft.getUser());
        verify(draftsRepository, times(1)).save(draft);
    }

    @Test
    void createDraftForUser_nonExistingUser_throwsException() {
        Long userId = 1L;
        Drafts draft = new Drafts();

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> usersService.createDraftForUser(userId, draft));

        verify(draftsRepository, never()).save(any(Drafts.class));
    }

    // 7. findByName
    @Test
    void findByName_existingUser_returnsUser() {
        String userName = "John";
        Users user = new Users();
        user.setName(userName);

        when(usersRepository.findByName(userName)).thenReturn(user);

        Users foundUser = usersService.findByName(userName);

        assertNotNull(foundUser);
        assertEquals(userName, foundUser.getName());
        verify(usersRepository, times(1)).findByName(userName);
    }

    @Test
    void findByName_nonExistingUser_returnsNull() {
        String userName = "John";

        when(usersRepository.findByName(userName)).thenReturn(null);

        Users foundUser = usersService.findByName(userName);

        assertNull(foundUser);
        verify(usersRepository, times(1)).findByName(userName);
    }
}
