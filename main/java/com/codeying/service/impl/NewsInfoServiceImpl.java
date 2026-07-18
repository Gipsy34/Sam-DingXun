package com.codeying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.ai.AiRecommend;
import com.codeying.component.TokenService;
import com.codeying.entity.*;
import com.codeying.mapper.NewsInfoMapper;
import com.codeying.service.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/** 服务实现类 */
@Service
public class NewsInfoServiceImpl extends AbsServiceImpl<NewsInfoMapper, NewsInfo>
    implements NewsInfoService {

  @Autowired StarService starService;

  @Autowired protected TokenService tokenService; // token专用

  /** vue获取当前用户的信息缓存 */
  public LoginUser getCurrentUser() {
    return tokenService.getLoginInfo();
  }

  // 我已Favorite加分
  double ALREADY_STAR = 0.2;
  // 命中我的标签加分
  double MATCH_MY_LABEL_SCORE = 0.4;
  // 命中Favorite标签加分
  double MATCH_MY_STAR_SCORE = 0.4;
  // 推荐
  @Override
  public List<NewsInfo> recommend(List<NewsInfo> all) {
    LoginUser loginUser = getCurrentUser();
    if (loginUser == null) {
      return all; // 未LOG IN，不推荐
    }
    final List<NewsInfo> itemList;
    Map<String, NewsInfo> collect = all.stream().collect(Collectors.toMap(NewsInfo::getId, e -> e));
    List<Star> allStar =
        starService.list(
            new LambdaQueryWrapper<Star>()
                .eq(Star::getItemtype, "newsInfo")
                .eq(Star::getType, "Favorite"));

    // 基于用户的协同推荐
    // 总结其他用户行为
    Map<String, List<String>> listMap = new HashMap<>();
    for (Star star : allStar) {
      if (listMap.containsKey(star.getUserid())) {
        listMap.get(star.getUserid()).add(star.getItemid());
      } else {
        List<String> l = new ArrayList<>();
        l.add(star.getUserid());
        l.add(star.getItemid());
        listMap.put(star.getUserid(), l);
      }
    }
    // 该用户没有Favorite任何内容，那么自然无可分析
    if (listMap.get(loginUser.getId()) == null) {
      itemList = all;
    } else {
      List<List<String>> userAndLike = new ArrayList<>(listMap.values());
      String userid = loginUser.getId();
      List<AiRecommend.Sort> compute = new ArrayList<>();
      if (userAndLike.size() != 0) {
        compute = AiRecommend.compute(userAndLike, userid);
      }
      itemList = new ArrayList<>();
      // 加入推荐项目
      for (AiRecommend.Sort s : compute) {
        if (collect.get(s.getId()) != null) {
          collect.get(s.getId()).setRecommendScore(s.getScore());
          if (s.getScore() > 0.8) {
            collect.get(s.getId()).setSmallTip("为您推荐");
          }
          // else if(s.getScore() > 0.7){collect.get(s.getId()).setSmallTip("可能喜欢");}
          itemList.add(collect.get(s.getId()));
        }
      }
      // 加入其他项目
      collect.forEach(
          (e, v) -> {
            if (!itemList.contains(v) && v != null) itemList.add(v);
          });
    }

    // 根据用户的标签进行推荐
    if (loginUser.getRole().equalsIgnoreCase("User")) {
      // 如果用户有标签
      String userLabels = null;
      User thisUser = (User) loginUser;
      userLabels = thisUser.getBiaoq();
      if (userLabels != null && !userLabels.isEmpty()) {
        for (NewsInfo value : collect.values()) {
          int similarity = AdvancedTextSimilarity.calculateSimilarity(userLabels, value.getBiaoq());
          if (similarity > 0) {
            value.setRecommendScore(
                value.getRecommendScore() + similarity * MATCH_MY_LABEL_SCORE); // 用户标签加分
          }
        }
      }
    }

    // 基于物品的协同推荐
    List<String> myStarIds = new ArrayList<>(); // 对应项目可能为空
    for (Star star : allStar) {
      if (star.getUserid().equals(loginUser.getId())) {
        myStarIds.add(star.getItemid());
      }
    }
    StringBuilder myStarLabels = null; // 我Favorite的所有标签
    for (String myStarId : myStarIds) {
      NewsInfo value = collect.get(myStarId);
      if (value == null) {
        continue;
      }
      if (value.getBiaoq() != null && !value.getBiaoq().isEmpty()) {
        if (myStarLabels == null) {
          myStarLabels = new StringBuilder();
          myStarLabels.append(value.getBiaoq());
        } else {
          myStarLabels.append(",").append(value.getBiaoq());
        }
      }
    }

    // 推荐加分
    if (myStarLabels != null) {
      for (NewsInfo value : collect.values()) {
        if (myStarIds.contains(value.getId())) {
          // 已经Favorite的项目，就加0.1
          value.setRecommendScore(value.getRecommendScore() + ALREADY_STAR);
        } else {
          int similarity =
              AdvancedTextSimilarity.calculateSimilarity(myStarLabels.toString(), value.getBiaoq());
          if (similarity > 0) {
            // 命中Favorite标签
            value.setRecommendScore(value.getRecommendScore() + similarity * MATCH_MY_STAR_SCORE);
          }
        }
      }
    }

    for (NewsInfo e : itemList) {
      if (e.getSmallTip() == null) {
        if (e.getRecommendScore() > 0.7) {
          e.setSmallTip("猜你喜欢");
        } else if (e.getRecommendScore() > 0.5) {
          e.setSmallTip("可能喜欢");
        }
      }
    }

    // 排序
    itemList.sort(
        (o1, o2) -> {
          // 先根据分数排序，然后根据时间排序
          int scoreCompare = Double.compare(o2.getRecommendScore(), o1.getRecommendScore());
          if (scoreCompare != 0) {
            return scoreCompare;
          }
          // 再根据发布时间排序
          return Long.compare(o2.getPublishtime().getTime(), o1.getPublishtime().getTime());
        });
    return itemList;
  }

  @Override
  public List<NewsInfo> sqlSelectList(NewsInfo qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(NewsInfo e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(NewsInfo e) {
    return baseMapper.sqlSave(e);
  }

  @Override
  public List<NewsInfo> topN(int n) {
    Page<NewsInfo> page = new Page<>(1, n);
    return baseMapper.topN(page);
  }
}

