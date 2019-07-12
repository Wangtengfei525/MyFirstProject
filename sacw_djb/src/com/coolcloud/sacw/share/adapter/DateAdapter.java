package com.coolcloud.sacw.share.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	private static final String format = "yyyy-MM-dd HH:mm:ss";

	@Override
	public Date unmarshal(String v) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(v);
	}

}
