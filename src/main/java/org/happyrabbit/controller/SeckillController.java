package org.happyrabbit.controller;

import org.happyrabbit.dto.Exposer;
import org.happyrabbit.dto.SeckillExcution;
import org.happyrabbit.dto.SeckillResult;
import org.happyrabbit.entity.Seckill;
import org.happyrabbit.enums.SeckillStateEnum;
import org.happyrabbit.exception.RepeatKillException;
import org.happyrabbit.exception.SeckillCloseException;
import org.happyrabbit.service.SeckillSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller //将当前controller放入到容器当中
@RequestMapping("/seckill")  //url根
public class SeckillController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillSerivce seckillSerivce;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model) {
        //list.jsp + model = ModelAndView

        List<Seckill> list = seckillSerivce.getSeckillList();

        logger.info("--------------------------------list--------------------------------");
        for(Seckill seckill:list){
            logger.info("seckill="+seckill.toString());
        }
        logger.info("--------------------------------list--------------------------------");


        model.addAttribute("list",list);

        return "list"; //  /WEB-INF/jsp/list.jsp
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {

        logger.info("--------------------------------detail--------------------------------");

        logger.info("seckillId=" + seckillId);

        if (seckillId == null) {
            return "redirect:/seckill/list";
        }

        Seckill seckill = seckillSerivce.getById(seckillId);

        logger.info("seckill=" + seckill.toString());

        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);

        logger.info("--------------------------------detail out--------------------------------");

        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckilled) {

        SeckillResult<Exposer> result;

        try {
            Exposer exposer = seckillSerivce.exportSeckillUrl(seckilled);

            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.info(e.getMessage());
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }


        return result;
    }


    @RequestMapping(value = "/{seckill}/{md5}/excution",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExcution> excute(@PathVariable("seckillId") Long seckillId,
                                                 @PathVariable("md5") String md5,
                                                 @CookieValue(value = "killPhone",required = false) Long phone) {

        if (phone == null) {
            return new SeckillResult<SeckillExcution>(false, "该客户未注册！");
        }

        SeckillResult<SeckillExcution> result;


        try {
            SeckillExcution seckillExcution = seckillSerivce.excuteSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExcution>(true, seckillExcution);
        } catch (RepeatKillException e) {
            //重复秒杀
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExcution>(false, seckillExcution);
        } catch (SeckillCloseException e) {
            //秒杀关闭
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExcution>(false, seckillExcution);
        } catch (Exception e) {
            //其他所有错误
            logger.info(e.getMessage());
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExcution>(false, seckillExcution);
        }

    }

    @RequestMapping(value = "/time/now",method=RequestMethod.GET)
    public SeckillResult<Long> time() {

        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }





}
