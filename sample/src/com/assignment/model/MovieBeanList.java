package com.assignment.model;


public class MovieBeanList {
	String desc,adult,release_date,image,id;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public MovieBeanList(String desc, String adult, String release_date,String image,String id) {
		super();
		this.desc = desc;
		this.adult = adult;
		this.release_date = release_date;
		this.image=image;
		this.id=id;
	}
    
	
	

}
