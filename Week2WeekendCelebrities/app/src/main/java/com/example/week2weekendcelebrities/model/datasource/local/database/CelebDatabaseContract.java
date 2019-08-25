package com.example.week2weekendcelebrities.model.datasource.local.database;

public class CelebDatabaseContract {
    //  private String name, height, born, nationality;
    //    int favorite;
    public static final String DATABASE_NAME = "celebrity_db";
    public static final String TABLE_NAME = "celebrity_table";
    public static final int DATABASE_VERSION = 1;
    public static final String COL_NAME = "name";
    public static final String COL_HEIGHT = "height";
    public static final String COL_BORN = "born";
    public static final String COL_NATIONALITY = "nationality";
    public static final String COL_FAVORITE = "favorite";

    //Create Table Query
    public static final String QUERY_CREATE_TABLE =
            String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY NOT NULL, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                    TABLE_NAME, COL_NAME, COL_HEIGHT, COL_BORN, COL_NATIONALITY, COL_FAVORITE);

    //Select All Query
    public static final String QUERY_SELECT_ALL = String.format("SELECT * FROM %s", TABLE_NAME);

    //Select All favorites
    public static String QUERY_SELECT_FAVORITES = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, COL_FAVORITE, 1);

    //Select by ID
    public static String QUERY_SELECT_BY_NAME(String name) {
        return String.format("SELECT * FROM %s WHERE %s = \'%s\'", TABLE_NAME, COL_NAME, name);
    }

    //Drop Table Query
    public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);
}
