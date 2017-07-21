package com.changyu.foryou.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.changyu.foryou.model.News;
import com.changyu.foryou.model.SmallNews;
import com.changyu.foryou.service.NewsService;
import com.changyu.foryou.tools.Constants;


/**
 * 处理新闻控制器
 *
 * @author 殿下
 */
@Controller
@RequestMapping("/news")
public class NewsController {
    private NewsService newsService;

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * 获取主界面图片
     *
     * @return
     */
    @RequestMapping("/getMainImage")
    public @ResponseBody
    Map<String, Object> getMainImage(@RequestParam Integer campusId, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("campusId", campusId);
        if (page != null && limit != null) {
            requestMap.put("offset", (page - 1) * limit);
            requestMap.put("limit", limit);
        }
        try {
            List<SmallNews> news = newsService.getSmallNews(requestMap);
            if (news.size() == 0) {
                map.put(Constants.STATUS, Constants.SUCCESS);
                map.put(Constants.MESSAGE, "没有图片！");
                map.put("newsList", news);
            } else {
                map.put(Constants.STATUS, Constants.SUCCESS);
                map.put(Constants.MESSAGE, "获取图片成功！");
                map.put("newsList", news);
            }
        } catch (Exception e) {
            e.getStackTrace();
            map.put(Constants.STATUS, Constants.FAILURE);
            map.put(Constants.MESSAGE, "获取图片失败");
        }

        return map;
    }

    /**
     * 根据id获取新闻
     *
     * @param newsId
     * @return
     */
    @RequestMapping("/getNewsById")
    public @ResponseBody
    Map<String, Object> getNewsById(@RequestParam Long newsId) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            News news = newsService.getNewsById(newsId);
            if (news == null) {
                map.put(Constants.STATUS, Constants.FAILURE);
                map.put(Constants.MESSAGE, "获取新闻失败");
            } else {
                map.put(Constants.STATUS, Constants.SUCCESS);
                map.put(Constants.MESSAGE, "获取新闻成功");
                map.put("news", news);
            }
        } catch (Exception e) {
            e.getStackTrace();
            map.put(Constants.STATUS, Constants.FAILURE);
            map.put(Constants.MESSAGE, "获取新闻失败");
        }
        return map;
    }

    @RequestMapping("/getPCAllNews")
    public @ResponseBody
    JSONArray getPcAllNews(@RequestParam Integer campusId) {
        List<News> news = null;
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("campusId", campusId);
        try {
            news = newsService.getPcAllNews(requestMap);
            System.out.println(news.size());
            for (News newsMessage : news) {
                if (newsMessage.getContent().length() > 27) {
                    String contentString = newsMessage.getContent().substring(0, 26);
                    newsMessage.setContent(contentString + ".....");
                }
            }
            JSONArray json = JSON.parseArray(JSON.toJSONStringWithDateFormat(news, "yyyy-MM-dd"));
            return json;
        } catch (Exception e) {
            e.getStackTrace();
            e.getMessage();
        }

        return null;
    }

    /**
     * 更新新闻图片
     *
     * @param myfile
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadNewsImage")
    public String uploadNewsImage(@RequestParam MultipartFile myfile, HttpServletRequest request) throws IOException {
        News news = new News();

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Integer campusId = Integer.parseInt(request.getParameter("campusId"));
        if (myfile.isEmpty()) {
            System.out.println("文件未上传");
        } else {
            String contentType = myfile.getContentType();

            if (contentType.startsWith("image")) {
                String realPath = request.getSession().getServletContext().getRealPath("/");

                realPath = realPath.replace("foryou", "ForyouImage");
                realPath = realPath.concat("/news");

                System.out.println(realPath);
                String newFileName = new Date().getTime() + "" + new Random().nextInt() + ".jpg";
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, newFileName));

                String imageUrl = Constants.localIp + "/news/" + newFileName;
                news.setImgUrl(imageUrl);
                news.setTitle(title);
                news.setCreateTime(new Date());
                news.setContent(content);
                news.setNewsId(new Date().getTime());
                news.setCampusId(campusId);
                int flag = newsService.addNews(news);
                if (flag != 0 && flag != -1) {
                    return "redirect:/pages/news.html";
                }
            }
        }

        return "redirect:/pages/uploadError.html";
    }

    @RequestMapping(value = "/deleteNews", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteNewsById(String newsId) {
        Map<String, Object> map = new HashMap<>();
        String ids[] = newsId.split(",");
        try {
            int flag = 0;

            for (String id : ids) {
                if (id != null && !id.equals("")) {
                    flag = newsService.deleteById(id);
                }
            }
            if (flag != -1) {
                map.put(Constants.STATUS, Constants.SUCCESS);
                map.put(Constants.MESSAGE, "删除新闻成功！");
            } else {
                map.put(Constants.STATUS, Constants.FAILURE);
                map.put(Constants.MESSAGE, "删除新闻失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.STATUS, Constants.FAILURE);
            map.put(Constants.MESSAGE, "删除新闻失败！");
        }

        return map;
    }
}
