package com.newcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("shixian1")
public class AlphaDaoHibernateImpl implements AlphaDao{

    @Override
    public String select() {
        return "shixianjiekou";
    }
}
