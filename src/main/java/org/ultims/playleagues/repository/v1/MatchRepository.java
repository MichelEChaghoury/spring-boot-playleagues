package org.ultims.playleagues.repository.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ultims.playleagues.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {

}
