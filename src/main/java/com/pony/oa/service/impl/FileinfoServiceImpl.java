package com.pony.oa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pony.core.dao.IDao;
import com.pony.oa.entity.Fileinfo;
import com.pony.oa.service.IFileinfoService;
import com.pony.oa.util.SecurityUtils;

@Service("attachmentService")
@Transactional
public class FileinfoServiceImpl implements IFileinfoService {

	protected Logger logger = Logger.getLogger(getClass());
	
	@Value("${file.path}") private String base;
	@Resource private IDao dao;
	
	public void save(Fileinfo fileinfo, File file) {
		Assert.notNull(fileinfo);
		Assert.notNull(file);
		
		if(file.exists() && file.isFile()){
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DATE);
			
			String path = new StringBuilder()
				.append("/").append(year)
				.append("/").append(month)
				.append("/").append(day)
				.toString();
			fileinfo.setPath(path);
			fileinfo.setSize(file.length());
			fileinfo.setCreator(SecurityUtils.getUsername());
			
			dao.save(fileinfo);
			
			FileInputStream fin = null;
			OutputStream out = null;
			try {
				
				fin = new FileInputStream(file);
				out = new FileOutputStream(base + path + fileinfo.getId());
				
				fin.getChannel().transferTo(0, file.length(), Channels.newChannel(out));
				
			} catch (FileNotFoundException e) {
				logger.error("file doesn't found", e);
				throw new RuntimeException("file doesn't found");
			} catch (IOException e) {
				logger.error("file transfer error", e);
				throw new RuntimeException("file transfer error");
			} finally {
				if(fin != null){
					try {
						fin.close();
					} catch (IOException e) {}
				}
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {}
				}
			}
			
		}else{
			throw new RuntimeException("file doesn't exist or is not a file");
		}
		
	}

	public void remove(String id) {
		Assert.hasText(id);
		dao.remove(Fileinfo.class, id);
	}

	public void remove(String[] ids) {
		Assert.notNull(ids);
		for (String id : ids) {
			dao.remove(Fileinfo.class, id);
		}
	}
	
	public File findFile(String id) {
		Assert.hasText(id);
		Fileinfo fileinfo = dao.find(Fileinfo.class, id);
		if(fileinfo != null){
			String path  = base + fileinfo.getPath() + "/" + id;
			return new File(path);
		}
		return null;
	}

	
}
