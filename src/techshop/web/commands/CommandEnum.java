package techshop.web.commands;

/**
 * Contains and manages all commands
 */
public enum CommandEnum {

	LOGIN {
		{
			command = new LoginCommand();
		}
	},

	LOGOUT {
		{
			command = new LogoutCommand();
		}
	},

	LANGUAGE {
		{
			command = new LanguageCommand();
		}
	},

	REGISTRATION {
		{
			command = new RegistrationCommand();
		}
	},

	SHOP {
		{
			command = new ShopCommand();
		}
	},

	CATALOG {
		{
			command = new CatalogCommand();
		}
	},

	SELECTION_CATALOG {
		{
			command = new SelectionCatalogCommand();
		}

	},

	PRICE_CATALOG {
		{
			command = new PriceCatalogCommand();
		}
	},

	SORT_CATALOG {
		{
			command = new SortCatalogCommand();
		}
	},

	CART {
		{
			command = new CartCommand();
		}
	},

	ADD_TO_CART {
		{
			command = new AddToCartCommand();
		}
	},

	REG_ORDER {
		{
			command = new RegisterOrderCommand();
		}
	},

	PERSON_ACCOUNT {
		{
			command = new PersonalAccountCommand();
		}
	},

	ADMIN_MENU {
		{
			command = new AdminMenuCommand();
		}
	},

	ALL_CLIENTS {
		{
			command = new AllClientsCommand();
		}
	},

	ALL_ORDERS {
		{
			command = new AllOrdersCommand();
		}
	},

	ALL_GOODS {
		{
			command = new AllGoodsCommand();
		}
	},

	GOODS_SETTING {
		{
			command = new GoodsSettingCommand();
		}
	},

	EDIT_GOODS {
		{
			command = new EditGoodsCommand();
		}
	};

	ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}

}
