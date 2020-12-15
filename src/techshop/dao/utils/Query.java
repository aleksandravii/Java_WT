package techshop.dao.utils;

/**
 * Contain all queries access to database
 */
public class Query {
	
	public static final String SELECT_ALL_GOODS = "SELECT * FROM goods;";
	public static final String SELECT_GOODS_BY_ID = "SELECT * FROM goods WHERE id = ?;";
	public static final String SELECT_GOODS_NAMES_BY_ORDER_ID = "SELECT goods.name "
			+ "FROM goods "
			+ "INNER JOIN order_items ON goods.id = order_items.goods_id "
			+ "WHERE order_items.order_id = ?;";
	
	public static final String SELECT_GOODS_BY_CATEGORY = 
			  "SELECT g.id, g.name, g.price, g.release_date, g.colour_id, g.group_id, g.manufacturer_id "
			+ "FROM goods g "
			+ "INNER JOIN groups ON g.group_id = groups.id "
			+ "INNER JOIN categories ON groups.category_id = categories.id "
			+ "WHERE category_name = ?;";
	

	public static final String SELECT_GOODS_BY_COUNT = "select * from goods limit ?;";
	
	public static final String SELECT_MAX_PRICE = "SELECT MAX(goods.price) AS price FROM goods;";
	public static final String SELECT_MIN_PRICE = "SELECT MIN(goods.price) AS price FROM goods;";
	
	public static final String SELECT_ALL_GROUPS = "SELECT * FROM groups;";
	public static final String SELECT_GROUP_BY_CATEGORY = "SELECT groups.id, groups.group_name, groups.category_id "
			+ "FROM groups "
			+ "INNER JOIN categories ON groups.category_id = categories.id "
			+ "WHERE categories.category_name = ?;";
	
	public static final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories;";
	public static final String SELECT_CATEGORIES_BY_NAME = "SELECT * FROM categories WHERE category_name = ?";
	
	public static final String SELECT_ALL_MANUFACTURERS = "SELECT * FROM manufacturers;";
	public static final String SELECT_MANUFACTURER_BY_CATEGORY = "SELECT DISTINCT m.id, m.manufacturer_name "
			+ "FROM manufacturers m "
			+ "INNER JOIN goods ON m.id = goods.manufacturer_id "
			+ "INNER JOIN groups ON goods.group_id = groups.id "
			+ "INNER JOIN categories ON groups.category_id = categories.id "
			+ "WHERE categories.category_name = ?;";
	
	public static final String SELECT_ALL_COLOURS = "SELECT * FROM colours;";
	public static final String SELECT_COLOR_BY_NAME = "SELECT * FROM colours WHERE colour_name = ?;";
	public static final String SELECT_COLOUR_BY_CATEGORY = "SELECT DISTINCT col.id, col.colour_name "
			+ "FROM colours col "
			+ "INNER JOIN goods g ON col.id = g.colour_id "
			+ "INNER JOIN groups gr ON g.group_id = gr.id "
			+ "INNER JOIN categories c ON gr.category_id = c.id "
			+ "WHERE c.category_name = ?;";
	
	public static final String SELECT_ALL_ORDERS = "SELECT * FROM orders;";
	public static final String SELECT_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ?;";
	
	public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?;";
	public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
	public static final String SELECT_USER_BY_ROLE_ID = "SELECT * FROM users WHERE role_id = ?;";
	
	public static final String INSERT_ORDER = "INSERT INTO orders (date, status, price, user_id) VALUE (?, ?, ?, ?);";
	public static final String INSERT_ORDER_ITEM = "INSERT INTO order_items (amount, order_id, goods_id) VALUE (?, ?, ?);";
	public static final String INSERT_USER = "INSERT INTO users (login, password, first_name, last_name, email, is_blocked, role_id) "
			+ "VALUE (?, ?, ?, ?, ?, ?, ?);";
	public static final String INSERT_GOODS = "INSERT INTO goods (name, price, release_date, colour_id, group_id, manufacturer_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status = ? WHERE id = ?;";
	public static final String UPDATE_USER_IS_BLOCKED = "UPDATE users SET is_blocked = ? WHERE id = ?;";
	public static final String UPDATE_GOODS = "UPDATE goods "
			+ "SET name = ?, price = ?, release_date = ?, colour_id = ?, group_id = ?, manufacturer_id = ? WHERE id = ?;";
	
	public static final String REMOVE_GOODS = "DELETE FROM goods WHERE id = ?";
	
}
