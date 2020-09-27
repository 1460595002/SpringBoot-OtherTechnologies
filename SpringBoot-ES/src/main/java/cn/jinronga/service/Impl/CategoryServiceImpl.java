package cn.jinronga.service.Impl;

import cn.jinronga.dao.CategoryDao;
import cn.jinronga.pojo.CategoryEntity;
import cn.jinronga.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl  extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Override
    public List<CategoryEntity> listTree() {
        //查询所有分类信息
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);

        //2、组装成父子的树形结构

        //2.1）、找出所有的一级分类

        List<CategoryEntity> level1Menus = categoryEntities.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((menu) -> {
                    //TODO 递归出所有分类信息需要的参数
                    menu.setChildren(getChildrens(menu,categoryEntities));
                    return menu;
                }).sorted((menu1, menu2) -> {
                    return ((menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()));

                }).collect(Collectors.toList());

        return level1Menus;
    }
    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {

            //1、找出子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            return ((menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()));
        }).collect(Collectors.toList());
        return children;
            }
}
