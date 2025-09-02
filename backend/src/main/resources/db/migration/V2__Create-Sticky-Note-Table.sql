CREATE TABLE sticky_note (
    id UUID PRIMARY KEY,
    title TEXT NOT NULL,
    body TEXT,
    board_id UUID NOT NULL REFERENCES board (id),
    position_x INTEGER NOT NULL,
    position_y INTEGER NOT NULL
);

CREATE INDEX sticky_note_board_id_idx ON sticky_note (board_id);
