package com.epsmart.wzcc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchBean;
import com.epsmart.wzcc.db.table.AppDetailTable;
import com.epsmart.wzcc.db.table.AppHeadTable;
import com.epsmart.wzcc.db.table.LeaveDetailTable;
import com.epsmart.wzcc.db.table.LeaveHeadTable;
import com.epsmart.wzcc.db.table.SimpleData;
import com.epsmart.wzcc.db.table.SubmitDateTable;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper<E> extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "wzcc.db";///mnt/sdcard/ss/wzcc.db
	private static final int DATABASE_VERSION = 1;
	// 泛型表示DAO的操作类

//	private Map<String, Dao<E, Integer>> daos = new HashMap<String, Dao<E, Integer>>();

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, AppHeadTable.class);
			TableUtils.createTable(connectionSource, AppDetailTable.class);
			TableUtils.createTable(connectionSource, SimpleData.class);
			TableUtils.createTable(connectionSource, LeaveHeadTable.class);
			TableUtils.createTable(connectionSource, LeaveDetailTable.class);
			TableUtils.createTable(connectionSource, SubmitDateTable.class);
			TableUtils.createTable(connectionSource, BatchBean.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, AppHeadTable.class, true);
			TableUtils.dropTable(connectionSource, AppDetailTable.class, true);
			TableUtils.dropTable(connectionSource, SimpleData.class, true);
			TableUtils.dropTable(connectionSource, LeaveHeadTable.class, true);
			TableUtils.dropTable(connectionSource, LeaveDetailTable.class, true);
			TableUtils.dropTable(connectionSource, SubmitDateTable.class, true);
			TableUtils.dropTable(connectionSource, BatchBean.class, true);
			onCreate(db, connectionSource);
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * DatabaseHelper 单例的实现
	 */
	private static DatabaseHelper<?> instance;

	public static synchronized DatabaseHelper<?> getHelper(Context context) {
		context = context.getApplicationContext();
		if (instance == null) {
			synchronized (DatabaseHelper.class) {
				if (instance == null)
					instance = new DatabaseHelper(context);
			}
		}
		return instance;
	}

//	@SuppressWarnings("unchecked")
//	public synchronized Dao<E, Integer> getDao(Class clazz) throws SQLException {
//		Dao<E, Integer> dao = null;
//		String className = clazz.getSimpleName();
//
//		if (daos.containsKey(className)) {
//			dao = daos.get(className);
//		}
//		if (dao == null) {
//			dao = super.getDao(clazz);
//			daos.put(className, dao);
//		}
//		return dao;
//	}

	@Override
	public void close() {
		super.close();
	}
}
