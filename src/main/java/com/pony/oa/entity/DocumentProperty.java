package com.pony.oa.entity;

import java.beans.PropertyDescriptor;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.beanutils.PropertyUtils;

import com.pony.core.entity.BaseEntity;

@Entity
@Table(name="oa_document_property")
public class DocumentProperty extends BaseEntity{

	private	Document 	document; 			//所属公文
	private String 		java_lang_String;	//字符串字段
	private Long		java_lang_Long;
    private Integer 	java_lang_Integer;
    private Float		java_lang_Float;
    private Boolean		java_lang_Boolean;
    private Date 		java_util_Date;
    private byte[] 		java_io_File;

    @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="doc_id")
    public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	
	@Column(length=100)
    public String getJava_lang_String() {
        return java_lang_String;
    }
    public void setJava_lang_String(String java_lang_String) {
        this.java_lang_String = java_lang_String;
    }
    
    @Basic
    public Long getJava_lang_Long() {
        return java_lang_Long;
    }
    public void setJava_lang_Long(Long java_lang_Long) {
        this.java_lang_Long = java_lang_Long;
    }
    
    @Basic
    public Integer getJava_lang_Integer() {
        return java_lang_Integer;
    }
    public void setJava_lang_Integer(Integer java_lang_Integer) {
        this.java_lang_Integer = java_lang_Integer;
    }
    
    @Basic
    public Float getJava_lang_Float() {
		return java_lang_Float;
	}
	public void setJava_lang_Float(Float java_lang_Float) {
		this.java_lang_Float = java_lang_Float;
	}
	
	@Column(columnDefinition="integer")
	public Boolean getJava_lang_Boolean() {
		return java_lang_Boolean;
	}
	public void setJava_lang_Boolean(Boolean java_lang_Boolean) {
		this.java_lang_Boolean = java_lang_Boolean;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    public Date getJava_util_Date() {
        return java_util_Date;
    }
    public void setJava_util_Date(Date java_util_Date) {
        this.java_util_Date = java_util_Date;
    }
    
    @Lob @Basic(fetch=FetchType.EAGER)
    public byte[] getJava_io_File() {
        return java_io_File;
    }
    public void setJava_io_File(byte[] java_io_File) {
        this.java_io_File = java_io_File;
    }

    private static String[] supportedTypeNames;
    static{
        PropertyDescriptor[] ps = PropertyUtils.getPropertyDescriptors(DocumentProperty.class);
        supportedTypeNames = new String[ps.length];
        for(int i=0; i< ps.length; i++){
            PropertyDescriptor pd = ps[i];
            if(!"document".equals(pd.getName())){
            	supportedTypeNames[i] = pd.getName();
            }
        }
    }
    public static boolean support(String type){
        for(int i=0; i<supportedTypeNames.length; i++){
            if(type.equals(supportedTypeNames[i])){
                return true;
            }
        }
        return false;
    }
    
}
