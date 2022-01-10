package com.fivepotato.eggmeetserver.domain.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.dto.Mentoring.SortOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


@Repository
public class UserQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public UserQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(User.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<User> findMentorsByMultipleConditionsOnPageable(Pageable pageable, Location location, Category mentorCategory,
                                                                SortOrder mentorRatingSortOrder, SortOrder growthPointOrder) {
        QueryResults<User> result = jpaQueryFactory
                .selectFrom(QUser.user)
                .where(
                        eqLocation(location),
                        eqMentorCategory(mentorCategory)
                )
                .orderBy(
                        orderByMentorRating(mentorRatingSortOrder),
                        orderByGrowthPoint(growthPointOrder)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression eqLocation(Location location) {
        if (location == null) {
            return null;
        }

        return QUser.user.location.eq(location);
    }

    private BooleanExpression eqMentorCategory(Category mentorCategory) {
        if (mentorCategory == null) {
            return null;
        }

        return QUser.user.mentorArea.category.eq(mentorCategory);
    }

    private OrderSpecifier orderByMentorRating(SortOrder sortOrder) {
        if (sortOrder.equals(SortOrder.ASCENDING)) {
            return QUser.user.mentorRating.asc();

        } else if (sortOrder.equals(SortOrder.DESCENDING)) {
            return QUser.user.mentorRating.desc();
        }

        return null;
    }

    private OrderSpecifier orderByGrowthPoint(SortOrder sortOrder) {
        if (sortOrder.equals(SortOrder.ASCENDING)) {
            return QUser.user.menteeRating.asc();

        } else if (sortOrder.equals(SortOrder.DESCENDING)) {
            return QUser.user.menteeRating.desc();
        }

        return null;
    }
}
