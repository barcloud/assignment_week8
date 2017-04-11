package cn.edu.cumt.service;

import cn.edu.cumt.dao.indexDao;
import cn.edu.cumt.dao.indexDaoimpl;
import cn.edu.cumt.entity.Good;

import java.util.List;

public class indexService {
    private indexDao indexDao;
    public void setIndexDao(){
        this.indexDao = new indexDaoimpl();
    }
    public List<Good> getAll(){
        return indexDao.getAll();
    }
}
