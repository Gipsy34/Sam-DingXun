package com.codeying.service.impl;

import java.util.HashSet;
import java.util.Set;

/**
 * 字符串相似度计算器，采用词空间向量模型实现相似度计算
 * 支持预处理、词规范化、集合运算等完整流程
 */
public class AdvancedTextSimilarity {

    /**
     * 计算两个文本的相似度核心方法
     * @param text1 第一个输入文本（需用空格分隔特征项）
     * @param text2 第二个输入文本（需用空格分隔特征项）
     * @return 基于词项交集的相似度值（非标准化整数值）
     */
    public static int calculateSimilarity(String text1, String text2) {
        // 输入安全处理层
        String processedText1 = inputSanitization(text1);
        String processedText2 = inputSanitization(text2);

        // 特征工程处理层
        Set<String> vectorSpace1 = buildFeatureSpace(processedText1);
        Set<String> vectorSpace2 = buildFeatureSpace(processedText2);

        // 相似度计算层
        return computeFeatureIntersection(vectorSpace1, vectorSpace2);
    }

    /**
     * 输入净化处理（空值保护+首尾空格处理）
     * @param rawInput 原始输入文本
     * @return 安全处理后的标准字符串
     */
    private static String inputSanitization(String rawInput) {
        if (rawInput == null) return "";
        return rawInput.trim();
    }

    /**
     * 构建文本特征空间（带词项标准化处理）
     * @param processedText 预处理后的文本
     * @return 标准化词项的特征集合
     */
    private static Set<String> buildFeatureSpace(String processedText) {
        Set<String> featureSpace = new HashSet<>();
        if (processedText.isEmpty()) return featureSpace;

        // 多级分割处理
        String[] rawTokens = processedText.split(",");
        for (String token : rawTokens) {
            String standardized = lexicalStandardization(token);
            if (!standardized.isEmpty()) {
                featureSpace.add(standardized);
            }
        }
        return featureSpace;
    }

    /**
     * 词项标准化处理（大小写归一化+空格清理）
     * @param rawToken 原始词项
     * @return 标准化后的词项
     */
    private static String lexicalStandardization(String rawToken) {
        return rawToken.trim().toLowerCase();
    }

    /**
     * 特征空间交集运算器
     * @param space1 第一个特征空间
     * @param space2 第二个特征空间
     * @return 两个特征空间的交集基数
     */
    private static int computeFeatureIntersection(Set<String> space1, Set<String> space2) {
        Set<String> intersection = new HashSet<>(space1);
        intersection.retainAll(space2);
        return intersection.size();
    }

    /**
     * 增强版相似度计算（带详细日志输出）
     * @param text1 第一个输入文本
     * @param text2 第二个输入文本
     * @param debug 是否启用调试模式
     * @return 相似度值及调试信息
     */
    public static int calculateSimilarityWithLog(String text1, String text2, boolean debug) {
        if (debug) {
            System.out.println("=== 相似度计算调试模式 ===");
            System.out.println("原始输入1: [" + text1 + "]");
            System.out.println("原始输入2: [" + text2 + "]");
        }

        int result = calculateSimilarity(text1, text2);

        if (debug) {
            System.out.println("净化后输入1: [" + inputSanitization(text1) + "]");
            System.out.println("净化后输入2: [" + inputSanitization(text2) + "]");
            System.out.println("计算得到相似度值: " + result);
            System.out.println("=== 调试结束 ===\n");
        }
        return result;
    }

    /**
     * 单元测试用例
     */
    public static void main(String[] args) {

        // 标准测试用例
        String sample1 = "唱歌,跳舞,轰趴";
        String sample2 = "跳舞,轰趴,野炊,代码,野炊,代码";

        System.out.println("基本测试：");
        int score = calculateSimilarity(sample1, sample2);
        System.out.println("相似度得分: " + score);

        // 调试模式测试
        System.out.println("\n调试模式测试：");
        calculateSimilarityWithLog(sample1, sample2, true);

        // 边缘测试
        System.out.println("\n边缘测试：");

        String[] testCases = {
                null, "",
                "  ", "   ",
                "Hello World", "hello world hello world",
                "Java Python", "Python Java C++"
        };

        for (int i = 0; i < testCases.length; i += 2) {
            int score2 = calculateSimilarity(testCases[i], testCases[i+1]);
            System.out.println(String.format("Case %d: '%s' vs '%s' => %d",
                    i/2+1,
                    testCases[i],
                    testCases[i+1],
                    score2));
        }
    }

}