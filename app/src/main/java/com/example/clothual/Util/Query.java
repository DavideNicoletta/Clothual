package com.example.clothual.Util;

public class Query {

    //Query User
    public static final String SELECT_ALL_USER = "SELECT * FROM User";

    public static final String GET_USER_BY_UID = "SELECT * FROM User WHERE id LIKE :Uid";


    //Query Account
    public static final String SELECT_ALL_ACCOUNT = "SELECT * FROM Account";

    public static final String GET_ID = "SELECT id FROM Account WHERE username LIKE :username";


    public static final String GET_EMAIL = "SELECT email FROM Account WHERE id LIKE :idAccount";

    public static final String GET_USERNAME = "SELECT username FROM Account WHERE id LIKE :idAccount";

    public static final String GET_ACCOUNT_BY_ID = "SELECT * FROM Account WHERE id LIKE :idAccount";

    public static final String GET_ID_BY_EMAIL = "SELECT id FROM Account WHERE email LIKE :email";

    public static final String GET_ACCOUNT_BY_USERNAME = "SELECT * FROM Account WHERE username LIKE :username";

    //Query Image
    public static final String SELECT_ALL_IMAGE = "SELECT * FROM Image";


    //Query clothual
    public static final String SELECT_ALL_CLOTHAUL = "SELECT * FROM Clothual";


    public static final String GET_ID_BY_URI = "SELECT ID FROM Image WHERE uri LIKE  :uri";

    public static final String GET_CLOTUAL_BY_ID = "SELECT * FROM Clothual WHERE id LIKE :idClothual";

    //Query Outfir
    public static final String GET_ALL_OUTFIT = "SELECT * FROM Outfit";

    public static final String GET_OUTFIT_BY_DATE = "SELECT * FROM Outfit WHERE date LIKE :date";
}
