package ppp.simt.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Authority;


@Repository("authorityRepository")
public interface AuthorityRepository extends JpaRepository <Authority, Integer>{

	Authority findById(int authorityId);

    Authority findByPosition(String position);
}