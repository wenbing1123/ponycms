package com.pony.core.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pony.core.dao.IDao;
import com.pony.core.hibernate4.jpa.HibernateJpaDaoSupport;

@Repository("dao")
@Transactional
public class DaoImpl extends HibernateJpaDaoSupport implements IDao{

}
