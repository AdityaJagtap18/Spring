package com.springcrud.CurdOparation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name ="chart_report")
@NamedQuery(name = "ChartReportTbl.findAll",query="SELECT m FROM ChartReportTbl m")
public class ChartReportTbl {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int Id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="x_axis_header")
	private String xAxisHeader;
	
	@Column(name="y_axis_header")
	private String yAxisHeader;
	
	@Column(name="x_axis_values")
	private String xAxisValues;

	@Column(name="y_axis_values")
	private String yAxisValues;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		type = type;
	}

	public String getxAxisHeader() {
		return xAxisHeader;
	}

	public void setxAxisHeader(String xAxisHeader) {
		this.xAxisHeader = xAxisHeader;
	}

	public String getyAxisHeader() {
		return yAxisHeader;
	}

	public void setyAxisHeader(String yAxisHeader) {
		this.yAxisHeader = yAxisHeader;
	}

	public String getxAxisValues() {
		return xAxisValues;
	}

	public void setxAxisValues(String xAxisValues) {
		this.xAxisValues = xAxisValues;
	}

	public String getyAxisValues() {
		return yAxisValues;
	}

	public void setyAxisValues(String yAxisValues) {
		this.yAxisValues = yAxisValues;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
