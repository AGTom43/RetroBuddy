package uk.co.fivium.retrobuddy.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Board {

  @Id
  private UUID id;

  private String name;

  private String defaultColour;

  public Board(UUID id) {
    this.id = id;
  }

  public Board() {
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDefaultColour() {
    return defaultColour;
  }

  public Board setDefaultColour(String defaultColour) {
    this.defaultColour = defaultColour;
    return this;
  }
}
