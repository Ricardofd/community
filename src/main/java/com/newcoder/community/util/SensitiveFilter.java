package com.newcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    private static final String REPLACEMENT = "***";//替换敏感词的符号

    private TrieNode rootNode = new TrieNode();

    //自动初始化树的方法,注解说明这是个初始化方法，当容器调用这个Bean构造器之后运行，这个Bean加了component，在服务启动的时候这个bean就初始化了
    @PostConstruct
    public void init(){
        try (            InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                         //字节流转字符流转缓冲流（效率高）
                         BufferedReader reader = new BufferedReader(new InputStreamReader(is));){//trycatch会自动关闭字节流
            String keyword;
            while((keyword= reader.readLine())!=null){
                //添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (Exception e) {
            logger.error("加载敏感词文件失败"+e.getMessage());
        }
    }
    //将敏感词添加到前缀树
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for(int i=0;i<keyword.length();i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(subNode==null){
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            //到这子节点已经有了，要不是新建要不是原来就有
            //指针指向子节点，进入下一轮循环
            tempNode = subNode;
            //最后一个字符要打结束标志
            if(i==keyword.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词，参数是待过滤的文本，返回的是过滤后的文本
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        if(StringUtils.isBlank(text))return null;
        //指针一
        TrieNode tempNode = rootNode;
        //指针二
        int begin = 0;
        //指针三
        int position = 0;
        //记录最终结果
        StringBuilder sb = new StringBuilder();
        while(position<text.length()){
            char c = text.charAt(position);
            //跳过符号
            if(isSymbol(c)){
                //若指针1在根节点,则计入结果，指针2下移一步
                if(tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                position++;//指针3无论什么情况都走，所以写外面
                continue;
            }
            //不是符号，检查下级节点咯
            tempNode = tempNode.getSubNode(c);
            if(tempNode==null) {//下级没有节点了
                //则以begin为开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                //进入下一个位置
                begin++;
                position = begin;
                //指针1归位根节点
                tempNode = rootNode;
            }else if(tempNode.isKeywordEnd()){
                //发现敏感词，从begin开头，position结尾，将这一段替换
                sb.append(REPLACEMENT);
                //进入下一个位置
                position++;
                begin=position;
                tempNode = rootNode;
            }else{
                //检查下一个字符
                position++;
            }
        }
        //考虑最后一批字符，即指针3到终点，2没到重点的情况
        sb.append(text.substring(begin));
        return sb.toString();
    }
    //判断是否为符号
    private boolean isSymbol(Character c){
        return !CharUtils.isAsciiAlphanumeric(c) && (c<0x2E80 || c>0x9FFF);//判断字符是不是特殊符号,后面是东亚文字范围判断
    }

    //根据敏感词文件定义敏感词前缀树
    private class TrieNode{
        //关键词结束标志
        private boolean isKeywordEnd = false;
        //子节点（多个),key是下级节点的字符，value是下级节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }
        //添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
