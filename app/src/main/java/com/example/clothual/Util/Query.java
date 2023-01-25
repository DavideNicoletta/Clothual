package com.example.clothual.Util;

import com.example.clothual.R;

public class Query {

    //Query User
    public static final String SELECT_ALL_USER = "SELECT * FROM User";

    //Query Account
    public static final String SELECT_ALL_ACCOUNT = "SELECT * FROM Account";

    public static final String GET_ID = "SELECT id FROM Account WHERE username LIKE :username";

    public static final String GET_PASSWORD = "SELECT password FROM Account WHERE id LIKE :idAccount";

    public static final String GET_EMAIL = "SELECT email FROM Account WHERE id LIKE :idAccount";

    public static final String GET_USERNAME = "SELECT username FROM Account WHERE id LIKE :idAccount";

    public static final String GET_ACCOUNT_BY_ID = "SELECT * FROM Account WHERE id LIKE :idAccount";

    //Query Image
    public static final String SELECT_ALL_IMAGE = "SELECT * FROM Image";


    //Query clothual
    public static final String SELECT_ALL_CLOTHAUL = "SELECT * FROM Clothual";

    public static final String SELECT_ALL_SHOES = "SELECT * FROM Clothual WHERE type LIKE " + (char)34 + R.string.shoes + (char)34;

    public static final String GET_ID_BY_URI = "SELECT ID FROM Image WHERE uri LIKE  :uri";

    public static final String GET_CLOTUAL_BY_ID = "SELECT * FROM Clothual WHERE id LIKE :idClothual";

    //Query Outfir
    public static final String GET_ALL_OUTFIT = "SELECT * FROM Outfit";

    public static final String GET_OUTFIT_BY_DATE = "SELECT * FROM Outfit WHERE date LIKE :date";
}
