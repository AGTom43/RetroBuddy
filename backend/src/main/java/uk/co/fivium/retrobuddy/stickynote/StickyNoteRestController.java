package uk.co.fivium.retrobuddy.stickynote;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board/{boardId}/sticky-note")
public class StickyNoteRestController {

  private final StickyNoteService stickyNoteService;
  private final SimpMessagingTemplate template;

  @Autowired
  public StickyNoteRestController(StickyNoteService stickyNoteService, SimpMessagingTemplate template) {
    this.stickyNoteService = stickyNoteService;
    this.template = template;
  }

  @GetMapping
  public JsonStickyNoteList getStickyNotes(@PathVariable UUID boardId) {
    return stickyNoteService.getStickyNotes(boardId);
  }

  @PostMapping("/create")
  public ResponseEntity<?> createStickyNote(@PathVariable String boardId, @RequestBody JsonCreateStickyNote stickyNote) {
    var stickyNoteId = stickyNoteService.createStickyNote(UUID.fromString(boardId), stickyNote);

    template.convertAndSend("/topic/board/%s/sticky-note/created".formatted(boardId),
        new JsonCreateStickyNoteResponse(stickyNoteId, stickyNote.title(), stickyNote.body(), stickyNote.positionX(),
            stickyNote.positionY(), stickyNote.colour()));

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{stickyNoteId}/delete")
  public ResponseEntity<?> deleteStickyNote(@PathVariable String boardId, @PathVariable String stickyNoteId) {
    stickyNoteService.deleteStickyNote(UUID.fromString(stickyNoteId), UUID.fromString(boardId));

    template.convertAndSend("/topic/board/%s/sticky-note/deleted".formatted(boardId),
        new JsonDeleteStickyNoteResponse(UUID.fromString(stickyNoteId)));

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{stickyNoteId}/update")
  public ResponseEntity<?> editStickyNote(@PathVariable String boardId, @PathVariable String stickyNoteId,
                                          @RequestBody JsonCreateStickyNote stickyNote) {
    stickyNoteService.editStickyNote(UUID.fromString(stickyNoteId), UUID.fromString(boardId), stickyNote);

    template.convertAndSend("/topic/board/%s/sticky-note/updated".formatted(boardId),
        new JsonCreateStickyNoteResponse(UUID.fromString(stickyNoteId), stickyNote.title(), stickyNote.body(),
            stickyNote.positionX(), stickyNote.positionY(), stickyNote.colour()));

    return ResponseEntity.ok().build();
  }
}
