package ppp.simt.service.core;

import java.util.List;

import ppp.simt.entity.core.Category;


public interface CategoryService {
	public List<Category> findAll();
	public void createCategory(Category category);
	public void updateCategory(Category category);
	public Category findCategoryById(long categoryId);

}
