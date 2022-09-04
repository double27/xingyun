package com.lframework.xingyun.basedata.biz.service.member;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.Member;
import com.lframework.xingyun.basedata.facade.vo.member.CreateMemberVo;
import com.lframework.xingyun.basedata.facade.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.member.QueryMemberVo;
import com.lframework.xingyun.basedata.facade.vo.member.UpdateMemberVo;
import java.util.Collection;
import java.util.List;

public interface IMemberService extends BaseMpService<Member> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<Member> query(Integer pageIndex, Integer pageSize, QueryMemberVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<Member> query(QueryMemberVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  Member findById(String id);

  /**
   * 根据ID停用
   *
   * @param ids
   */
  void batchUnable(Collection<String> ids);

  /**
   * 根据ID启用
   *
   * @param ids
   */
  void batchEnable(Collection<String> ids);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateMemberVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateMemberVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<Member> selector(Integer pageIndex, Integer pageSize, QueryMemberSelectorVo vo);
}
