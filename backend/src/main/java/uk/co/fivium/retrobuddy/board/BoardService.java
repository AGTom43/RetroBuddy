package uk.co.fivium.retrobuddy.board;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private final BoardRepository boardRepository;

  @Autowired
  public BoardService(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public Board getBoardFromId(UUID boardId) {
    return boardRepository.findById(boardId).orElseThrow();
  }

  public List<JsonBoard> getAllBoards() {
    return boardRepository.findAll().stream()
        .map(board -> new JsonBoard(board.getName(), board.getDefaultColour(), board.getId().toString()))
        .toList();
  }

  @Transactional
  public UUID createBoard() {
    var numberOfBoardsWithRetroBuddyAsName = boardRepository.findAllByNameContaining("RetroBuddy").size() + 1;

    var newBoard = new Board(UUID.randomUUID());
    newBoard.setDefaultColour("#f3c446");
    newBoard.setName("RetroBuddy " + numberOfBoardsWithRetroBuddyAsName);
    return boardRepository.save(newBoard).getId();
  }

  @Transactional
  public UUID editBoard(UUID boardId, JsonBoard board) {
    var boardToEdit = getBoardFromId(boardId);
    boardToEdit.setDefaultColour(board.defaultColour());
    boardToEdit.setName(board.name() == null ? boardToEdit.getName() : board.name());
    return boardRepository.save(boardToEdit).getId();
  }
}
