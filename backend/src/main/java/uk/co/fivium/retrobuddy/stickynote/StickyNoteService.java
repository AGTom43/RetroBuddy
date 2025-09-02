package uk.co.fivium.retrobuddy.stickynote;

import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.retrobuddy.board.BoardService;

@Service
public class StickyNoteService {

  private final StickyNoteRepository stickyNoteRepository;
  private final BoardService boardService;

  @Autowired
  public StickyNoteService(StickyNoteRepository stickyNoteRepository,
                           BoardService boardService) {
    this.stickyNoteRepository = stickyNoteRepository;
    this.boardService = boardService;
  }

  public JsonStickyNoteList getStickyNotes(UUID boardId) {
    var stickyNotes = stickyNoteRepository.findAllByBoard_Id(boardId);
    var jsonStickyNotes = stickyNotes.stream()
        .map(stickyNote -> new JsonStickyNote(stickyNote.getId(), stickyNote.getTitle(), stickyNote.getBody(),
            stickyNote.getPositionX(), stickyNote.getPositionY(), stickyNote.getColour()))
        .toList();
    return new JsonStickyNoteList(jsonStickyNotes);
  }

  public UUID createStickyNote(UUID boardId, JsonCreateStickyNote stickyNote) {
    var board = boardService.getBoardFromId(boardId);

    var newStickyNote = new StickyNote(UUID.randomUUID())
        .setBody(stickyNote.body())
        .setTitle(stickyNote.title())
        .setPositionX(stickyNote.positionX())
        .setPositionY(stickyNote.positionY())
        .setColour("#f3c446")
        .setBoard(board);

    stickyNoteRepository.save(newStickyNote);

    return newStickyNote.getId();
  }

  @Transactional
  public void deleteStickyNote(UUID stickyNoteId, UUID boardId) {
    stickyNoteRepository.deleteByIdAndBoard_Id(stickyNoteId, boardId);
  }

  @Transactional
  public void editStickyNote(UUID stickyNoteId, UUID boardId, JsonCreateStickyNote stickyNote) {
    var editedStickyNote = stickyNoteRepository.findStickyNoteByIdAndBoard_Id(stickyNoteId, boardId);

    editedStickyNote
        .setTitle(stickyNote.title())
        .setBody(stickyNote.body())
        .setPositionX(stickyNote.positionX())
        .setPositionY(stickyNote.positionY())
        .setColour(stickyNote.colour());

    stickyNoteRepository.save(editedStickyNote);
  }
}
