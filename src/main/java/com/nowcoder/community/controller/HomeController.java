package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lv
 * @create 2022-08-14 13:01
 */
//Controller访问路径可以省略
@Controller
public class HomeController implements CommunityConstant {

    //注入对象
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){

        //方法调用之前，Spring MVC会自动实例化Model和Page，并讲Page注入Model
        //所以在thymeleaf中可以直接访问page对象中的数据
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list=discussPostService.findDiscussPosts(0,page.getOffset(),page.getLimit());
        //用Map把post和user装到一起
        List<Map<String,Object>> discussPosts=new ArrayList<>();
        //遍历post，用post里的id查询user
        if(list!=null){
            for (DiscussPost post:list){
                Map<String,Object> map=new HashMap<>();
                map.put("post",post);
                User user=userService.findUserById(post.getUserId());
                map.put("user",user);

                //新增加的方法 点赞数量
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }
        //把我们准备好的数据传给model
        model.addAttribute("discussPosts",discussPosts);
        //返回模板
        return "/index";
    }

    //错误时返回
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }


}