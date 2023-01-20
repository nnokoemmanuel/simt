package ppp.simt.service.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Category;
import ppp.simt.repository.core.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	
	
	@Override
	public void createCategory(Category category) {
		categoryRepository.save(category);
		
	}

	@Override
	public void updateCategory(Category category) {
		categoryRepository.saveAndFlush(category);
		
	}
	
	@Override
	public Category findCategoryById(long categoryId) {
		
		return categoryRepository.findById(categoryId);
	}
}
