package Blog.app.Service;

import Blog.app.Blog.Drafts;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.DraftsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final DraftsRepository draftsRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository, PostsRepository postsRepository, DraftsRepository draftsRepository) {
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
        this.draftsRepository = draftsRepository;
    }

    public Users createUser(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return usersRepository.save(user);
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }


    public Users updateUser(Long id, Users userDetails) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setName(userDetails.getName());
        user.setPassword(userDetails.getPassword());
        user.setFriends(userDetails.getFriends());

        return usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public Posts createPostForUser(Long userId, Posts post) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        post.setUser(user);
        return postsRepository.save(post);
    }

    public Drafts createDraftForUser(Long userId, Drafts draft) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        draft.setUser(user);
        return draftsRepository.save(draft);
    }

    public Users findByName(String name) {
        return usersRepository.findByName(name);
    }
}
