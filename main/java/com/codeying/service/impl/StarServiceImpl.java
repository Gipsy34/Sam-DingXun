package com.codeying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codeying.utils.CommonUtils;
import com.codeying.entity.Star;
import com.codeying.mapper.StarMapper;
import com.codeying.service.StarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class StarServiceImpl extends AbsServiceImpl<StarMapper, Star> implements StarService {

    /**
     * Favorite
     */
    @Override
    public boolean star(String userid, String role, String itemId, String itemType, String op) {
        if (hasStar(userid, role, itemId, itemType, op) != null) {
            return false;
        }
        Star star = new Star();
        star.setId(CommonUtils.newId());
        star.setCreatetime(new Date());
        star.setItemid(itemId);
        star.setItemtype(itemType);
        star.setUserid(userid);
        star.setUserrole(role);
        star.setType(op);
        save(star);
        return true;
    }

    /**
     * isFavorite
     */
    @Override
    public String hasStar(String userid, String role, String itemId, String itemType, String op) {
        List<Star> list = list(new LambdaQueryWrapper<Star>()
                .eq(Star::getItemid, itemId)
                .eq(Star::getUserid, userid)
                .eq(Star::getUserrole, role)
                .eq(Star::getType, op)
                .eq(Star::getItemtype, itemType)
        );
        if (list.size() > 0) {
            return list.get(0).getId();
        }
        return null;
    }

    /**
     * 我的Favorite
     */
    @Override
    public List<Star> userStar(String userid, String role) {
        List<Star> list = list(new LambdaQueryWrapper<Star>()
                .eq(Star::getUserid, userid)
                .eq(Star::getUserrole, role)
                .eq(Star::getType, "Favorite")
        );
        List<Star> resList = new ArrayList<>();
        //填充内容
        for (Star star : list) {
            Star res = baseMapper.getStar(star.getItemtype(), star.getItemid());
            if (res == null) {
                //内容已经被删除
                removeById(star.getId());
            } else {
                star.setShowpic(res.getShowpic());
                star.setShowtitle(res.getShowtitle());
                star.setShowdesc(res.getShowdesc());
                star.setPublishtime(res.getPublishtime());
                resList.add(star);
            }
        }
        return resList;
    }
}

