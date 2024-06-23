package com.shopping_cart.common.constant;

public enum ApiConstant {
    API_PATH(Names.API_PATH),
    HEALTHCHECK(Names.HEALTHCHECK),
    ADD_PRODUCT(Names.ADD_PRODUCT),
    PRODUCT_BY_ID(Names.PRODUCT_BY_ID),
    PRODUCTS(Names.PRODUCTS),
    SIGNUP(Names.SIGNUP),
    SIGNIN(Names.SIGNIN),
    DONUTS(Names.DONUTS),
    DONUT_BY_ID(Names.DONUT_BY_ID),
    TOPPING_BY_DONUT_ID(Names.TOPPINGS_BY_DONUT_ID),
    TOPPING_BY_ID(Names.TOPPING_BY_ID);

    public class Names{
        public static final String API_PATH = "/v1/api";
        public static final String HEALTHCHECK = "/healthcheck";
        public static final String ADD_PRODUCT = "/add-product";
        public static final String PRODUCT_BY_ID = "/product/{id}";
        public static final String PRODUCTS = "/products";
        public static final String SIGNUP = "/signup";
        public static final String SIGNIN = "/signin";
        public static final String DONUTS = "/donuts";
        public static final String DONUT_BY_ID = "/donut/{id}";
        public static final String TOPPINGS_BY_DONUT_ID = "/{donutId}/toppings";
        public static final String TOPPING_BY_ID = "/{donutId}/topping/{toppingId}";
    }

    private String url;

    private ApiConstant(String name)
    {
        this.url = url;
    }

    public String getName()
    {
        return url;
    }
}
