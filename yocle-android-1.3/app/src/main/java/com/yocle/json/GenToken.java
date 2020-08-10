package com.yocle.json;

public class GenToken
{
	public Integer id;
	public String utm_source;
	public String utm_medium;
	public String utm_campaign;
	public String item_name;
	public String share_message;
	public String share_uri;
	public String error;

	public GenToken(Integer id, String source, String medium, String campaign, String item_name, String share_message, String share_uri, String error) {
		this.id = id;
		this.utm_source = source;
		this.utm_medium = medium;
		this.utm_campaign = campaign;
		this.item_name = item_name;
		this.share_message = share_message;
		this.share_uri = share_uri;
		this.error = error;
	}

}
