package com.news.qidian.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fengjigang on 16/3/30.
 * 新闻详情页
 */
public class NewsDetail implements Serializable {

    /**
     * imgNum : 1
     * pubTime : 2016-03-30 10:54:05
     * url : http://www.yidianzixun.com/home?page=article&id=0Coo66St
     * docid : 0Coo66St
     * tags : [""]
     * descr : 北京时间3月30日，NBA2015-16赛季常规赛克利夫兰骑士队坐镇主场速贷中心球馆迎战休斯敦火箭队。此前，骑士（52-21）客胜尼克斯；火箭（36-38）客场不敌步行者。本场比赛，詹姆斯轮休。双方开局势均力敌，火箭逐渐占据场上主动，首节领先2分。第二节，骑士
     * pubName : 直播吧
     * pubUrl : http://news.zhibo8.cc/nba/2016-03-30/68546.htm
     * content : [{"img":"http://bdp-pic.deeporiginalx.com/W0JAMTM4NjBkMWY.jpg"},{"txt":"北京时间3月30日，NBA常规赛火箭客场106-100战胜骑士。火箭在最多落后20分的情况下逆转奏凯，保住西部第八的排名。哈登末节砍下18分，全场比赛他送出27分8助攻6篮板。詹姆斯轮休，欧文为骑士砍下31分。"},{"txt":"\n本场比赛，詹姆斯轮休。双方开局势均力敌，火箭逐渐占据场上主动，首节领先2分。第二节，骑士成功反超比分，欧文率队建立起19分的领先优势。第三节，火箭苦苦追分，欧文、香波特发挥出色，帮助骑士领先13分进入末节。第四节，哈登、比斯利帮助火箭成功反超比分，欧文连得4分将分差缩小到1分，终场前17.3秒，阿里扎底角命中三分，火箭领先4分。哈登两罚全中，欧文三罚全中，霍华德两罚一中，终场前8.4秒，火箭领先4分。哈登两罚全中，火箭锁定胜局。最终，火箭客场106比100逆转骑士。"},{"txt":"首节比赛开始，欧文抢断快攻上篮得手，为骑士主场先声夺人。贝弗利助攻莫泰尤纳斯上篮得手，为火箭队首次得分。欧文突破上篮得手，霍华德转身勾手打进，阿里扎两罚一中，欧文技犯罚球命中，阿里扎两罚全中，乐福中投命中，哈登三分命中，乐福突破挑篮得手，莫泰尤纳斯转身勾手打进，乐福两罚一中，霍华德两罚一中，香波特助攻T-汤普森上篮得手，贝弗利抢断快攻上篮得手，霍华德助攻比斯利上篮得手，火箭17-12领先。比斯利两罚一中，JR-史密斯两罚全中，布鲁尔急停跳投命中，德拉维多瓦抛投得手，4分分差。哈登两罚一中，特里三分命中，T-汤普森罚球得到4分，比斯利跳投命中，琼斯打成3+1。首节战罢，火箭26-24领先骑士2分。"},{"txt":"第二节比赛开始，莫-威廉姆斯、J-琼斯三分命中，骑士30-26反超。阿里扎突破上篮得手，琼斯三罚全中，布鲁尔转身跳投打板命中，莫-威廉姆斯三分命中，骑士36-30领先。哈登突破上篮得手，德拉维多瓦转身后仰跳投命中，麦克丹尼尔斯三分飙中，乐福后撤步跳投打板命中，霍华德两罚一中，莫兹戈夫补扣得手，欧文三分命中，JR-史密斯突破抛投得手，骑士47-36领先。卡佩拉两罚一中，比斯利转身强攻上篮得手，乐福三分命中，欧文连得4分，骑士领先15分。T-汤普森两罚全中，欧文三分命中，哈登两罚一中。半场结束，骑士59-40领先火箭19分。"},{"txt":"下半场双方易边再战，莫泰尤纳斯转身勾手打进，香波特三分命中，霍华德两罚一中，阿里扎快攻暴扣，贝弗利二次进攻反手上篮得手，15分分差。暂停回来，欧文技犯罚球命中，香波特两罚一中，霍华德篮下强攻得手，比斯利突破上篮打进，香波特快攻暴扣，贝弗利、乐福对飙三分，比斯利突破反身拉杆上篮得手，香波特急停跳投命中，比斯利转身跳投命中，卡佩拉补扣得手，T-汤普森空切暴扣，欧文三分命中，骑士领先16分。阿里扎转身跳投命中，哈登反击快攻上篮得手，香波特三分命中，卡佩拉连得4分，T-汤普森两罚全中，阿里扎、欧文对飙三分。三节战罢，骑士84-71领先火箭13分。"},{"txt":"进入第四节，哈登连得7分，T-汤普森转身勾手打进，卡佩拉空切暴扣，理查德-杰弗森底角三分命中，骑士89-80领先。暂停回来，布鲁尔两罚一中，比斯利中投命中，T-汤普森篮下转身打板命中，哈登连得4分，火箭将分差缩小到4分。霍华德两罚一中，比斯利中投命中，霍华德两罚一中，贝弗利三分命中，火箭94-91反超。欧文两罚一中后跳投命中，霍华德两罚一中，哈登、欧文对飙三分，阿里扎三分命中，终场前17.3秒，火箭领先4分哈登两罚全中，欧文三罚全中，霍华德两罚一中，终场前8.4秒，火箭领先4分。哈登两罚全中，火箭锁定胜局。最终，火箭客场106比100逆转骑士。"},{"txt":"骑士首发：莫兹戈夫、乐福、香波特、JR-史密斯、欧文"},{"txt":"火箭首发：霍华德、莫泰尤纳斯、阿里扎、哈登、贝弗利"}]
     * commentSize : 9
     * title : 20分惊天逆转！火箭灭骑士保前八 哈登末节18分
     * channelId : 6
     */

    private int imgNum;
    private String pubTime;
    private String url;
    private String docid;
    private String descr;
    private String pubName;
    private String pubUrl;
    private int commentSize;
    private String title;
    private int channelId;
    private ArrayList<String> tags;
    private ArrayList<HashMap<String,String>> content;

    public int getImgNum() {
        return imgNum;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getPubUrl() {
        return pubUrl;
    }

    public void setPubUrl(String pubUrl) {
        this.pubUrl = pubUrl;
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<HashMap<String, String>> getContent() {
        return content;
    }

    public void setContent(ArrayList<HashMap<String, String>> content) {
        this.content = content;
    }

}
