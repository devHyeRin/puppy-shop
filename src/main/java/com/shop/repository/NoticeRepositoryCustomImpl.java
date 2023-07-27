package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemCategory;
import com.shop.constant.NoticeCategory;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.NoticeSearchDto;
import com.shop.entity.Item;
import com.shop.entity.Notice;
import com.shop.entity.QItem;
import com.shop.entity.QNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public NoticeRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchCategoryEq(NoticeCategory searchCategory){
        return searchCategory == null ? null : QNotice.notice.noticeCategory.eq(searchCategory);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("noticeTitle", searchBy)){
            return QNotice.notice.noticeTitle.like("%" + searchQuery + "%");
        }
        else if(StringUtils.equals("createdBy", searchBy)){
            return QNotice.notice.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable){
        QueryResults<Notice> results = queryFactory.selectFrom(QNotice.notice)
                .where(searchCategoryEq(noticeSearchDto.getSearchCategory()),
                        searchByLike(noticeSearchDto.getSearchBy(), noticeSearchDto.getSearchQuery()))
                .orderBy(QNotice.notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Notice> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression noticeTitleLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QNotice.notice.noticeTitle.like("%" + searchQuery + "%");
    }




}
