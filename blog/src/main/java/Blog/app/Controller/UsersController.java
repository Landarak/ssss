package Blog.app.Controller;

import Blog.app.Blog.Drafts;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users createdUser = usersService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = usersService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users userDetails) {
        Users updatedUser = usersService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Crear un post para un usuario
    @PostMapping("/{userId}/posts")
    public ResponseEntity<Posts> createPostForUser(@PathVariable Long userId, @RequestBody Posts post) {
        Posts createdPost = usersService.createPostForUser(userId, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // Crear un borrador para un usuario
    @PostMapping("/{userId}/drafts")
    public ResponseEntity<Drafts> createDraftForUser(@PathVariable Long userId, @RequestBody Drafts draft) {
        Drafts createdDraft = usersService.createDraftForUser(userId, draft);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDraft);
    }

    @PostMapping("/login")
    public ResponseEntity<Users> loginUser(@RequestBody Users loginData) {
        Users user = usersService.findByName(loginData.getName());
        if (user != null && user.getPassword().equals(loginData.getPassword())) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Error 401 si no coincide
        }
    }
}
