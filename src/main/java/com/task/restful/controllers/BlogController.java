package com.task.restful.controllers;

import com.task.domain.ResultCode;
import com.task.domain.ResultVo;
import com.task.domain.blog.SearchBlogReqVo;
import com.task.restful.controllers.exception.BadRequestException;
import com.task.restful.services.BlogService;
import com.task.restful.utils.CommonUtil;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("search")
    public ResultVo search(@Valid SearchBlogReqVo reqVo, BindingResult bindingResult) throws Exception {

        if(bindingResult.hasErrors()) {
            throw new BadRequestException(ResultCode.INVALID_PARAM, CommonUtil.makeBindingResultMessage(bindingResult));
        }

        return new ResultVo(ResultCode.OK, blogService.getBlogs(reqVo));
    }

}
