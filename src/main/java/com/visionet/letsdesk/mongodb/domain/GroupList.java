package com.visionet.letsdesk.mongodb.domain;

public class GroupList {
	
	private String url;
    private String date;

	private GroupResult consumeTime;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GroupResult getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(GroupResult consumeTime) {
		this.consumeTime = consumeTime;
	}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
	public String toString() {
		return "ActionLog [url=" + url + ", userName=" + consumeTime.getAvg()
				+ "," + consumeTime.getCount() + "," + consumeTime.getMax()
				+ "," + consumeTime.getMin() + "," + consumeTime.getSum() + "]";
	}

	
}
