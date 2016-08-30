package com.visionet.letsdesk.app.base.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JsonDateSerializer extends JsonSerializer<Date> {

	private final SimpleDateFormat datetimeFormat = new SimpleDateFormat(DateUtil.YMD_FULL);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.YMD1);

	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

        if(date.getHours() == 0 && date.getMinutes() == 0 && date.getSeconds() == 0){
			gen.writeString(dateFormat.format(date));
		}else{
			gen.writeString(datetimeFormat.format(date));
		}

	}

}