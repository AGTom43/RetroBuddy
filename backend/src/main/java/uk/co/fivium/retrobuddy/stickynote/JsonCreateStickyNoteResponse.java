package uk.co.fivium.retrobuddy.stickynote;

import java.util.UUID;

public record JsonCreateStickyNoteResponse(UUID id, String title, String body, Long positionX, Long positionY, String colour) {
}
