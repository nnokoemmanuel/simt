package ppp.simt.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Category;



@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository <Category, Integer>{
	
	Category findById(long categoryId);

}