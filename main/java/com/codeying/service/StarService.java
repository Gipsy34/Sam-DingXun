package com.codeying.service;

import com.codeying.entity.Star;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface StarService extends MyService<Star> {

    boolean star(String userid, String role, String itemId, String itemType, String type);

    String hasStar(String userid, String role, String itemId, String itemType, String op);

    List<Star> userStar(String userid, String role);
}

