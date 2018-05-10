package scut218.pisces.dbc;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.Connection;



/**
 * Created by SeHun on 2018-03-17.
 */

public class  SqliteDBConnector  implements DBConnector {
    private static SQLiteDatabase db;
    public static final String sql = " create table Comment("
            + "cId integer primary key,"
            + "mId integer ,"
            + "userId varchar,"
            + "time datetime,"
            + "type integer,"
            + "replyUserId varchar,"
            +"text text);"
            +" create table Moment("
            + "mId integer primary key,"
            + "authorId varchar  ,"
            + "type integer,"
            + "time datetime,"
            + "path varchar,"
            + "location varchar,"
            +"text text);"
            +" create table User("
            + "userId text primary key,"
            + "password varchar  ,"
            + "nickname varchar  ,"
            + "phone char(13)  ,"
            + "gender char(2)  ,"
            + "birthday date,"
            + "school varchar,"
            + "grade integer,"
            + "whatsup varchar,"
            +"photoPath varchar);"
            ;
    static {
        db=SQLiteDatabase.openOrCreateDatabase("/data/data/scut218.pisces.dbc/databases/Pisces.db",null);
        String sql = "select * from Pisces where name='Comment'";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount()==0)
            db.execSQL(sql);
    }
    public Connection getConnection()
    {
        return null;
    }
    public  SQLiteDatabase getDb()
    {
        return db;
    }
    public void connect(){
        db=SQLiteDatabase.openOrCreateDatabase("/data/data/scut218.pisces.dbc/databases/Pisces.db",null);
    }
    public void close(){
        db.close();
    }
}
