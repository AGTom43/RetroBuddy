package uk.co.fivium.retrobuddy.stickynote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import uk.co.fivium.retrobuddy.board.Board;

@Entity
public class StickyNote {

  @Id
  private UUID id;

  private String title;

  private String body;

  @JoinColumn(name = "board_id")
  @ManyToOne
  private Board board;

  @Column(name = "position_x")
  private Long positionX;

  @Column(name = "position_y")
  private Long positionY;

  private String colour;


  public StickyNote(UUID id) {
    this.id = id;
  }

  public StickyNote() {
  }

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public StickyNote setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getBody() {
    return body;
  }

  public StickyNote setBody(String body) {
    this.body = body;
    return this;
  }

  public Board getBoard() {
    return board;
  }

  public StickyNote setBoard(Board board) {
    this.board = board;
    return this;
  }

  public Long getPositionX() {
    return positionX;
  }

  public StickyNote setPositionX(Long positionX) {
    this.positionX = positionX;
    return this;
  }

  public Long getPositionY() {
    return positionY;
  }

  public StickyNote setPositionY(Long positionY) {
    this.positionY = positionY;
    return this;
  }

  public String getColour() {
    return colour;
  }

  public StickyNote setColour(String colour) {
    this.colour = colour;
    return this;
  }
}
