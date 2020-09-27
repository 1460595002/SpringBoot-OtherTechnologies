package cn.jinronga.service;

import cn.jinronga.pojo.CategoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<CategoryEntity> {

  //查询分分类所有信息
    List<CategoryEntity> listTree();

}
