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

	private	Document document; //所属公文
	private String java_lang_String; //属性类型为字符串  类型=值
    private Date java_util_Date; //属性类型为日期
    private Integer java_lang_Integer; //属性类型是整型
    private Long java_lang_Long; //属性类型为长整型
    private byte[] java_io_File; //属性类型为文件类型

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
    
    @Temporal(TemporalType.TIMESTAMP)
    public Date getJava_util_Date() {
        return java_util_Date;
    }
    public void setJava_util_Date(Date java_util_Date) {
        this.java_util_Date = java_util_Date;
    }
    
    @Basic
    public Integer getJava_lang_Integer() {
        return java_lang_Integer;
    }
    public void setJava_lang_Integer(Integer java_lang_Integer) {
        this.java_lang_Integer = java_lang_Integer;
    }
    
    @Basic
    public Long getJava_lang_Long() {
        return java_lang_Long;
    }
    public void setJava_lang_Long(Long java_lang_Long) {
        this.java_lang_Long = java_lang_Long;
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
