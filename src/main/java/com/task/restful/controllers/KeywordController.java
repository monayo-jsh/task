package com.task.restful.controllers;

import com.task.domain.ResultCode;
import com.task.domain.ResultVo;
import com.task.restful.services.KeywordService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("keyword")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("popular")
    public @ResponseBody ResultVo getPopular(HttpServletRequest request) throws Exception {
        return new ResultVo(ResultCode.OK, keywordService.getPopularKeywords());
    }

}
