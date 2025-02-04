/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2022 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.pagehelper.test.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.mapper.UserMapper;
import com.github.pagehelper.model.User;
import com.github.pagehelper.model.UserQueryModel;
import com.github.pagehelper.util.MybatisHelper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArgumentsObjTest {

    /**
     * 使用Mapper接口调用时，使用PageHelper.startPage效果更好，不需要添加Mapper接口参数
     */
    @Test
    public void testArgumentsObj() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        try {
            UserQueryModel queryModel = new UserQueryModel();
            queryModel.setPageNum(1);
            queryModel.setPageSize(10);
            queryModel.setOrderBy("id desc");
            List<User> list = userMapper.selectByQueryModel(queryModel);
            assertEquals(10, list.size());
            assertEquals(183, ((Page<?>) list).getTotal());

            queryModel.setPageNum(2);
            queryModel.setOrderBy(null);
            list = userMapper.selectByQueryModel(queryModel);
            assertEquals(10, list.size());
            assertEquals(183, ((Page<?>) list).getTotal());

            queryModel.setPageNum(3);
            queryModel.setPageSize(20);
            list = userMapper.selectByQueryModel(queryModel);
            assertEquals(20, list.size());
            assertEquals(183, ((Page<?>) list).getTotal());
        } finally {
            sqlSession.close();
        }
    }
}
