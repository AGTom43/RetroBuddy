package uk.co.fivium.retrobuddy.board;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

  private final BoardService boardService;

  @Autowired
  public BoardRestController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/{boardId}/get")
  public JsonBoard getBoard(@PathVariable UUID boardId) {
    var board = boardService.getBoardFromId(boardId);
    return new JsonBoard(board.getName(), board.getDefaultColour(), boardId.toString());
  }

  @GetMapping
  public List<JsonBoard> getAllBoards() {
    return boardService.getAllBoards();
  }

  @PostMapping("/create")
  public JsonCreateBoardResponse createBoard(){
    UUID boardId = boardService.createBoard();
    return new JsonCreateBoardResponse(boardId);
  }

  @PutMapping("/{boardId}/edit")
  public JsonCreateBoardResponse editBoard(@PathVariable UUID boardId,
                                           @RequestBody JsonBoard jsonBoard){
    UUID edittedBoardId = boardService.editBoard(boardId, jsonBoard);
    return new JsonCreateBoardResponse(edittedBoardId);
  }
}
