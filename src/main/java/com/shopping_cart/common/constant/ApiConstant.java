package com.shopping_cart.common.constant;

public enum ApiConstant {
    API_PATH(Names.API_PATH),
    HEALTHCHECK(Names.HEALTHCHECK),
    ADD_PRODUCT(Names.ADD_PRODUCT),
    PRODUCT_BY_ID(Names.PRODUCT_BY_ID),
    PRODUCTS(Names.PRODUCTS),
    SIGNUP(Names.SIGNUP),
    SIGNIN(Names.SIGNIN);

    public class Names{
        public static final String API_PATH = "/v1/api";
        public static final String HEALTHCHECK = "/healthcheck";
        public static final String ADD_PRODUCT = "/add-product";
        public static final String PRODUCT_BY_ID = "/product/{id}";
        public static final String PRODUCTS = "products";
        public static final String SIGNUP = "/signup";
        public static final String SIGNIN = "/signin";
    }

    private String name;

    private ApiConstant(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
