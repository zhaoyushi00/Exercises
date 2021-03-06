package com.shi.address.service;

import com.shi.address.dao.PeopleMapper;
import com.shi.address.pojo.Page;
import com.shi.address.pojo.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    private PeopleMapper peopleMapper;

    public List<People> getAllPeople(int uid) { return peopleMapper.getAllPeople(uid); }

    public int deletePeople(int id) { return peopleMapper.deletePeople(id); }

    public int addPeople(People people) {
        return peopleMapper.addPeople(people);
    }

    public List getSort(int uid) {
        return peopleMapper.getSort(uid);
    }

    public Page<People> getSomePeople(int pageno, int uid, People people, int size) {
        //因为数据库中是不存在page这个实体的，所以不能直接返回方法
        Page<People> page = new Page();
        //初始化当前页码 一般为1
        page.setPageno(pageno);

        //一页三条信息
        int pageSize = 3;
        page.setPageSize(pageSize);

        //一页中第一条信息的下标
        int pageStartIndex;
        pageStartIndex = (pageno - 1)*pageSize;
        page.setPageStartIndex(pageStartIndex);

        //初始化当前页所包含的数据
        Map<Object,Object> map = new HashMap();
        map.put("pageStartIndex",page.getPageStartIndex());
        map.put("pagePageSize",page.getPageSize());
        map.put("people",people);
        List<People> datas = peopleMapper.getSomePeople(map);
        page.setDatas(datas);

        //初始化总记录数
        int totalRows = size;
        System.out.println(totalRows);
        page.setTotalRows(totalRows);

        //初始化totalPages
        int totalPages ;
        if(totalRows % pageSize==0){
            totalPages = totalRows / pageSize;
        }else {
            totalPages = totalRows / pageSize + 1;
        }
        page.setTotalPages(totalPages);

        return page;
    }

    public List<People> getsomePeopleNumber(People people) {
        HashMap map = new HashMap();
        map.put("people",people);
        return peopleMapper.getsomePeopleNumber(map);
    }

    public int revisePeople(People people) {
        return peopleMapper.revisePeople(people);
    }

    public People getPeopleById(int id) {
        return peopleMapper.getPeopleById(id);
    }

    public Page<People> getCurrentPage(int pageno, int uid) {
        //因为数据库中是不存在page这个实体的，所以不能直接返回方法
        Page<People> page = new Page();
        //初始化当前页码
        page.setPageno(pageno);
        //初始化总记录数
        int totalRows = peopleMapper.getAllCount(uid);
        page.setTotalRows(totalRows);
        //初始化totalPages
        int totalPages ;
        int pageSize = 3;
        int pageStartIndex;
        pageStartIndex = (pageno - 1)*pageSize;
        page.setPageStartIndex(pageStartIndex);
        page.setPageSize(pageSize);
        if(totalRows % pageSize==0){
            totalPages = totalRows / pageSize;
        }else {
            totalPages = totalRows / pageSize + 1;
        }
        page.setTotalPages(totalPages);
        //初始化当前页所包含的数据
        HashMap map = new HashMap();
        map.put("pageStartIndex",page.getPageStartIndex());
        map.put("pagePageSize",page.getPageSize());
        List<People> datas = peopleMapper.getCurrentPagePeople(map);
        page.setDatas(datas);

        return page;
    }

    public int getAllCount(int uid) {
        return 0;
    }

}
