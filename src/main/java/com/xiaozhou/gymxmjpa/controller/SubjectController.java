package com.xiaozhou.gymxmjpa.controller;

import com.alibaba.fastjson.JSON;
import com.xiaozhou.gymxmjpa.dao.PrivateCoachInfoDao;
import com.xiaozhou.gymxmjpa.dao.ReservationSubjectDao;
import com.xiaozhou.gymxmjpa.dao.SubjectDao;
import com.xiaozhou.gymxmjpa.entity.*;
import com.xiaozhou.gymxmjpa.mapper.SubjectReservationMapper;
import com.xiaozhou.gymxmjpa.mapper.UserReservationMapper;
import com.xiaozhou.gymxmjpa.service.SubjectDaoImpl;
import com.xiaozhou.gymxmjpa.service.SubjectReservationService;
import com.xiaozhou.gymxmjpa.service.UserReservationService;
import com.xiaozhou.gymxmjpa.util.CustomException;
import com.xiaozhou.gymxmjpa.util.ThreadLocalHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @Description: 课程管理Controller控制层
 * @Author: LiuJian
 * @Date: 2020/4/8
 */
@Slf4j
@Controller
@RequestMapping("/subject")
public class SubjectController {
   @Autowired
   private SubjectDaoImpl subjectDaoImpl;
   @Autowired
   private SubjectDao subjectDao;
   @Resource
   private ReservationSubjectDao reservationSubjectDao;
   @Autowired
   private PrivateCoachInfoDao privateCoachInfoDao;

    /**
     * @Description: 课程管理-进入课程信息界面
     * @Author: LiuJian
     * @Date: 2020/4/8
     */
    @RequestMapping("/jin7")
    public String jin7(){

        return "WEB-INF/jsp/subject";
    }

    /**
     * @Description: 课程管理-课程预约
     * @return
     */
    @RequestMapping("/jin8")
    public String jin8(){
        return "WEB-INF/jsp/members_subject";
    }

    /**
     * @Description: 课程管理-预约管理
     * @return
     */
    @RequestMapping("/jin9")
    public String jin9(){
        return "WEB-INF/jsp/reservation_subject";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(HttpSession httpSession){
        Adminuser adminuser=(Adminuser) httpSession.getAttribute("user");
        log.info("获取当前用户 =>"+adminuser.getAdminName());
        return adminuser.getAdminName();
    }

    /**
     * @Description: 课程管理-根据课程名称分页查询
     * @Author: LiuJian
     * @Date: 2020/4/8
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String,Object> query(String subname, int pageSize, int pageNumber){
        Map<String,Object>  map1=new HashMap<String,Object>();
        map1.put("subname",subname);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return subjectDaoImpl.query(map1);
    }

    @RequestMapping("/subjectReservationQuery")
    @ResponseBody
    public Map<String,Object> subjectReservationQuery(String subname, int pageSize, int pageNumber){
        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("subname",subname);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return subjectDaoImpl.subjectReservationQuery(map1);
    }

    @Autowired
    private UserReservationService userReservationService;

    @Resource
    UserReservationMapper userReservationMapper;

    @RequestMapping("/subQuery")
    @ResponseBody
    public Map<String,Object> subQuery(String subname, int pageNumber, int pageSize, HttpSession httpSession){
        log.info("预约课程subname =>{}",subname);
        // 获取当前管理员用户
        Adminuser adminuser = (Adminuser) httpSession.getAttribute("user");
        if (adminuser == null) {
            adminuser = ThreadLocalHolder.getCurrentUser();
        }
        long adminId = adminuser.getAdminId();
        log.info("预约课程adminId =>{}",adminId);

        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("subname",subname);
        map1.put("adminId",adminId);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return subjectDaoImpl.subQuery(map1);
//        List<UserReservation> userReservations = userReservationMapper.selectData(subname,adminId);
//        log.info("查询结果 =>{}",JSON.toJSONString(userReservations));
//        Integer total = userReservationMapper.selectDataPage(subname,adminId,pageNumber,pageSize);
//        if (null == total) total = 0;
//        // 封装结果
//        Map<String, Object> map = new HashMap<>();
//        map.put("total", total);
//        map.put("rows", userReservations);
//        return map;



    }


    @Autowired
    SubjectReservationService subjectReservationService;

    @Autowired
    SubjectReservationMapper subjectReservationMapper;

    /**
     * 保存预约课程
     */
    @RequestMapping("/saveReservation")
    public void saveReservation(@RequestBody UserReservation userReservation, HttpSession httpSession) throws RuntimeException{
        log.info("预约课程保存 =>{}",JSON.toJSONString(userReservation));
        Adminuser adminuser=(Adminuser) httpSession.getAttribute("user");
        if(adminuser == null){
            adminuser = ThreadLocalHolder.getCurrentUser();
        }
        log.info("adminuser:{}",JSON.toJSONString(adminuser));
        userReservation.setAdminId((int) adminuser.getAdminId());

        //校验预约人数
        Integer max = subjectReservationMapper.getMax(userReservation.getSubId());
        log.info("max:{}",max);
        Integer alreadyNum = subjectReservationMapper.getAlreadyNum(userReservation.getSubId());
        log.info("已预约人数:{}",alreadyNum);
        if (alreadyNum >= max) {
            log.info("当前时段课程已约满");
            throw new CustomException(999,"当前课程已约满");
        }
        userReservationService.save(userReservation);
        //更新已预约人数
        subjectReservationMapper.updateNum(userReservation.getSubId());

    }

//    @RequestMapping("/count")
//    public Map<String,Object> subjectReservationQuery(String subname){
//        log.info("课程查询总数 =>{}",subname);
//        return  subjectDaoImpl.count(subname);
//    }

    /**
     * @Description: 课程管理-根据课程id删除课程
     * @Author: LiuJian
     * @Date: 2020/4/8
     */
    @RequestMapping("/del")
    @ResponseBody
    public  Map<String,Object> del(long subId,String subname, int pageSize, int pageNumber){

        //先根据教练id在私教信息表里查询是否有其信息
        List<PrivateCoachInfo> privateCoachInfoList = privateCoachInfoDao.queryBySubjectIdNative(subId);
        if(privateCoachInfoList !=null && privateCoachInfoList.size() > 0){
            //如果有,先循环删除
            for(PrivateCoachInfo privateCoachInfo : privateCoachInfoList){
                if(subId == privateCoachInfo.getSubject().getSubId()){
                    privateCoachInfoDao.delete(privateCoachInfo);
                }
            }
        }
        subjectDao.deleteById(subId);
        Map<String,Object>  map1=new HashMap<String,Object>();
        map1.put("subname",subname);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return subjectDaoImpl.query(map1);
    }

    /**
     * @Description: 课程管理-添加课程
     * @Author: LiuJian
     * @Date: 2020/4/8
     */
    @RequestMapping("/add")
    @ResponseBody
    public  void save(Subject subject){
        subjectDao.save(subject);
    }

    /**
     * @Description: 课程管理-根据课程id查询课程信息
     * @Author: LiuJian
     * @Date: 2020/4/8
     */
    @RequestMapping("/cha")
    @ResponseBody
    public Optional<Subject> one(long subId){
        return subjectDao.findById(subId);
    }

    @RequestMapping("/getReservationSubjectById")
    @ResponseBody
    public Optional<ReservationSubject> getReservationSubjectById(long subId){
        return reservationSubjectDao.findById(subId);
    }

    /**
     * @Description: 课程管理-修改课程信息
     * @Author: LiuJian
     * @Date: 2020/4/8
     */
    @RequestMapping("/upd")
    @ResponseBody
    public  void upd(Subject subject){
        subjectDao.save(subject);
    }

    /**
     * @Description: 课程管理-根据课程名称计算总课程数据
     * @Author: LiuJian
     * @Date: 2020/4/8
     */
    @RequestMapping("/count")
    @ResponseBody
    public Long count (String subname){
        subjectDaoImpl.count(subname);
        return  subjectDaoImpl.count(subname);
    }

}
