package com.coolcloud.sacw.share.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 袁永祥
 *
 * @date 2017年8月22日 上午11:49:32
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Share {

	private Header header;

	@XmlElement(name = "case")
	private Case caze;

	@XmlElementWrapper(name = "persons")
	@XmlElement(name = "person")
	private List<Person> persons;

	@XmlElementWrapper(name = "goods")
	@XmlElement(name = "good")
	private List<Good> goods;

	@XmlElementWrapper(name = "files")
	@XmlElement(name = "file")
	private List<File> files;

	@XmlElementWrapper(name = "photos")
	@XmlElement(name = "photo")
	private List<Photo> photos;

	@XmlElementWrapper(name = "hz")
	@XmlElement(name = "good")
	private List<Receipt> receipts;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Case getCaze() {
		return caze;
	}

	public void setCaze(Case caze) {
		this.caze = caze;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Good> getGoods() {
		return goods;
	}

	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public List<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}

	
	
	
	
	
	
}
