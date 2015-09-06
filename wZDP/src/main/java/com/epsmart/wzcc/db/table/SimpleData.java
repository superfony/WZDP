package com.epsmart.wzcc.db.table;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

/**
 * 测试表
 * @author fony
 *
 */
public class SimpleData {
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField(index = true)
	public String string;
	@DatabaseField
	public long millis;
	@DatabaseField
	Date date;
	@DatabaseField
	public boolean even;

	SimpleData() {

	}

	public SimpleData(long millis) {
		this.date = new Date(millis);
		this.string = (millis % 1000) + "ms";
		this.millis = millis;
		this.even = ((millis % 2) == 0);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("string=").append(string);
		sb.append(", ").append("millis=").append(millis);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.S");
		sb.append(", ").append("date=").append(dateFormatter.format(date));
		sb.append(", ").append("even=").append(even);
		return sb.toString();
	}
}
