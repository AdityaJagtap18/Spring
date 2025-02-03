package com.springcrud.CurdOparation.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="customer_tbl")
@NamedQuery(name="CustomerTbl.findAll", query="SELECT m FROM CustomerTbl m")
public class CustomerTbl {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="description")
	private String description;
    
	@Column(name="contact_person")
	private String contactPerson;
	
	@Column(name="contact_mail")
	private String contactMail;
	
	@Column(name="contact_phone")
	private String contactPhone;
    
	@Column(name="location")
	private String location;
    
	@Column(name="default_currency")
	private String defaultCurrency;
    
	@Column(name="created_by")
	private String createdBy;
    
	@Column(name="creation_date")
	private Date creationDate;
    
	@Column(name="last_updated_by")
	private String lastUpdatedBy;
    
	@Column(name="last_update_date")
	private Date lastUpdateDate;
	
	@Column(name="customer_code")
	private String customerCode;
    
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Lob
	@Column(name="logo")
	private byte[] logo;

	@JsonIgnore
	@OneToMany(mappedBy="customerIdTbl")
	private List<UserTbl> userTblList;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public List<UserTbl> getUserTblList() {
		return userTblList;
	}

	public void setUserTblList(List<UserTbl> userTblList) {
		this.userTblList = userTblList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
