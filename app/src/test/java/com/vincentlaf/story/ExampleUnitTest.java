package com.vincentlaf.story;

import com.alibaba.fastjson.JSONObject;
import com.vincentlaf.story.bean.Method;
import com.vincentlaf.story.bean.User;
import com.vincentlaf.story.bean.netbean.StoryListInfo;
import com.vincentlaf.story.bean.param.BasicParam;
import com.vincentlaf.story.bean.param.FullStoryParam;
import com.vincentlaf.story.bean.param.QueryListParam;
import com.vincentlaf.story.bean.result.QueryResult;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.util.RequestUtil;
import com.vincentlaf.story.util.StringUtil;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends TestCase{
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public void testLogin() throws IOException, WrongRequestException {
        JSONObject param=new JSONObject();
        param.put("userPhone","15071301289");
        param.put("userPass","123456");
        Result result=RequestUtil.doPost(RequestUtil.testUrl, Method.LOGIN, param);
        User user=result.getEntityData(User.class);
        System.out.println(user.getUserId());
    }
    public void testFindStories() throws IOException, WrongRequestException {
        QueryListParam param=new QueryListParam();
        param.setLon(23.4646621);
        param.setLat(95.1646562);
        param.setPage(1);
        Result result = RequestUtil.doPost(RequestUtil.testUrl,Method.FIND_STORIES,param);
        System.out.println(result.toString());
        QueryResult<StoryListInfo> infos= result.getList(StoryListInfo.class);
        System.out.println(infos.getRows().size());
    }

    public void testImage2String() throws IOException {
        File file =new File("C:\\Users\\Johnson\\Desktop\\1.jpg");
        String a=StringUtil.file2String(file);
        StringUtil.string2File(a,"C:\\Users\\Johnson\\Desktop","2.jpg");

    }

    public void testRegist() throws IOException, WrongRequestException {
        User user = new User();
        user.setUserName("dsafdas");
        user.setUserPass("dsafdas");
        user.setUserPhone("15071301289");
        user.setUserPicEntity(StringUtil.file2String(new File("C:\\Users\\Johnson\\Desktop\\1.jpg")));
        Result result = RequestUtil.doPost(RequestUtil.testUrl,Method.REGISTER,user);
        System.out.println(result);
    }

    public void testPublish() throws IOException, WrongRequestException {
        FullStoryParam param=new FullStoryParam();
        param.setUid(1);
        param.setContent("title","content",StringUtil.file2String(new File("C:\\Users\\Johnson\\Desktop\\1.jpg")));
        param.setPlace(23.264686,98.568451,"placeName");
        Result result=RequestUtil.doPost(RequestUtil.testUrl,Method.PUBLISH_STORY,param);
        System.out.println(result.toString());
    }
}