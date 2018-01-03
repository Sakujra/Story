package com.vincentlaf.story;

import com.alibaba.fastjson.JSONObject;
import com.vincentlaf.story.bean.Method;
import com.vincentlaf.story.bean.User;
import com.vincentlaf.story.bean.netbean.StoryListInfo;
import com.vincentlaf.story.bean.param.BasicParam;
import com.vincentlaf.story.bean.param.QueryListParam;
import com.vincentlaf.story.bean.result.QueryResult;
import com.vincentlaf.story.bean.result.Result;
import com.vincentlaf.story.exception.WrongRequestException;
import com.vincentlaf.story.util.RequestUtil;

import junit.framework.TestCase;

import org.junit.Test;

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
}