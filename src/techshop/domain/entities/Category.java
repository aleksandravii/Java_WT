package techshop.domain.entities;

/**
 * Category entity
 */
public class Category extends Entity {

	private static final long serialVersionUID = -1769222510270798921L;
	
	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + "]";
	}
	
}
