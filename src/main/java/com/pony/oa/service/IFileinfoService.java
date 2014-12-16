package com.pony.oa.service;

import java.io.File;

import com.pony.oa.entity.Fileinfo;

//file api
public interface IFileinfoService {
	
	/**
	 * 保存文件及文件元数据
	 * 
	 * @param fileinfo
	 * @param file
	 */
	public void save(Fileinfo fileinfo, File file);
	
	/**
	 * 删除文件及元数据
	 * 
	 * @param id
	 */
	public void remove(String id);
	
	/**
	 * 删除文件及元数据
	 * 
	 * @param ids
	 */
	public void remove(String[] ids);
	
	/**
	 * 查找文件
	 * 
	 * @param id
	 * @return
	 */
	public File findFile(String id);
	
	
}
