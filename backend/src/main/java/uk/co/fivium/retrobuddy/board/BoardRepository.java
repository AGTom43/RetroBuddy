package uk.co.fivium.retrobuddy.board;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends ListCrudRepository<Board, UUID> {

  List<Board> findAllByNameContaining(String name);
}
