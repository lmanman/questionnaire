package com.visionet.letsdesk.app.common.mobilepush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MobilePushMsgJob {
	private static Logger logger = LoggerFactory.getLogger(MobilePushMsgJob.class);

	public void init() {
		logger.debug("MobilePush.push start!");
		
		MobilePush.push();
	}

}
