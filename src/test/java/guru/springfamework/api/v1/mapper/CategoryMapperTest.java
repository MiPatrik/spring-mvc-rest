package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

	private static final String NAME = "Joe";
	private static final Long ID = 1L;

	private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	public void categoryToCategoryDTO() {
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);

		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

		assertEquals(ID, categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}
