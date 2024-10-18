package Blog.app.Controller;

import Blog.app.Blog.Drafts;
import Blog.app.Service.DraftsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drafts")
public class DraftsController {

    private final DraftsService draftsService;

    @Autowired
    public DraftsController(DraftsService draftsService) {
        this.draftsService = draftsService;
    }

    // Crear un nuevo borrador
    @PostMapping
    public ResponseEntity<Drafts> createDraft(@RequestBody Drafts draft) {
        Drafts createdDraft = draftsService.createDraft(draft);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDraft);
    }

    // Obtener todos los borradores de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Drafts>> getAllDraftsByUser(@PathVariable Long userId) {
        List<Drafts> drafts = draftsService.getAllDraftsbyUser(userId);
        return ResponseEntity.ok(drafts);
    }

    // Actualizar un borrador
    @PutMapping("/{id}")
    public ResponseEntity<Drafts> updateDraft(@PathVariable Long id, @RequestBody Drafts draftDetails) {
        Drafts updatedDraft = draftsService.updateDraft(id, draftDetails);
        return ResponseEntity.ok(updatedDraft);
    }

    // Eliminar un borrador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDraft(@PathVariable Long id) {
        draftsService.deleteDraft(id);
        return ResponseEntity.noContent().build();
    }
}
