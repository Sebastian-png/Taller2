package com.edu.basededatos.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import android.widget.Toast;

import com.edu.basededatos.MainActivity;
import com.edu.basededatos.classes.User;

public class ManageDB extends  SQLiteOpenHelper{

    private static final String DATA_BASE_USERS = "dbUsers";
    private static final int VERSION = 1;
    private static final String TABLE_USERS="users";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_USERS+"(usu_document INTEGER PRIMARY KEY,"+
            "usu_user varchar(35) NOT NULL, usu_names varchar(100) NOT NULL, usu_last_names varchar(100) NOT NULL,"+
            "usu_pass varchar(20) NOT NULL,"+"usu_status INTEGER (1) NOT NULL);";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+ TABLE_USERS;


    public ManageDB(Context context) { super(context, DATA_BASE_USERS, null, VERSION); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.i("DATABASE", "se creó la tabla"+CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long actualizar(User user, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        long error = -1;

        ContentValues values = new ContentValues();
        values.put("usu_document", user.getDocument());
        values.put("usu_user", user.getUser());
        values.put("usu_names", user.getNames());
        values.put("usu_last_names", user.getLastNames());
        values.put("usu_pass", user.getPass());
        values.put("usu_status", user.getStatus());

        try {
            error = db.update(TABLE_USERS, values, "usu_document = ?", new String[] { String.valueOf(user.getDocument()) });

            if (error != -1) {
                Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No se pudo actualizar el usuario", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "No se pudo actualizar el usuario", Toast.LENGTH_SHORT).show();
        }

        return error;
    }

}
