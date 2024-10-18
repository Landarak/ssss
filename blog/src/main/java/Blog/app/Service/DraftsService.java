package Blog.app.Service;

import Blog.app.Blog.Drafts;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.DraftsRepository;
import Blog.app.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftsService {
    private final DraftsRepository draftsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public DraftsService(DraftsRepository draftsRepository, UsersRepository usersRepository) {
        this.draftsRepository = draftsRepository;
        this.usersRepository = usersRepository;
    }

    public Drafts createDraft(Drafts draft) {
        if (draft.getUser() == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        return draftsRepository.save(draft);
    }


    public List<Drafts> getAllDraftsbyUser(Long user_id) {
        try {
            Users user = usersRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("User not found"));
            return user.getDrafts();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Drafts updateDraft(Long id, Drafts draftDetails) {
        Drafts draft = draftsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Draft not found"));

        draft.setLabel(draftDetails.getLabel());
        draft.setUser(draftDetails.getUser());

        return draftsRepository.save(draft);
    }


    public void deleteDraft(Long id) {
        draftsRepository.deleteById(id);
    }

}
