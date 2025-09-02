package uk.co.fivium.retrobuddy.stickynote;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickyNoteRepository extends CrudRepository<StickyNote, Integer> {
  List<StickyNote> findAllByBoard_Id(UUID boardId);

  StickyNote findStickyNoteByIdAndBoard_Id(UUID stickyNoteId, UUID boardId);

  void deleteByIdAndBoard_Id(UUID stickyNoteId, UUID boardId);
}
