package com.invest.json;

public class ViewItemError
{
	public int errnum;
	public int iid;
	public String thumbnail;
	public String err;

	public int rprice;
	public String rdesc;
	public int m1price;
	public int m3price;
	public int m6price;
	public int m12price;


	public ViewItemError(int errnum, int iid, String thumbnail, String err) {
		this.errnum = errnum;
		this.iid = iid;
		this.thumbnail = thumbnail;
		this.err = err;
		this.rdesc = "";
		this.rprice = 0;
		this.m1price = 0;
		this.m3price = 0;
		this.m6price = 0;
		this.m12price = 0;

	}

	public ViewItemError(int errnum, int iid, String rdesc, int rprice, int m1price, int m3price, int m6price, int m12price) {
		this.thumbnail = "";
		this.err = "";
		this.errnum = errnum;
		this.iid = iid;
		this.rdesc = rdesc;
		this.rprice = rprice;
		this.m1price = m1price;
		this.m3price = m3price;
		this.m6price = m6price;
		this.m12price = m12price;
	}


}