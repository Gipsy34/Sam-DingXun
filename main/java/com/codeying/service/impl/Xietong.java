package com.codeying.service.impl;

import java.util.*;

/**
 * 协同过滤算法
 */
public class Xietong {

    public static void main(String[] args) {
        List<List<String>> userAndLike = new ArrayList<>();

        List<String> a = new ArrayList<>();
        a.add("A");a.add("a");a.add("b");a.add("d");
        userAndLike.add(a);

        List<String> b = new ArrayList<>();
        b.add("B");b.add("a");b.add("c");
        userAndLike.add(b);

        List<String> c = new ArrayList<>();
        c.add("C");c.add("b");c.add("e");
        userAndLike.add(c);

        List<String> d = new ArrayList<>();
        d.add("D");d.add("c");d.add("d");d.add("e");
        userAndLike.add(d);

        List<String> e = new ArrayList<>();
        e.add("E");e.add("c");e.add("a");e.add("f");
        userAndLike.add(e);

        compute(userAndLike, "B");
    }
    public static List<Sort> compute(List<List<String>> userAndLike,String recommendUser) {
        /**
         * 输入用户-->喜欢的条目 ，一个用户对应多个物品，第一个值是用户的id，接着是用户喜欢的物品id
         * 用户ID 物品ID集合，如
         * A  a b d
         * B  a c
         * C  b e
         * D  c d e
         */
        //输入用户总量
        int N = userAndLike.size();
        int[][] sparseMatrix = new int[N][N];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();//存储每一个用户对应的不同物品总数  eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();//建立物品到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();//辅助存储物品集合
        Map<String, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射
        for(int i = 0; i < N ; i++){//依次处理N个用户 输入数据  以空格间隔
            List<String> user_item = userAndLike.get(i);
            int length = user_item.size();
            userItemLength.put(user_item.get(0), length-1);//eg: A 3
            userID.put(user_item.get(0), i);//用户ID与稀疏矩阵建立对应关系
            idUser.put(i, user_item.get(0));
            //建立物品--用户倒排表
            for(int j = 1; j < length; j ++){
                if(items.contains(user_item.get(j))){//如果已经包含对应的物品--用户映射，直接添加对应的用户
                    itemUserCollection.get(user_item.get(j)).add(user_item.get(0));
                }else{//否则创建对应物品--用户集合映射
                    items.add(user_item.get(j));
                    itemUserCollection.put(user_item.get(j), new HashSet<>());//创建物品--用户倒排关系
                    itemUserCollection.get(user_item.get(j)).add(user_item.get(0));
                }
            }
        }
        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        System.out.println(userItemLength.toString());
        System.out.println(userID.get(recommendUser));
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+sparseMatrix[recommendUserId][j]/Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j))));
            }
        }
        List<Sort> sortList = new ArrayList<>();
        //计算指定用户recommendUser的物品推荐度
        for(String item: items){//遍历每一件物品
            Set<String> users = itemUserCollection.get(item);//得到购买当前物品的所有用户集合
            if(!users.contains(recommendUser)){//如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(String user: users){
                    itemRecommendDegree += sparseMatrix[userID.get(recommendUser)][userID.get(user)] /
                            Math.sqrt(userItemLength.get(recommendUser)*userItemLength.get(user));//推荐度计算
                }
                System.out.println("The item "+item+" for "+recommendUser +"'s recommended degree:"+itemRecommendDegree);
                sortList.add(new Sort(item,itemRecommendDegree));
            }
        }
        Sort.sort(sortList);
        List<String> res = new ArrayList<>();
        for (Sort sort : sortList) {
            res.add(sort.id);
        }
        return sortList;
    }

    public static class Sort {
        public String id;
        public double score;

        public Sort(String id, double score) {
            this.id = id;
            this.score = score;
        }

        public static void sort(List<Sort> list){
            Sort e;
            for (int i = list.size()-1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if(list.get(j).score < list.get(j+1).score){
                        e=list.get(j);
                        list.set(j,list.get(j+1));
                        list.set(j+1,e);
                    }
                }
            }
            for (Sort sort : list) {
                System.out.println(sort.id +" :"+ sort.score);
            }
        }

        public String getId() {
            return id;
        }

        public double getScore() {
            return score;
        }

    }

}
