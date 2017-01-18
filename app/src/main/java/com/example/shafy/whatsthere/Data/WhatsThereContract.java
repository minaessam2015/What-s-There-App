package com.example.shafy.whatsthere.Data;

import android.provider.BaseColumns;

/**
 * Created by mina essam on 16-Dec-16.
 */
public final class WhatsThereContract {

    public static class NewsTable implements BaseColumns {
        public final static String ID=BaseColumns._ID;
        public static final String TABLE_NAME="favouriteNews";
        public static final String TITLE_COLUMN="title";
        public static final String AUTHOR_COLUMN ="author";
        public static final String DESCRIPTION_COLUMN="description";
        public static final String DATE_COLUMN="date";
        public static final String NEWS_URL="URL";
        public static final String IMAGE_URL ="imageURL";

    }
    public static final String INTEGER="INTEGER";
    public static final String TEXT="TEXT";
    public static final String PRIMARY_KEY="PRIMARY KEY";
    public static final String AUTO_INCREMENT="AUTOINCREMENT";
    public static final String NOT_NULL="NOT NULL";
    public static final String CREATE_TABLE ="CREATE TABLE "+ NewsTable.TABLE_NAME+" ("+ NewsTable.ID+" "
            +INTEGER
            +" "+PRIMARY_KEY+" "+AUTO_INCREMENT +", " + NewsTable.AUTHOR_COLUMN +
            " "+TEXT+" "+NOT_NULL+","+ NewsTable.TITLE_COLUMN+" "+TEXT+" , "+NewsTable.DESCRIPTION_COLUMN+
            " "+TEXT+" "+NOT_NULL+" , "+  NewsTable.DATE_COLUMN+" "
            +TEXT+" "+NOT_NULL+" , "+NewsTable.NEWS_URL+" "+TEXT+" "+NOT_NULL+" , "+NewsTable.IMAGE_URL
            +" "+TEXT+" "+NOT_NULL
          +");";


    public static final String DELETE_TABLE="DROP TABLE IF EXIST "+ NewsTable.TABLE_NAME+" ;" ;
    public static final String SELECT_ALL="SELECT * FROM "+ NewsTable.TABLE_NAME +" ;";
    public static final String SELECT_ROW_BY_NEWS_URL ="SELECT * FROM "+NewsTable.TABLE_NAME+" WHERE "+NewsTable.NEWS_URL;

}
